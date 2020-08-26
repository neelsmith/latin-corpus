
package edu.holycross.shot.latincorpus

import org.scalatest.FlatSpec

class TokenAnalyticalStatusSpec extends FlatSpec {

  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val chapter = LatinCorpus.fromFile(chapterFile, cexHeader = false)
  val testVerb = chapter.sentences.sequences(2).finiteVerbs(2)


  "A LatinParsedToken" should "identify if it is unanalyzed" in {
    val epeus = chapter.tokens.filter(_.text == "Epeus").head
    assert(epeus.unanalyzed)
  }

  it should "identify if it is morphologically ambiguous" in {
    val capere = chapter.tokens.filter(_.text == "capere").head
    assert(capere.ambiguous)

  }
  it should "identify if it is lexically ambiguous" in {
    val dono = chapter.tokens.filter(_.text == "DONO").head
    assert(dono.lexicallyAmbiguous)
  }
  it should "identify if it is unambigously analyzed" in {
    val equus = chapter.tokens.filter(_.text == "EQUUS").head
    assert(equus.singleAnalysis)
  }


}
