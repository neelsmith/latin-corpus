package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


case class LatinParsedToken(
  cn: CitableNode,
  category: MidTokenCategory,
  analyses: Vector[LemmatizedForm] = Vector.empty[LemmatizedForm]
) {

  def urn = cn.urn
  def text = cn.text

  def matchesAny(lexemes: Vector[String]) : Boolean = {
    val tf = for (lex <- lexemes) yield {
      matchesLexeme(lex)
    }
    tf.contains(true)
  }

  def matchesLexeme(lexeme: String) :  Boolean = {
    analyses.filter(_.lemmaId == lexeme).nonEmpty
  }

  def analysisUrns(umgr: UrnManager) : Vector[LemmatizedFormUrns] = {
    val lexNull = Cite2Urn("urn:cite2:tabulae:ls.v1:null")
    val morphNull =  Cite2Urn("urn:cite2:tabulae:morphforms.v1:null")
    if (analyses.isEmpty) {
      val v = Vector(LemmatizedFormUrns(urn,text,lexNull, morphNull, category))
      v
    } else {
      val lines = for (a <- analyses) yield {
        umgr.urn(a.lemmaId) match {
          case None => LemmatizedFormUrns(urn,text,lexNull, a.formUrn, category)
          case Some(u) => LemmatizedFormUrns(urn,text,u,a.formUrn, category)
        }
      }
      lines
    }
  }
}

object LatinParsedToken extends LogSupport {

  //urn#label#passage#token#lexeme#form#category#sequence
  // One or more lines ofr a single token
  /*()
  def apply(cex: Vector[String]) : LatinParsedToken = {
    fromCexLine(cex)
  }
*/
  def analysisFromLine(columns: Vector[String]) = {

  }

/*
  def fromCexLine(cex: Vector[String], separator: String = "#") : LatinParsedToken = {
    val byColumns = cex.map(ln => ln.split(separator))
    val first = byColumns.head
    val urn = CtsUrn(first(0)).collapsePassageBy(1)
    val text = first(1)
    val citableNode = CitableNode(urn, text)
    val tokenType: MidTokenCategory =  tokenCategory(first(4))

    for (row <- byColumns) yield {
      val lexeme = Cite2Urn(first(2))
      val form = Cite2Urn(first(3))

    }

    LatinParsedToken(urn,tokenType, text,lexeme, form )
  }
  */

  // defined in MID and latin libraries.
  // If you need other, custom types they won't be recognized.
  //
  val recognizedTokenTypes = Vector(
    LexicalToken,
    NumericToken,
    PunctuationToken,
    PraenomenToken
  )

  def tokenCategory(s: String): Option[MidTokenCategory] = {
    val candidates = recognizedTokenTypes.filter(_.toString == s)
    if (candidates.size == 1) {
      Some(candidates.head)
    } else {
      warn("LatinCorpus: could not find token type for string " + s)
      None
    }
  }


}
