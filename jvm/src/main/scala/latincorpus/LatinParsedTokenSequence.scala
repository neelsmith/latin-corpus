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

trait LatinParsedTokenSequence extends LogSupport {
  //Logger.setDefaultLogLevel(LogLevel.WARN)

  /** Tokens contained in this sequence.*/
  def tokens: Vector[LatinParsedToken]

  /** List lexemes in this sequence sorted by Lewis-Short position.*/
  def lexemes: Vector[String] = {
    val lexemeIds = lexicalTokens.flatMap(t => t.analyses.map(a => a.lemmaId)).distinct
    LatinParsedTokenSequence.sortLexemes(lexemeIds)
  }
  def labelledLexemes : Vector[String] = {
    lexemes.map(lex => LewisShort.label(lex))
  }

  /** Alphabetized list of text of all lexical tokens in this sequence.
  *
  * @param caseSensitive True if list should be case sensitive.  If false,
  * resulting list is all lower case.
  */
  def vocabulary(caseSensitive: Boolean = false): Vector[String] = {
    if (caseSensitive) {
      lexicalTokens.map(t => t.text).distinct.sorted
    } else {
      lexicalTokens.map(t => t.text.toLowerCase).distinct.sorted
    }
  }
  /** Compute a Histogram of all lexical tokens in this sequence.
  *
  * @param caseSensitive True if items in Histogram should be case sensitive.  If false,
  * all items are lower case.
  */
  def tokensHistogram(caseSensitive : Boolean = true): Histogram[String] = {
    val grouped = if (caseSensitive) {
      lexicalTokens.groupBy(t => t.text)
    } else {
      lexicalTokens.groupBy(t => t.text.toLowerCase)
    }
    val counts = grouped.toVector.map{ case (t, v) =>   Frequency(t, v.size)
    }
    Histogram(counts).sorted
  }

  /** Flat list of every unique combination of individual lexeme
  * with individual token in this sequence's analyses. */
  def lexemeTokenPairings = {
    val idx = this.tokenLexemeIndex
    val lexemeVectorsWithTokens = this.analyzed.map(t => (idx(t.text), t.text))
    val distinctLexemesPlusTokens = lexemeVectorsWithTokens.flatMap{ case (v, t) => v.map(id => (id, t)) }.distinct
    distinctLexemesPlusTokens
  }

  def lexemesOnly : Vector[String]  = {
    analyzed.flatMap(t => t.analyses.map(a => a.lemmaId))
  }
  /** Compute a Histogram of all lexemeId values in this sequence. */
  def lexemesHistogram  : Histogram[String] = {
    val counts = lexemesOnly.groupBy(lex => lex).toVector.map{ case (lex, v) => Frequency(lex,v.size)}
    Histogram(counts).sorted
  }
  /** Compute a Histogram of all labelled lexemeId values in this sequence.*/
  def labelledLexemesHistogram : Histogram[String] = {
    val counts = lexemesOnly.groupBy(lex => lex).toVector.map{ case (lex, v) => Frequency(LewisShort.label(lex),v.size)}
    Histogram(counts).sorted
  }


  /** Index of tokens to a Vector of lexeme Ids.*/
  def tokenLexemeIndex : Map[String,Vector[String]] = {
    val analyzedForms = this.analyzed.map(t => (t.text, t.analyses.map(_.lemmaId).distinct))
    analyzedForms.groupBy(_._1).map{ case (s,v) => (s, v.map(_._2).flatten.distinct)}
  }
  /** Index of lexeme Ids to a Vector of tokens.*/
  def lexemeTokenIndex  : Map[String,Vector[String]] = {
    lexemeTokenPairings.groupBy(_._1).toVector.map{ case (k,v) => (k, v.map(_._2))}.toMap
  }

  /** Create a histogram of LemmatizedForms.*/
  def formsHistogram : Histogram[ValidForm] = {
    val freqs = formsOnly.groupBy(f => f).map{ case(k,v) => Frequency(k, v.size) }
    Histogram(freqs.toVector).sorted
  }

