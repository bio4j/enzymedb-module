package ohnosequences.bio4j.bundles

import shapeless._
import shapeless.ops.hlist._
import ohnosequences.typesets._
import ohnosequences.statika._
import ohnosequences.statika.aws._
import ohnosequences.statika.ami._
import ohnosequences.bio4j.statika._
import ohnosequences.awstools.s3._
import ohnosequences.awstools.regions._
import com.ohnosequences.bio4j.titan.programs._
import java.io._

/* This bundle is important, it doesn't really import anything, but initializes Bio4j */
case object InitialBio4j extends Bundle() with AnyBio4jInstanceBundle {
  val dbLocation: File = new File("/media/ephemeral0/bio4jtitandb")

  override def install[D <: AnyDistribution](d: D): InstallResults = {
    if (!dbLocation.exists) dbLocation.mkdirs
    InitBio4jTitan.main(Array(dbLocation.getAbsolutePath))
    success("Initialized Bio4j DB in " + dbLocation)
  }
}

case object EnzymeDBRawData extends RawDataBundle("ftp://ftp.expasy.org/databases/enzyme/enzyme.dat")

case object EnzymeDBAPI extends APIBundle(){}

case class EnzymeDBProgram(
  data : File, // 1. Enzyme DB data file (.dat)
  db   : File  // 2. Bio4j DB folder
) extends ImporterProgram(new ImportEnzymeDBTitan(), Seq(
  data.getAbsolutePath, 
  db.getAbsolutePath
))

case object EnzymeDBImportedData extends ImportedDataBundle(
    rawData = EnzymeDBRawData :~: ∅,
    initDB = InitialBio4j,
    importDeps = ∅
  ) {
  override def install[D <: AnyDistribution](d: D): InstallResults = {
    EnzymeDBProgram(
      data = EnzymeDBRawData.inDataFolder("enzyme.dat"),
      db   = dbLocation
    ).execute ->-
    success("Data " + name + " is imported to" + dbLocation)
  }
}

case object EnzymeDBModule extends ModuleBundle(EnzymeDBAPI, EnzymeDBImportedData)

case object EnzymeDBMetadata extends generated.metadata.EnzymedbModule()

case object EnzymeDBRelease extends ReleaseBundle(
  ObjectAddress("bio4j.releases", 
                "enzymedb/v" + EnzymeDBMetadata.version.stripSuffix("-SNAPSHOT")), 
  EnzymeDBModule
)

case object EnzymeDBDistribution extends DistributionBundle(
  EnzymeDBRelease,
  destPrefix = new File("/media/ephemeral0/")
)

