
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

class CorpusProfilingSpec extends FlatSpec {

  val corpusFile = "jvm/src/test/resources/hyginus-latc.cex"
  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val corpus = LatinCorpus.fromFile(corpusFile)
  val chapter = LatinCorpus.fromFile(chapterFile)

  "A LatinCorpus" should "create a histogram of tokens" in {
    val tokensHisto = chapter.tokensHistogram(caseSensitive = false)
    assert(chapter.vocabulary().size == tokensHisto.size)
    assert(chapter.lexicalTokens.size == tokensHisto.total)
  }
  it  should "create a histogram of lexemes" in {
    val lexemesHisto = chapter.lexemesHistogram
    assert(lexemesHisto.total == chapter.lexemes.size)
    assert(lexemesHisto.total == chapter.lexicalTokens.flatMap(_.analyses).size)
    val tops = lexemesHisto.sorted.frequencies.head
    val expectedCount = 12
    val expectedLexeme = "ls.n21026"
    assert(tops.count == expectedCount)
    assert(tops.item == expectedLexeme)
  }
  it  should "create a histogram of LS-labelled lexemes" in {
    val lexemesHisto = chapter.labelledLexemesHistogram
    //println(lexemesHisto.sorted)
    //ls.n21026:hostis,12
    val tops = lexemesHisto.sorted.frequencies.head
    val expectedCount = 12
    val expectedLexeme = "ls.n21026:hostis"
    assert(tops.count == expectedCount)
    assert(tops.item == expectedLexeme)

  }


  it should "create a histogram of forms" in pending
  it should "create a histogram of lemmatized forms" in pending


  it should "create a histogram of lexemes labelled with PoS" in pending
  it should "create a histogram of tokens labelled with PoS" in pending

  it should "measure percent coverage of tokens" in pending
  it should "measure percent coverage of token occurrences" in pending

  it should "create a histogram of lexically ambiguous tokens" in pending


}