  def formsOnly = analyzed.flatMap(t => t.analyses.map(a => ValidForm(a.formUrn)))
  def forms: Vector[ValidForm] = {
    formsOnly.distinct
  }

  def citableFormsPerToken(umgr: UrnManager = LatinParsedTokenSequence.defaultUmgr) : Vector[Vector[CitableForm]]= {
    lexicalTokens.map(t => {
      if (t.analyses.isEmpty) {
        Vector(CitableForm(t.urn, None, t.text))
      } else {
        t.analyses.map(a => CitableForm(t.urn, Some(ValidForm(a.formUrn)), t.text))
      }
    })
  }

  def citableForms(umgr: UrnManager = LatinParsedTokenSequence.defaultUmgr): Vector[CitableForm] = {
    citableFormsPerToken(umgr).flatten
  }

  def functionStrings(umgr: UrnManager = LatinParsedTokenSequence.defaultUmgr) = {
    citableForms(umgr).map(_.functionString)
  }

  def functionStringsPerToken(umgr: UrnManager = LatinParsedTokenSequence.defaultUmgr) = {
    citableFormsPerToken(umgr).map( v => v.map(_.functionString))
  }

  def lexicalText: Vector[String] = {
    lexicalTokens.map(_.text)
  }


  /** True if one or more tokens matches any one or more
  * lexeme in a given list.
  *
  * @param lexemes List of lexemes to look for.
  */
  def matchesLexeme(lexemes: Vector[String]): Boolean = {
    LatinParsedTokenSequence.matchesLexeme(tokens, lexemes)
  }

  /** True if one or more tokens matches a given lexeme.
  *
  * @param lexeme Lexeme to test for.
  */
  def matchesLexeme(lexeme: String) : Boolean = {
    LatinParsedTokenSequence.matchesLexeme(tokens, lexeme)
  }

  /** Format time stamps with underscores as separators to
  * faciliate use in URNs.
  */
  val formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd")
  /** Time stamp to use in unique identifiers for automatically
  * generated records.
  */
  val todayFormatted = LocalDate.now.format(formatter)



  /** Represent this corpus as a sequence of CEX lines.
  *
  * @param umgr UrnManager to expand abbreviated identifiers.
  */
  def analysisUrns(umgr: UrnManager = LatinParsedTokenSequence.defaultUmgr): Vector[LemmatizedFormUrns] = tokens.flatMap(t => t.analysisUrns(umgr: UrnManager))
  def cexLines(umgr: UrnManager = LatinParsedTokenSequence.defaultUmgr, separator: String = "#") : Vector[String] = analysisUrns(umgr).map(_.cex(separator))

  def citeCollectionLines(umgr: UrnManager = LatinParsedTokenSequence.defaultUmgr, urnBase: String = "urn:cite2:linglat:tkns.v1:", separator: String = "#") = {
    val citable = for ( (ln, i) <- cexLines(umgr, separator).zipWithIndex) yield {
      val recordId = todayFormatted + "_" + i
      val urnStr = urnBase + recordId
      val label = "Record " + recordId
      urnStr + "#" + label + "#" + ln + "#" + i
    }
    citable
  }

  def cex(umgr: UrnManager = LatinParsedTokenSequence.defaultUmgr, urnBase: String = "urn:cite2:linglat:tkns.v1:", separator: String = "#") : String = {
    val header = "urn#label#passage#token#lexeme#form#category#sequence\n"
    header + citeCollectionLines(umgr, urnBase, separator).mkString("\n")
  }


  /** Compose a String highlighting a specified part of speech.
  *
  * @param label Labelling String identifying a part of speech
  * (more properly, an inflectional type).
  * @param hlOpen Opening (left) String marking highlighted token.
  * @param hlClose Closing (right) String marking highlighted
  * token.
  */
  def highlightPoS(label: String, hlOpen : String = "**", hlClose : String = "**") : String = {
    StringFormatter.highlightPoS(tokens, label, hlOpen, hlClose)
  }

