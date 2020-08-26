package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.histoutils._


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

// association of tagging strings for
// a given lexeme
case class LexemesHighlighter(
  lexemeIds: Vector[String],
  highlight: String
) extends Highlighter{

  def addHighlight(token: LatinParsedToken): Boolean = {
    token.matchesAny(lexemeIds)
  }

  def highlightString = highlight


}
