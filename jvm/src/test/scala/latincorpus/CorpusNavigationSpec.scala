
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


  it should "index tokens to lexemes" in {
    // Works, need a good test
    val expectedSize = chapter.analyzed.map(_.text).distinct.size

    assert(chapter.tokenLexemeIndex.size == expectedSize)
    val expectedLexemes = 2 // relative/interrogative ambiguity
    assert(chapter.tokenLexemeIndex("quem").size == expectedLexemes)
  }


  it should "pair lexemes and tokens" in {
    // This is OK: figure out a good test for it:
    //println(chapter.lexemeTokenPairings)
  }

  it should "index lexemes to tokens" in {
    val expectedForms = Set("sunt", "essent", "est")
    val sum = "ls.n46529"
    assert(chapter.lexemeTokenIndex(sum).toSet == expectedForms)
  }

  it should "create concordance of lexemes" in pending /*{
  }*/

  it should "create a concordance of forms" in {
    // CEX SOURCE IS WRONGLY INDEXED TO PARENT NODE
  }





}
