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

case object enzymedbRawData 
  extends RawDataBundle(???)

case object enzymedbAPI extends APIBundle(){}

case class enzymedbProgram(
  ???
) extends ImporterProgram(new ImportenzymedbTitan(), Seq(
  ???
))

case object enzymedbImportedData extends ImportedDataBundle(
    rawData = enzymedbRawData :~: ∅,
    initDB = InitialBio4j,
    importDeps = ∅
  ) {
  override def install[D <: AnyDistribution](d: D): InstallResults = {
    enzymedbProgram(
      ???
    ).execute ->-
    success("Data " + name + " is imported to" + dbLocation)
  }
}

case object enzymedbModule extends ModuleBundle(enzymedbAPI, enzymedbImportedData)

case object enzymedbMetadata extends generated.metadata.EnzymedbModule()

case object enzymedbRelease extends ReleaseBundle(
  ObjectAddress("bio4j.releases", 
                "enzymedb/v" + enzymedbMetadata.version.stripSuffix("-SNAPSHOT")), 
  enzymedbModule
)

case object enzymedbDistribution extends DistributionBundle(
  enzymedbRelease,
  destPrefix = new File("/media/ephemeral0/")
)

