
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


  val urnManagerFile = "jvm/src/test/resources/urnregistry.cex"
  val urnManager = UrnManager.fromFile(urnManagerFile)



  "A LatinParsedToken" should "have a URN" in {
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

  it should "extract an option for the analytical combinationa TenseMood" in {
    val cex = Vector("urn:cite2:linglat:tkns.v1:2020_08_02_44077#Record 2020_08_02_44077#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:196a.1.19#deciperent#urn:cite2:tabulae:ls.v1:n12498#urn:cite2:tabulae:morphforms.v1:322210004#LexicalToken#44077")
    val corpus = LatinCorpus(cex)
    val verb = corpus.tokens.head
    println(verb.tenseMood)
  }

  it should "report if the form of the token matches a form specified by URN" in {
    val cex = Vector("urn:cite2:linglat:tkns.v1:2020_08_02_44077#Record 2020_08_02_44077#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:196a.1.19#deciperent#urn:cite2:tabulae:ls.v1:n12498#urn:cite2:tabulae:morphforms.v1:322210004#LexicalToken#44077")
    val corpus = LatinCorpus(cex)
    val verb = corpus.tokens.head

    val expectedTrue  = Cite2Urn("urn:cite2:tabulae:morphforms.v1:322210004")
    assert(verb.hasForm(expectedTrue, urnManager))
    val expectedFalse  = Cite2Urn("urn:cite2:tabulae:morphforms.v1:222210004")
    assert(verb.hasForm(expectedFalse, urnManager) == false)
   }




}
