package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._

trait LatinTokenSequence {
  def tokens: Vector[LatinToken]

  lazy val analyzed = tokens.filter(_.analyses.nonEmpty)

  lazy val lexicalTokens = tokens.filter(_.category == LexicalToken)

  lazy val size : Int = tokens.size

  lazy val singleAnalysis = {
    tokens.filter(_.analyses.size == 1)
  }
  lazy val noAnalysis = {
    tokens.filter(_.analyses.isEmpty)
  }
  lazy val mutipleAnalyses = {
    tokens.filter(_.analyses.size > 1)
  }

  lazy val missingAnalysis = {
    lexicalTokens.filter(_.analyses.isEmpty)
  }

  lazy val allAnalyses =  analyzed.flatMap(_.analyses)

  lazy val tokenAmbiguity = {
    allAnalyses.size / analyzed.size.toDouble
  }

  lazy val verbs = {
    analyzed.filter(_.analyses(0).posLabel == "verb")
  }
  lazy val nouns = {
    analyzed.filter(_.analyses(0).posLabel == "noun")
  }
  lazy val adjectives = {
    analyzed.filter(_.analyses(0).posLabel == "adjective")
  }
  lazy val adverbs = {
    analyzed.filter(_.analyses(0).posLabel == "adverb")
  }
  lazy val indeclinables = {
    analyzed.filter(_.analyses(0).posLabel == "indeclinable")
  }
  lazy val participles = {
    analyzed.filter(_.analyses(0).posLabel == "participle")
  }
  lazy val infintives = {
    analyzed.filter(_.analyses(0).posLabel == "infintive")
  }
  lazy val gerundives = {
    analyzed.filter(_.analyses(0).posLabel == "gerundive")
  }
  lazy val gerunds = {
  analyzed.filter(_.analyses(0).posLabel == "gerund")
  }
  lazy val supines = {
    analyzed.filter(_.analyses(0).posLabel == "supine")
  }

}
