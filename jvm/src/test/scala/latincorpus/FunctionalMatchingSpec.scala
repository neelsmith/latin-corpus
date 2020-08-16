
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._


import org.scalatest.FlatSpec

class FunctionalMatchingSpec extends FlatSpec {
  val corpusFile = "jvm/src/test/resources/hyginus-latc.cex"
  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val corpus = LatinCorpus.fromFile(corpusFile)
  val chapter = LatinCorpus.fromFile(chapterFile)

  val abbrs = Vector(
  "abbr#full",
  "ls#urn:cite2:tabulae:ls.v1:"
  )
  val umgr = UrnManager(abbrs)

  "A LatinCorpus" should "create a sequence of ValidForms" in {
    println(chapter.citableForms(umgr))
  }
}
