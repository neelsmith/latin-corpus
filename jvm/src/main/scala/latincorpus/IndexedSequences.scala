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


case class IndexedSequence(sequence: LatinParsedTokenSequence, index: Int)  {
  def size: Int = sequence.size
}


case class IndexedSequences(sequences: Vector[IndexedSequence]) extends LogSupport {

}

object IndexedSequences {

  def fromSequences(sequences: Vector[LatinParsedTokenSequence]): IndexedSequences = {
    val indexed = sequences.zipWithIndex.map{ case (s, i) => IndexedSequence(s,i)}
    IndexedSequences(indexed)
  }
}
