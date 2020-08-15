
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.histoutils._
import org.scalatest.FlatSpec
import scala.io._

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

  it should "create a concordance of forms" in {
    //println(cha)
  }



  it should "create a vocabulary list" in {
    val expectedSize = 83
    assert(chapter.vocabulary().size == expectedSize)
  }
  it should "create a lexeme list" in {
    val lexemes = chapter.lexemes
    //println(lexemes)
  }
  it should "create a labelled lexeme list" in {
    val lexemes = chapter.labelledLexemes
    //println(lexemes.mkString("\n"))
  }

  it should "index tokens to lexemes" in {
    val expectedSize = chapter.analyzed.map(_.text).distinct.size

    assert(chapter.tokenLexemeIndex.size == expectedSize)
    // Test relative/interrogative ambiguity:
    val expectedLexemes = 2
    assert(chapter.tokenLexemeIndex("quem").size == expectedLexemes)
  }
  it should "index lexemes to tokens" in {
    val expectedForms = Set("sunt", "essent", "est")
    val sum = "ls.n46529"
    assert(chapter.lexemeTokenIndex(sum).toSet == expectedForms)
  }

  it should "pair lexemes and tokens" in pending /*{
    // This is OK: figure out a good test for it:
    println(chapter.lexemeTokenPairings.size)
  }*/



}
