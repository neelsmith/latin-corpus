package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._


trait FeatureOccurrence {
  def urn : CtsUrn
  def featureList : Vector[MorphologicalProperty]
}

case class GrammaticalCaseOccurrence(urn: CtsUrn, featureList : Vector[MorphologicalProperty]) extends FeatureOccurrence

//
