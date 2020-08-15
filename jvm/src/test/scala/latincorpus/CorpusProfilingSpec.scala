
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._

import org.scalatest.FlatSpec
//import scala.io._

class CorpusProfilingSpec extends FlatSpec {

  val corpusFile = "jvm/src/test/resources/hyginus-latc.cex"
  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val corpus = LatinCorpus.fromFile(corpusFile)
  val chapter = LatinCorpus.fromFile(chapterFile)

  "A LatinCorpus" should "create a vocabulary list" in {
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
  it should "create a list of valid forms" in {
    println("NUMBER OF DISTINCT FORMS: " + chapter.forms.size)
  }

  it should "create a histogram of tokens" in {
    val tokensHisto = chapter.tokensHistogram(caseSensitive = false)
    assert(chapter.vocabulary().size == tokensHisto.size)
    assert(chapter.lexicalTokens.size == tokensHisto.total)
  }
  it  should "create a histogram of lexemes" in {
    val lexemesHisto = chapter.lexemesHistogram
    assert(lexemesHisto.size == chapter.lexemes.size)
    val tops = lexemesHisto.frequencies.head
    val expectedCount = 12
    val expectedLexeme = "ls.n21026"
    assert(tops.count == expectedCount)
    assert(tops.item == expectedLexeme)
  }
  it  should "create a histogram of LS-labelled lexemes" in {
    val lexemesHisto = chapter.labelledLexemesHistogram
    val tops = lexemesHisto.sorted.frequencies.head
    val expectedCount = 12
    val expectedLexeme = "ls.n21026:hostis"
    assert(tops.count == expectedCount)
    assert(tops.item == expectedLexeme)
  }

  it should "create a histogram of valid forms" in {
    val expectedMostFrequent  = 9
    assert(chapter.formsHistogram.frequencies.head.count == expectedMostFrequent)
  }


  it should "create a histogram of lexemes labelled with PoS" in pending
  it should "create a histogram of tokens labelled with PoS" in pending

  it should "measure percent coverage of tokens" in pending
  it should "measure percent coverage of token occurrences" in pending

  it should "create a histogram of lexically ambiguous tokens" in pending


}
