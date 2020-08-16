
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

class LatinParsedTokenDisplaySpec extends FlatSpec {


  val f = "jvm/src/test/resources/c108a.cex"
  val corpus = LatinCorpus.fromFile(f)
  val tokens = corpus.tokens

  "A LatinParsedTokenSequence" should  "highlight tokens by grammatical category" in pending
  it should "support cool hover display in HTML environment" in pending

}