  def formatSingleAnalysis(text: String, analysis: LemmatizedForm, highlighters: Vector[Highlighter]) : String =  {
    StringFormatter.formatSingleAnalysis(tokens, text, analysis, highlighters)
  }


  def highlight(tkn: LatinParsedToken, highlighters: Vector[Highlighter]) : String  = {
    StringFormatter.highlight(tokens, tkn, highlighters)
  }


  def highlightForms(highlighters: Vector[Highlighter]) : String = {
    StringFormatter.highlightForms(tokens, highlighters)
  }


  /*def highlightForms(mf : MorphologyFilter,
    hlOpen : String = "**",
    hlClose : String = "**") : String= {
      */


  /** Attach HTML markup to effect display of form information when
  * mouse is over a token.
  *
  * @param mfs Vector of filters to apply.
  * @param color Color to use in HTML highlighting.
  */
  //def hover(mfs : Vector[MorphologyFilter],
      // color: String = "green") : String = {


  /** All tokens with at least one morphological analysis.*/
  lazy val analyzed: Vector[LatinParsedToken] = tokens.filter(_.analyses.nonEmpty)

  /** All lexical tokens.*/
  lazy val lexicalTokens: Vector[LatinParsedToken] = tokens.filter(t => t.category == LexicalToken)

  /** Total number of tokens.*/
  lazy val size : Int = tokens.size

  /** Tokens with a single morphological analysis.*/
  lazy val singleAnalysis: Vector[LatinParsedToken] = {
    tokens.filter(_.analyses.size == 1)
  }

  /** Tokens with no morphological anlayses.
  */
  lazy val noAnalysis: Vector[LatinParsedToken] = {
    tokens.filter(_.analyses.isEmpty)
  }

  /** Tokens with more than one morphological analysis.*/
  lazy val mutipleAnalyses: Vector[LatinParsedToken] = {
    tokens.filter(_.analyses.size > 1)
  }

  /** Measure of ambigutiy/uniqueness of tokens.*/
  lazy val tokenAmbiguity: Double = {
    allAnalyses.size / analyzed.size.toDouble
  }


  /** List of all morphological analyses.*/
  lazy val allAnalyses: Vector[LemmatizedForm] =  analyzed.flatMap(_.analyses)

  /** Reduce analyzed tokens to citable node + lexeme.*/
  //def lexemesOnly:  Vector[(CitableNode, Vector[String])] = analyzed.map(t => (t.cn, t.analyses.map(_.lemmaId).distinct) )

  def multipleLexemes:  Vector[LatinParsedToken] = {
    analyzed.filter( _.analyses.map(_.lemmaId).distinct.size > 1)
  }

  def singleLexeme:  Vector[LatinParsedToken] = {
    analyzed.filter( _.analyses.map(_.lemmaId).distinct.size == 1)
  }


  lazy val lexicalAmbiguity: Double = {
    analyzed.size / singleLexeme.size.toDouble
  }

  def matchesFunctionStrings(requiredFunctions: Vector[String],
  urnManager : UrnManager = LatinParsedTokenSequence.defaultUmgr): Boolean = {
    LatinParsedTokenSequence.matchesFunctionStrings(this, requiredFunctions, urnManager)
  }

  def matchesFunctionStringLists(requiredFunctions: Vector[Vector[String]],
  urnManager : UrnManager = LatinParsedTokenSequence.defaultUmgr ): Boolean = {
    LatinParsedTokenSequence.matchesFunctionStringLists(this, requiredFunctions, urnManager)
  }


  /** Verb tokens only*/
  lazy val verbs = {
    analyzed.filter(_.analyses(0).posLabel == "verb")
  }


 def tenseMoodValues : Vector[TenseMood] = {
  //verbs.flatMap(_.analyses.flatMap(a => (a.verbTense, a.verbMood) )).distinct
  Vector.empty[(TenseMood)]
 }

