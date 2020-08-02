package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._

case class LemmatizedFormUrns(
  psg: CtsUrn,
  txt: String,
  lexeme: Cite2Urn,
  form: Cite2Urn,
  category: MidTokenCategory
) {
  def cex(separator: String = "#") = {
    List(psg, txt, lexeme, form, category).mkString(separator)
  }
}
