
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._

import org.scalatest.FlatSpec

class LatinParsedTokenSpec extends FlatSpec {

  val urn = CtsUrn("urn:cts:latinLit:phi0959.phi006:1.1.1")
  val cn = CitableNode(urn,"In")
  val indeclForm = IndeclinableForm("ls.n22111", "latcommon.n22111","latcommon.indeclinfl1", Preposition)

  val parsedToken = LatinParsedToken(cn, LexicalToken, Vector(indeclForm))


  "A LatinNode" should "have a URN" in {
    assert(parsedToken.urn == urn)
  }
  it should "have a text reading" in {
    assert(parsedToken.text == "In")
  }
  it should "have a token category" in {
    assert(parsedToken.category == LexicalToken)
  }
  it should "have a vector of analyses" in {
    val expectedAnalyses = 1
    assert(parsedToken.analyses.size == expectedAnalyses)
  }
  it should "have extended LemmatizedForms in the analysis vector" in {
    val analysis = parsedToken.analyses(0)

    val indeclAnalysis : IndeclinableForm = analysis match {
      case indecl : IndeclinableForm => indecl
      case _ => throw new Exception("Hey, that wasn't an indeclinable!")
    }
    assert(indeclAnalysis.pos == Preposition)
  }

  it should "determine if it has an analysis to give lexeme" in {
    val lexeme = "ls.n22111"
    assert(parsedToken.matchesLexeme(lexeme))
    val bogus = "ls.notforreal"
    assert(parsedToken.matchesLexeme(bogus) == false)
  }

  it should "determine if it has an analysis matching any of a list of lexemes" in {
    val lexeme = "ls.n22111"
    val bogus = "ls.notforreal"
    val mixed = Vector(lexeme, bogus)
    assert(parsedToken.matchesAny(mixed))

    val noneatall = Vector(bogus, "ls.anotherfake")
    assert(parsedToken.matchesAny(noneatall) == false)
  }

}