 /** Collect distinct values for a class of MorphologicalCategoryValues.
 *
 * @param prop Morphological property to collect values for.
 */
 def valuesForCategory(prop: MorphologicalCategoryValues): Vector[MorphologicalProperty] = {
   val matches = prop.name match {

     case "case" => {
       // Collect all values for GrammaticalCase in all substantives:
       val nounCases: Vector[GrammaticalCase] = nouns.flatMap(_.analyses.flatMap(a => a.substantiveCase)).distinct
       val pronounCases = pronouns.flatMap(_.analyses.flatMap(a => a.substantiveCase)).distinct
       val adjectiveCases = adjectives.flatMap(_.analyses.flatMap(a => a.substantiveCase)).distinct

       // Add gerund and supine?
       (nounCases ++ pronounCases ++ adjectiveCases)
     }
     case "gender" => {
       val nounGenders: Vector[Gender] = nouns.flatMap(_.analyses.flatMap(a => a.substantiveGender)).distinct
       val pronounGenders = pronouns.flatMap(_.analyses.flatMap(a => a.substantiveGender)).distinct
       val adjectiveGenders = adjectives.flatMap(_.analyses.flatMap(a => a.substantiveGender)).distinct
       (nounGenders ++ pronounGenders ++ adjectiveGenders)
     }

     case "person" => {
       verbs.flatMap(_.analyses.flatMap(a => a.verbPerson))
     }

     case "tense" => {
       val verbTenses = verbs.flatMap(v => v.analyses.flatMap( a => a.verbTense))
       val ptcplTenses = participles.flatMap(p => p.analyses.flatMap( a => a.participleTense))
       val infinTenses = infinitives.flatMap(i => i.analyses.flatMap(a => a.infinitiveTense))

       (verbTenses ++ ptcplTenses ++ infinTenses).distinct

       }

       case "mood" => verbs.flatMap(_.analyses.flatMap(a => a.verbMood))

       case "voice" => {
         val verbVoices = verbs.flatMap(v => v.analyses.flatMap( a => a.verbVoice))
         val ptcplVoices = participles.flatMap(p => p.analyses.flatMap( a => a.participleVoice))
         val infinVoices = infinitives.flatMap(i => i.analyses.flatMap(a => a.infinitiveVoice))

         (verbVoices ++ ptcplVoices ++ infinVoices).distinct
       }
     }
     matches

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

  val css = "<style>\na.hoverclass {\nposition: relative ;\n}\na.hoverclass:hover::after {\n content: attr(data-tooltip) ;\n position: absolute ;\n  top: 1.1em ;\n  left: 1em ;\n  min-width: 200px ;\n  border: 1px #808080 solid ;\n  padding: 8px ;\n  z-index: 1 ;\n  color: silver;\n  background-color: white;\n}\n</style>\n"



  /** Compute percent as a Double to a given scale.
  *
  * @param n Number.
  * @param total Total of which n is some percent.
  * @param scale Scale for resulting Double.
  */
  def dblPercent(n: Int, total: Int, scale: Int = 2) : Double = {
    val flt = ((n / total.toDouble) * 100).toDouble

    BigDecimal(flt).setScale(scale, BigDecimal.RoundingMode.HALF_UP).toDouble
  }


  /** Compute percent as an Int.
  *
  * @param n Number.
  * @param total Total of which n is some percent.
  */
  def percent(n: Int, total: Int) : Int = {
    ((n / total.toDouble) * 100).toInt
  }
}

object LatinParsedTokenSequence extends LogSupport {

  /** True if one or more tokens matches any one or more
  * lexeme in a given list.
  *
  * @param lexemes List of lexemes to look for.
  */
  def matchesLexeme(tokens: Vector[LatinParsedToken], lexeme: String) : Boolean = {
    val tf = for (t <- tokens) yield {
      t.matchesLexeme(lexeme)
    }
    tf.contains(true)
  }

  /** True if one or more tokens matches a given lexeme.
  *
  * @param lexeme Lexeme to test for.
  */
  def matchesLexeme(tokens: Vector[LatinParsedToken], lexemes: Vector[String]) : Boolean = {
    val tf = for (t <- tokens) yield {
      t.matchesAny(lexemes)
    }
    tf.contains(true)
  }

