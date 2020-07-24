
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._
import scala.io.Source

import org.scalatest.FlatSpec

class LatinCorpusChunkingSpec extends FlatSpec {


/*
  val corpus = CorpusSource.fromFile("src/test/resources/cex/met-sample.cex",cexHeader=true)
  val fstLines = Source.fromFile("src/test/resources/fst/met-sample-parsed.txt").getLines.toVector
  val latcorp = LatinCorpus.fromFstLines(corpus, Latin24Alphabet, fstLines, strict=false)
*/
  "A LatinCorpus" should "segment tokens by punctuated phrases" in pending /*{
    val phrases = latcorp.segmentByPhrase()
    println(phrases.size + " phrases.")
    for (phrase <- phrases) {
      println(phrase.canonicalNode + "\n\n")
    }
  } */

  it should "identify if phrases contain a conjunction" in pending /*{
    val phrases = latcorp.segmentByPhrase()
    for (p <- phrases) {
      println(p.conjunctions)
    }
  }*/



}
