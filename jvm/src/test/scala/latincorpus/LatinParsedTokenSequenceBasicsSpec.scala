
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

class LatinParsedTokenSequenceBasicsSpec extends FlatSpec {


  val f = "jvm/src/test/resources/c108a.cex"
  val corpus = LatinCorpus.fromFile(f)
  val tokens = corpus.tokens

  "A LatinParsedTokenSequence" should "select all analyzed tokens" in pending
  it should "select all unanalyzed tokens" in pending
  it should "select all tokens with a single analysis" in pending
  it should "select all tokens with multiple analyses" in pending
  it should "select all lexical tokens" in pending
  it should "select all tokens with a single lexeme" in pending
  it should "select all tokens with multiple lexemes" in pending

  // MOVE TENSE/MOOD STUFF FROM Corpus to trait
  //


}
