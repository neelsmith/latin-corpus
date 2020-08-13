
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

  "A LatinCorpus" should "create concordance of tokens" in {
    val expectedSize = 8119
    assert(corpus.tokenConcordance.size == expectedSize)
  }

  it should "create a vocabulary list" in {
    val expectedSize = 83
    assert(chapter.vocabulary().size == expectedSize)
  }


  it should "index tokens to lexemes" in pending /*{
    // Works, need a good test
    //println(chapter.tokenLexemeIndex)
  }*/

  it should "create concordance of lexemes" in {
  }

  it should "create a concordance of forms" in pending





}
