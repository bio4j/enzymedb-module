package ohnosequences.bio4j.bundles

import ohnosequences.typesets._
import ohnosequences.statika._
import ohnosequences.statika.aws._
import ohnosequences.statika.ami._
import ohnosequences.awstools.regions._

case object EnzymeDBApplicator extends AWSDistribution(
  EnzymeDBMetadata,
  amzn_ami_pv_64bit(Region.Ireland)(javaHeap = 6),
  members = EnzymeDBRelease :~: EnzymeDBDistribution :~: ∅
)
