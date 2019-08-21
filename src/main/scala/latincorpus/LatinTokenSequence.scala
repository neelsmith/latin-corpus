package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._

trait LatinTokenSequence {
  def tokens: Vector[LatinToken]

  def verbs = {
    tokens.filter(tkn => tkn.analyses.nonEmpty && tkn.analyses(0).posLabel == "verb")
  }
}
