package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.histoutils._

import edu.holycross.shot.seqcomp._

import java.time.LocalDate
import java.time.format.DateTimeFormatter


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter





case class ParsedSequenceCollection(sequences: Vector[LatinParsedTokenSequence]) extends LogSupport {

  def size = sequences.size

  def moreLike(txt: String) = {
    
  }
}
