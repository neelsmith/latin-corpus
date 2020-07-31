package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.histoutils._
import edu.holycross.shot.latin._


/**
*
*
*/
case class CorpusProfiler(latinCorpus: LatinCorpus) {


  def pnOption(personOpt: Option[Person], grammaticalNumberOpt: Option[GrammaticalNumber] ) :  Option[PersonNumber] = {
    if ((personOpt == None) || (grammaticalNumberOpt == None)) {
      None
    } else {
      Some(PersonNumber(personOpt.get, grammaticalNumberOpt.get))
    }
  }

  def tmOption(tenseOpt: Option[Tense], moodOpt: Option[Mood] ) :  Option[TenseMood] = {
    if ((tenseOpt == None) || (moodOpt == None)) {
      None
    } else {
      Some(TenseMood(tenseOpt.get, moodOpt.get))
    }
  }

  /**
  */
  def profilePersonNumber :  Histogram[PersonNumber]= {
    val pnOpts = latinCorpus.analyzed.map(t => t.analyses.map(a => pnOption(a.verbPerson, a.verbNumber)))
    val personNumbers = pnOpts.flatten.flatten
    val freqs:  Iterable[Frequency[PersonNumber]] = personNumbers.groupBy(pn => pn).map{ case (k,v) => Frequency(k, v.size) }
    Histogram(freqs.toVector)
  }

  def profileTenseMood = {
    val tmOpts = latinCorpus.analyzed.map(t => t.analyses.map(a => tmOption(a.verbTense, a.verbMood)))
    val tenseMoods = tmOpts.flatten.flatten
    val freqs:  Iterable[Frequency[TenseMood]] = tenseMoods.groupBy(pn => pn).map{ case (k,v) => Frequency(k, v.size) }
    Histogram(freqs.toVector)
  }
  def profileVoice = {}

  /** Compute a histogram of grammatical cases.  The
  * histogram counts every *possible* analysis, so that
  * tokens with multiple analyses are counted multiple times.
  */
  def profileCase: Histogram[GrammaticalCase] = {
    val caseList = latinCorpus.analyzed.map(t => t.analyses.map(_.substantiveCase).flatten).flatten
    val freqs : Iterable[Frequency[GrammaticalCase]]= caseList.groupBy(c => c).map{ case (k,v) => Frequency(k,v.size) }
    Histogram(freqs.toVector)
  }
}
