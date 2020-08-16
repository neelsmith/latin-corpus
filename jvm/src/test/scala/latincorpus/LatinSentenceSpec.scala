package edu.holycross.shot.latincorpus

import edu.holycross.shot.tabulae._

import org.scalatest.FlatSpec

class LatinSentenceSpec extends FlatSpec {


  val chapterFile = "jvm/src/test/resources/c108a.cex"
  val chapter = LatinCorpus.fromFile(chapterFile, cexHeader = false)
  val tokens = chapter.tokens
  val abbrs = Vector(
    "abbr#full",
    "ls#urn:cite2:tabulae:ls.v1:"
  )
  val umgr = UrnManager(abbrs)

  "The LatinSentence object" should "be able to parse a sequence of tokens into sentences" in {
    val sentences = LatinSentence.sentences(tokens)
    println("\n\nFound " + sentences.size + " Sentences")
  }


}
