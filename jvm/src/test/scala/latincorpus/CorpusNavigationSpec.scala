
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._

import org.scalatest.FlatSpec


class CorpusNavigationSpec extends FlatSpec {

  val corpusFile = "jvm/src/test/resources/hyginus-latc.cex"
  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val corpus = LatinCorpus.fromFile(corpusFile)
  val chapter = LatinCorpus.fromFile(chapterFile)

  "A LatinCorpus" should "create a concordance of tokens" in {
    // All tokens:
    val expectedSize = 8119
    assert(corpus.tokenConcordance.size == expectedSize)
    // Count only lexical tokens:
    val lexOnly = LatinCorpus(corpus.lexicalTokens)
    assert(lexOnly.tokenConcordance.size == corpus.vocabulary(true).size)
    // Retrieve ordered list of occurences:
    val expected =   Vector(
      CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.1"), CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.2.2"), CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.2.24"), CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.3.3")
    )
    val actual = chapter.tokenConcordance("cum")
    assert(actual == expected)
  }


  it should "create concordance of lexemes" in {
    assert(chapter.lexemeConcordance.size == chapter.lexemes.size)
  }
  it should "create concordance of labelled lexemes" in {
    //println(chapter.labelledLexemeConcordance.toVector.mkString("\n"))
    val labelledId = "ls.n11872:cum1"
    val expected = Vector(
      CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.1"), CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.2.2"), CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.2.24"), CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.3.3")
    )
    val actual = chapter.labelledLexemeConcordance(labelledId)
    assert(actual == expected)
  }

  it should "create a concordance of valid forms" in {
    val conjunction = ValidForm(Cite2Urn("urn:cite2:tabulae:morphforms.v1:00000000A"))
    val expected = Vector(
      CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.29"), CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.2.18"), CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.3.5"), CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.3.9"), CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.3.14"), CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.3.20"), CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.3.28")
    )
    assert(chapter.formConcordance(conjunction) == expected)
  }




}
