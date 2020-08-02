
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._

import org.scalatest.FlatSpec

class LatinParsedTokenUrnsSpec extends FlatSpec {


  val urn = CtsUrn("urn:cts:latinLit:phi0959.phi006:1.1.1")
  val cn = CitableNode(urn,"In")
  val indeclForm = IndeclinableForm("ls.n22111", "latcommon.n22111","latcommon.indeclinfl1", Preposition)
  val parsedToken = LatinParsedToken(cn, LexicalToken, Vector(indeclForm))

  val abbrs = Vector(
    "abbr#full",
    "ls#urn:cite2:tabulae:ls.v1:"
  )
  val umgr = UrnManager(abbrs)

  "A LatinParsedToken" should "be able to express all analyses as LemmatizedFormUrns" in {
    val expectedAnalyses = 1
    val expectedLexeme = Cite2Urn("urn:cite2:tabulae:ls.v1:n22111")
    val expectedForm = Cite2Urn("urn:cite2:tabulae:morphforms.v1:00000000B")
    val tokenUrns = parsedToken.analysisUrns(umgr)
    val expectedPassage = CtsUrn("urn:cts:latinLit:phi0959.phi006:1.1.1")
    assert(tokenUrns.size == expectedAnalyses)
    assert(tokenUrns.head.txt == "In")
    assert(tokenUrns.head.lexeme == expectedLexeme)
    assert(tokenUrns.head.psg == expectedPassage)

    val expectedCex = "urn:cts:latinLit:phi0959.phi006:1.1.1#In#urn:cite2:tabulae:ls.v1:n22111#urn:cite2:tabulae:morphforms.v1:00000000B#LexicalToken"
    assert(tokenUrns.head.cex() == expectedCex)
  }

}