  val defaultAbbrs = Vector(
  "abbr#full",
  "ls#urn:cite2:tabulae:ls.v1:"
  )
  val defaultUmgr = UrnManager(defaultAbbrs)

  def matchesFunctionStrings(tokenSequence: LatinParsedTokenSequence, requiredFunctions: Vector[String],
  urnManager : UrnManager = defaultUmgr): Boolean = {
    //Logger.setDefaultLogLevel(LogLevel.DEBUG)
    val tokenFunctions = tokenSequence.functionStrings(urnManager)
    debug("F STRINGS: \n " + tokenFunctions.mkString("\n"))
    val lcs = SequenceComp(tokenFunctions, requiredFunctions).lcs
    //Logger.setDefaultLogLevel(LogLevel.WARN)
    (lcs == requiredFunctions)
  }

  def filterFunctionStrings(
    clusters: Vector[LatinParsedTokenSequence],
    requiredFunctions: Vector[String],
    urnManager : UrnManager = defaultUmgr) :  Vector[LatinParsedTokenSequence] = {

    clusters.filter(cluster => matchesFunctionStrings(cluster, requiredFunctions, urnManager))
  }

  // recursively apply lists of required function to "and" requirements together.
  // as long as requirement is matched, keep recursing.
  def matchesFunctionStringLists(tokenSequence: LatinParsedTokenSequence,
    requiredFunctions: Vector[Vector[String]],
    urnManager : UrnManager = defaultUmgr) : Boolean = {

    if (requiredFunctions.isEmpty) {
      return(true)

    } else {
      val currentMatches = matchesFunctionStrings(tokenSequence, requiredFunctions.head, urnManager)
      if (currentMatches) { // continue recursing through requirements as long as we pass:
        matchesFunctionStringLists(tokenSequence, requiredFunctions.tail)
      } else {
        false
      }
    }
  }

  def filterFunctionStringLists(clusters: Vector[LatinParsedTokenSequence],
    requiredFunctions: Vector[Vector[String]],
    urnManager : UrnManager = defaultUmgr) :    Vector[LatinParsedTokenSequence] = {

    clusters.filter(cluster => matchesFunctionStringLists(cluster, requiredFunctions, urnManager))
  }

  def matchesForm(tokens: Vector[LatinParsedToken]) : Boolean = {
    false
  }


  /** Recursively replace instances of a list of Strings in a given String.
  *
  * @param s String to strip down.
  * @param prefixes List of prefixes to remove.
  */
  def stripPrefix(s: String, prefixes: Vector[String]): String = {
    if (prefixes.isEmpty) {
      s
    } else {
      stripPrefix(s.replaceFirst(prefixes.head, ""), prefixes.tail)
    }
  }

  /** Sort a list of lexemes identified following convention
  * used with Lewis-Short IDs, where a text prefix is followed by
  * a parseable, sortable numeric string.
  *
  * @param lexIds List of identifiers to sort.
  * @param prefixes List of prefixing strings to strip off the
  * beginning of identifiers.
  */
  def sortLexemes(lexIds: Vector[String], prefixes: Vector[String] = Vector("ls.n")): Vector[String] = {
    val paired = for (lexId <- lexIds.filter(_.nonEmpty)) yield {
      val lexString = stripPrefix(lexId, prefixes).replaceAll("[a-z]+$","")
      try {
        val lexNum = lexString.toInt
        (lexNum, lexId)
      } catch {
        case nfe: NumberFormatException => {
          warn("No  configuration to sort lexeme ID " + lexId)
          (0, lexId)
        }

        case t: Throwable => {
          warn(t)
          warn("No  configuration to sort lexeme ID " + lexId)
          (0, lexId)
        }
      }
    }
    // sort by number (part 1), produce entry only (part 2)
    val sorted = for (l <- paired.sortBy(_._1).distinct) yield { l._2 }
    sorted
  }


}
