package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._

case class LatinToken(
  cn: CitableNode,
  category: MidTokenCategory,
  morphology: Vector[LemmatizedForm] = Vector.empty[LemmatizedForm]
) {

  def urn = cn.urn
  def text = cn.text
}
