
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

class LatinParsedTokenSequenceObjectSpec extends FlatSpec {


  val f = "jvm/src/test/resources/c108a.cex"
  val corpus = LatinCorpus.fromFile(f)
  val tokens = corpus.tokens

  "The LatinParsedTokenSequenceObject" should "determine if a list of tokens has a match for a given lexeme ID" in {
    val lexeme = "ls.n16074"
    assert(LatinParsedTokenSequence.matchesLexeme(tokens, lexeme))

  }
  it should "match any lexeme in a list of lexeme IDs" in {
    val lexemes = Vector("bogus.id", "ls.n16074")
    assert(LatinParsedTokenSequence.matchesLexeme(tokens, lexemes))
  }
  it should "select all analyzed tokens" in pending
  it should "select all unanalyzed tokens" in pending
  it should "select all tokens with a single analysis" in pending
  it should "select all tokens with multiple analyses" in pending
  it should "select all lexical tokens" in pending
  it should "select all tokens with a single lexeme" in pending
  it should "select all tokens with multiple lexemes" in pending

  it should "sort a list of lexeme IDs" in pending

  // MOVE TENSE/MOOD STUFF FROM Corpus to trait
  //


}
