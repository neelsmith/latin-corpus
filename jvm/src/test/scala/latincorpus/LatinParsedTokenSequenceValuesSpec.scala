
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

class LatinParsedTokenSequenceValuesSpec extends FlatSpec {


  val f = "jvm/src/test/resources/c108a.cex"
  val corpus = LatinCorpus.fromFile(f)
  val tokens = corpus.tokens

  "A LatinParsedTokenSequence" should "determine values for case" in {
    println(corpus.valuesForCategory(GrammaticalCaseValues))
  }
  it should "determine values for gender" in {
    println(corpus.valuesForCategory(GenderValues))
  }

  it should "determine values for person" in {
    println(corpus.valuesForCategory(PersonValues))
  }
  it should "determine values for tense" in {
    println(corpus.valuesForCategory(TenseValues))
  }
  it should "determine values for mood" in {
    println(corpus.valuesForCategory(MoodValues))
  }




}
