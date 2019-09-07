package edu.holycross.shot.latincorpus

import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._
import edu.holycross.shot.histoutils._


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

trait LatinTokenSequence extends LogSupport {
  def tokens: Vector[LatinToken]


  


  def highlightPoS(label: String, hlOpen : String = "**", hlClose : String = "**") = {
    val hilited = tokens.map(t => {
      if (t.analyses.map(_.posLabel).contains(label)) {
        s"${hlOpen}${t.text}${hlClose}"
      } else {
        t.text
      }
    })
    hilited.mkString(" ")
  }




  def formatSingleAnalysis(text: String, analysis: LemmatizedForm, highlighters: Vector[Highlighter])  =  {
    val formatted = for  (hl <- highlighters) yield {
      if (hl.mf.agrees(analysis)) {
        (true, hl.opening + text + hl.closing)
      } else {
      (false, text)
      }
    }
    val flattened = formatted.filter(_._1 == true).map(_._2)

    if (flattened.isEmpty) {
      ""
    } else {
      flattened(0)
    }
  }


  def highlight(tkn: LatinToken, highlighters: Vector[Highlighter]) : String  = {
    val formatted =  for (a <- tkn.analyses) yield {
      formatSingleAnalysis(tkn.text, a, highlighters)
    }
    if (formatted.flatten.isEmpty) {
      tkn.text
    } else {
      formatted(0)
    }
  }

        /*val matched = formatted.filter(_._1 == true)
        if (matched.isEmpty) {
          text
        } else {
          matched(0)._2
        }*/

/*
    val matchingForms = tkn.analyses.map(a => {

    })
    val hilites = matchingForms.filter(_.nonEmpty)
    if (hilites.nonEmpty) {
      hilites(0)
    } else {
      tkn.text
    }
  }*/

  def highlightForms(highlighters: Vector[Highlighter]) = {
    Logger.setDefaultLogLevel(LogLevel.DEBUG)
    val hilited = tokens.map(t => {
      t.category.toString match {
        case "PunctuationToken" => {
          t.text.trim
        }
        case _ => {
          " " + highlight(t, highlighters)
        }
      }
    })
    hilited.mkString("").trim
  }


  def highlightForms(mf : MorphologyFilter,
    hlOpen : String = "**",
    hlClose : String = "**") = {
    Logger.setDefaultLogLevel(LogLevel.WARN)

    val hilited = tokens.map(t => {
      t.category.toString match {
        case "PunctuationToken" => {
          t.text.trim
        }
        case _ => {
          val formsMatch = t.analyses.map(mf.agrees(_))

          if (formsMatch.contains(true)) {
            s" ${hlOpen}${t.text}${hlClose}"
          } else {
            " " + t.text
          }
        }
      }
    })


    debug("Highlighted toekns: " + hilited.mkString("").trim)
    hilited.mkString("").trim
  }






  def matchesAny(lexemes: Vector[String]) = {
    val tf = for (t <- tokens) yield {
      t.matchesAny(lexemes)
    }
    tf.contains(true)
  }

  def matchesLexeme(lexeme: String) : Boolean = {
    val tf = for (t <- tokens) yield {
      t.matchesLexeme(lexeme)
    }
    tf.contains(true)
  }

  /** All tokens with at least one morphological analysis.*/
  lazy val analyzed = tokens.filter(_.analyses.nonEmpty)

  /** All lexical tokens.*/
  lazy val lexicalTokens = tokens.filter(_.category == LexicalToken)

  /** Total number of tokens.*/
  lazy val size : Int = tokens.size

  /** Tokens with a single morphological analysis.*/
  lazy val singleAnalysis = {
    tokens.filter(_.analyses.size == 1)
  }

  /** Tokens with no morphological anlayses.
  */
  lazy val noAnalysis = {
    tokens.filter(_.analyses.isEmpty)
  }

  /** Tokens with more than one morphological analysis.*/
  lazy val mutipleAnalyses = {
    tokens.filter(_.analyses.size > 1)
  }

  /** Lexical tokens without a morphological anlaysis. */
  lazy val missingAnalysis = {
    lexicalTokens.filter(_.analyses.isEmpty)
  }

  /** Measure of ambigutiy/uniqueness of tokens.*/
  lazy val tokenAmbiguity = {
    allAnalyses.size / analyzed.size.toDouble
  }


  /** List of all morphological analyses.*/
  lazy val allAnalyses =  analyzed.flatMap(_.analyses)

  /** Reduce analyzed tokens to citable node + lexeme.*/
  def lexemesOnly = analyzed.map(t => (t.cn, t.analyses.map(_.lemmaId).distinct) )

  def multipleLexemes = {
    analyzed.filter( _.analyses.map(_.lemmaId).distinct.size > 1)
  }

  def singleLexeme = {
    analyzed.filter( _.analyses.map(_.lemmaId).distinct.size == 1)
  }


  def lexemeToFormsHistogram: Histogram[String] = {
    val freqs : Vector[Frequency[String]] = lexemesOnly.flatMap(_._2).groupBy(s => s).toVector.map{ case (k,v) => Frequency(k, v.size) }
    Histogram(freqs)
  }


  lazy val lexicalAmbiguity = {
    analyzed.size / singleLexeme.size.toDouble
  }

  /** Compute frequency of individual forms.*/
  def formsHistogram  : Histogram[String] = {
    val freqs : Vector[Frequency[String]] = allAnalyses.map(_.toString).groupBy(s => s).toVector.map{ case (k,v) => Frequency(k, v.size) }
    Histogram(freqs)
  }


  /** Verb tokens only*/
  lazy val verbs = {
    analyzed.filter(_.analyses(0).posLabel == "verb")
  }

  /** Noun tokens only*/
  lazy val nouns = {
    analyzed.filter(_.analyses(0).posLabel == "noun")
  }

  /** Adjective tokens only*/
  lazy val adjectives = {
    analyzed.filter(_.analyses(0).posLabel == "adjective")
  }

  /** Adverb tokens only*/
  lazy val adverbs = {
    analyzed.filter(_.analyses(0).posLabel == "adverb")
  }

  /** Adverb tokens only*/
  lazy val pronouns = {
    analyzed.filter(_.analyses(0).posLabel == "pronoun")
  }

  /** Uninflected lexical tokens only*/
  lazy val indeclinables = {
    analyzed.filter(_.analyses(0).posLabel == "indeclinable")
  }

  /** Participle tokens only*/
  lazy val participles = {
    analyzed.filter(_.analyses(0).posLabel == "participle")
  }

  /** Infinitive tokens only*/
  lazy val infinitives = {
    analyzed.filter(_.analyses(0).posLabel == "infintive")
  }

  /** Gerundive tokens only*/
  lazy val gerundives = {
    analyzed.filter(_.analyses(0).posLabel == "gerundive")
  }

  /** Gerund tokens only*/
  lazy val gerunds = {
  analyzed.filter(_.analyses(0).posLabel == "gerund")
  }

  /** Supine tokens only*/
  lazy val supines = {
    analyzed.filter(_.analyses(0).posLabel == "supine")
  }

}
