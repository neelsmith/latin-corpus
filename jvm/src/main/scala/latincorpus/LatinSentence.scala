package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

case class LatinSentence(tokens: Vector[LatinParsedToken]) extends LatinParsedTokenSequence {
}

object LatinSentence extends LogSupport {

  def apply(seq: LatinParsedTokenSequence):
  ParsedSequenceCollection = {
  //Vector[LatinSentence] = {
    sentences(seq.tokens)
  }

  def sentences(tokens: Vector[LatinParsedToken],
    currentSentence: Vector[LatinParsedToken] = Vector.empty[LatinParsedToken],
    currentSentences: Vector[LatinSentence] = Vector.empty[LatinSentence]) :
    ParsedSequenceCollection = {

    ///Vector[LatinSentence] = {
    //Logger.setDefaultLogLevel(LogLevel.DEBUG)
    if (tokens.isEmpty) {
      debug("Adding last sentence from tokens: " + currentSentence)
      //Logger.setDefaultLogLevel(LogLevel.WARN)
      if (currentSentence.isEmpty) {
        ParsedSequenceCollection(currentSentences)
      } else {
        ParsedSequenceCollection(currentSentences :+ LatinSentence(currentSentence))
      }

    } else {
      val token = tokens.head
      if (".;?".contains(token.text)) {
        val updatedSentences = currentSentences :+ LatinSentence(currentSentence :+ token)

        sentences(tokens.tail, Vector.empty[LatinParsedToken], updatedSentences)

      } else {
        sentences(tokens.tail, currentSentence :+ token, currentSentences)
      }
    }
  }



}
