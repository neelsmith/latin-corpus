
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._

import org.scalatest.FlatSpec

class LatinTokenSpec extends FlatSpec {

  val urn = CtsUrn("urn:cts:latinLit:phi0959.phi006:1.1.1")
  val cn = CitableNode(urn,"In")
  val indeclForm = IndeclinableForm("ls.n22111", "latcommon.n22111","latcommon.indeclinfl1", Preposition)

  val latinToken = LatinToken(cn, LexicalToken, Vector(indeclForm))


  "A LatinNode" should "have a URN" in {
    assert(latinToken.urn == urn)
  }
  it should "have a text reading" in {
    assert(latinToken.text == "In")
  }
  it should "have a token category" in {
    assert(latinToken.category == LexicalToken)
  }
  it should "have a vector of analyses" in {
    val expectedAnalyses = 1
    assert(latinToken.analyses.size == expectedAnalyses)
  }
  it should "have extended LemmatizedForms in the analysis vector" in {
    val analysis = latinToken.analyses(0)

    val indeclAnalysis : IndeclinableForm = analysis match {
      case indecl : IndeclinableForm => indecl
      case _ => throw new Exception("Hey, that wasn't an indeclinable!")
    }
    assert(indeclAnalysis.pos == Preposition)
  }

  it should "determine if it has an analysis to give lexeme" in {
    val lexeme = "ls.n22111"
    assert(latinToken.matchesLexeme(lexeme))
    val bogus = "ls.notforreal"
    assert(latinToken.matchesLexeme(bogus) == false)
  }

  it should "determine if it has an analysis matching any of a list of lexemes" in {
    val lexeme = "ls.n22111"
    val bogus = "ls.notforreal"
    val mixed = Vector(lexeme, bogus)
    assert(latinToken.matchesAny(mixed))

    val noneatall = Vector(bogus, "ls.anotherfake")
    assert(latinToken.matchesAny(noneatall) == false)
  }

}
