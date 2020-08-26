
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._


import org.scalatest.FlatSpec

class StringFormatterSpec extends FlatSpec {

  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val chapter = LatinCorpus.fromFile(chapterFile, cexHeader = false)
  val testVerb = chapter.sentences.sequences(2).finiteVerbs(2)


  "A StringFormatter" should "somehow format strings" in pending



}
