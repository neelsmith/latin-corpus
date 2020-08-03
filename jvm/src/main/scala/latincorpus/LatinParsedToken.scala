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
  // Handy:
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

  /** Map analyses to [[LemmatizedFormUrns]] with assitance of a
  * [[UrnManager]] to expand abbreviations fto full URNs.
  *
  * @param umgr UrnManger to use for expanding abbreviated IDs.
  */
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

  /** Create a [[LatinParsedToken]] from a series of CEX
  * lines in the parsed token data model.  The Vector should
  * include one line for each analysis of the given token.
  *
  * @param cex CEX lines, one per analysis, of the form:
  * urn#label#passage#token#lexeme#form#category#sequence
  * where each line is an analysis of the same token.
  */
  def apply(cex: Vector[String]) : LatinParsedToken = {
    fromCexLines(cex)
  }

  /** Create abbreviated identifier following tabulae
  * convention of crossing collection and object identifiers
  * of a Cite2Urn.
  *
  * @param urn Cite2Urn to represent with an abbreviated string.
  */
  def abbreviatedUrn(urn: Cite2Urn): String = {
    urn.collection + "." + urn.objectComponent
  }

  /** From a group of lines representing all analyses of a given
  * token, construct a [[LatinParsedToken]] with one analysis from
  * each CEX line.
  *
  * @param cex CEX records following parsed token data model.
  * @param separator String separating columns in CEX input.
  */
  def fromCexLines(cex: Vector[String], separator: String = "#") : LatinParsedToken = {
    val rowsByColumns = cex.map(ln => ln.split(separator))
    val first = rowsByColumns.head
    val urn = CtsUrn(first(2)).collapsePassageBy(1)
    val text = first(3)
    val citableNode = CitableNode(urn, text)
    val tokenType: MidTokenCategory =  tokenCategory(first(6)).get

    val lemmatizedFormOpts = for (row <- rowsByColumns) yield {
      val lexeme = Cite2Urn(row(4))
      val lemmaId = abbreviatedUrn(lexeme)
      val form = Cite2Urn(row(5))
      LemmatizedForm(lemmaId, "", "", form )
    }
    val lemmatizedForms = lemmatizedFormOpts.flatten
    val token = LatinParsedToken(citableNode,tokenType,lemmatizedForms )
    debug("Made token from CEX lines: " + token)
    token
  }


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
