package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._

case class LatinSentence(tokens: Vector[LatinParsedToken]) extends LatinParsedTokenSequence {
}

object LatinSentence {

    def sentences(tokens: Vector[LatinParsedToken],
      currentSentence: Vector[LatinParsedToken] = Vector.empty[LatinParsedToken],
      currentSentences: Vector[LatinSentence] = Vector.empty[LatinSentence]) : Vector[LatinSentence] = {

      if (tokens.isEmpty) {
        currentSentences :+ LatinSentence(currentSentence)

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
