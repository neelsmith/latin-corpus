package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._


trait FeaturesInPassage {
  def urn : CtsUrn
  def featureList : Vector[MorphologicalProperty]
}

case class GrammaticalCasesInPassage(urn: CtsUrn, featureList : Vector[MorphologicalProperty]) extends FeaturesInPassage

//
