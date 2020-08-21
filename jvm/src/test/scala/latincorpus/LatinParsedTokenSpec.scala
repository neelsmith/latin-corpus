
package edu.holycross.shot.latincorpus

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._

import org.scalatest.FlatSpec

class LatinParsedTokenSpec extends FlatSpec {

  val urn = CtsUrn("urn:cts:latinLit:phi0959.phi006:1.1.1")
  val cn = CitableNode(urn,"In")
  val indeclForm = IndeclinableForm("ls.n22111", "latcommon.n22111","latcommon.indeclinfl1", Preposition)

  val parsedToken = LatinParsedToken(cn, LexicalToken, Vector(indeclForm))


  val urnManagerFile = "jvm/src/test/resources/urnregistry.cex"
  val urnManager = UrnManager.fromFile(urnManagerFile)



  "A LatinParsedToken" should "have a URN" in {
    assert(parsedToken.urn == urn)
  }
  it should "have a text reading" in {
    assert(parsedToken.text == "In")
  }
  it should "have a token category" in {
    assert(parsedToken.category == LexicalToken)
  }
  it should "have a vector of analyses" in {
    val expectedAnalyses = 1
    assert(parsedToken.analyses.size == expectedAnalyses)
  }
  it should "have extended LemmatizedForms in the analysis vector" in {
    val analysis = parsedToken.analyses(0)

    val indeclAnalysis : IndeclinableForm = analysis match {
      case indecl : IndeclinableForm => indecl
      case _ => throw new Exception("Hey, that wasn't an indeclinable!")
    }
    assert(indeclAnalysis.pos == Preposition)
  }

  it should "determine if it has an analysis to give lexeme" in {
    val lexeme = "ls.n22111"
    assert(parsedToken.matchesLexeme(lexeme))
    val bogus = "ls.notforreal"
    assert(parsedToken.matchesLexeme(bogus) == false)
  }

  it should "determine if it has an analysis matching any of a list of lexemes" in {
    val lexeme = "ls.n22111"
    val bogus = "ls.notforreal"
    val mixed = Vector(lexeme, bogus)
    assert(parsedToken.matchesAny(mixed))

    val noneatall = Vector(bogus, "ls.anotherfake")
    assert(parsedToken.matchesAny(noneatall) == false)
  }

  it should "extract an option for the analytical combinationa TenseMood" in {
    val cex = Vector("urn:cite2:linglat:tkns.v1:2020_08_02_44077#Record 2020_08_02_44077#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:196a.1.19#deciperent#urn:cite2:tabulae:ls.v1:n12498#urn:cite2:tabulae:morphforms.v1:322210004#LexicalToken#44077")
    val corpus = LatinCorpus(cex)
    val verb = corpus.tokens.head
    println(verb.tenseMood)
  }

  it should "report if the form of the token matches a form specified by URN" in {
    val cex = Vector("urn:cite2:linglat:tkns.v1:2020_08_02_44077#Record 2020_08_02_44077#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:196a.1.19#deciperent#urn:cite2:tabulae:ls.v1:n12498#urn:cite2:tabulae:morphforms.v1:322210004#LexicalToken#44077")
    val corpus = LatinCorpus(cex)
    val verb = corpus.tokens.head

    val expectedTrue  = Cite2Urn("urn:cite2:tabulae:morphforms.v1:322210004")
    assert(verb.hasForm(expectedTrue, urnManager))
    val expectedFalse  = Cite2Urn("urn:cite2:tabulae:morphforms.v1:222210004")
    assert(verb.hasForm(expectedFalse, urnManager) == false)
   }

   it should "determine if an token is analyzed as a finite verb" in {
     val node = Vector("urn:cite2:linglat:tkns.v1:2020_08_14_25013#Record 2020_08_14_25013#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.8#possent#urn:cite2:tabulae:ls.v1:n37193#urn:cite2:tabulae:morphforms.v1:322210004#LexicalToken#25013")
     val oneNode = LatinCorpus(node)
     assert(oneNode.tokens.head.finiteVerb)
     assert(oneNode.finiteVerbs.size == 1)
     assert(oneNode.tokens.head.verbal)
   }

   it should "determine if an token is analyzed as a infinitive" in {
     val node = Vector("urn:cite2:linglat:tkns.v1:2020_08_14_25010#Record 2020_08_14_25010#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.6#capere#urn:cite2:tabulae:ls.v1:n6614#urn:cite2:tabulae:morphforms.v1:211120004#LexicalToken#25010",
     "urn:cite2:linglat:tkns.v1:2020_08_14_25011#Record 2020_08_14_25011#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.6#capere#urn:cite2:tabulae:ls.v1:n6614#urn:cite2:tabulae:morphforms.v1:001010006#LexicalToken#25011")
     val oneNode = LatinCorpus(node)
     assert(oneNode.tokens.head.infinitive)
     assert(oneNode.infinitives.size == 1)
     assert(oneNode.tokens.head.verbal)
   }

   it should "determine if an token is analyzed as a participle" in {
     val node = Vector("urn:cite2:linglat:tkns.v1:2020_08_14_25029#Record 2020_08_14_25029#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.19#collecti#urn:cite2:tabulae:ls.v1:n9072#urn:cite2:tabulae:morphforms.v1:024021105#LexicalToken#25029")
     val oneNode = LatinCorpus(node)
     assert(oneNode.tokens.head.participle)
     assert(oneNode.participles.size == 1)
     assert(oneNode.tokens.head.verbal)
   }

   it should "determine if an token is analyzed as a gerund" in {
     val node = Vector(
       "urn:cite2:linglat:tkns.v1:2020_08_15_50645#Record 2020_08_15_50645#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:257_237.12.4#interficiendum#urn:cite2:tabulae:ls.v1:n24257#urn:cite2:tabulae:morphforms.v1:010001407#LexicalToken#50645",
       "urn:cite2:linglat:tkns.v1:2020_08_15_50646#Record 2020_08_15_50646#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:257_237.12.4#interficiendum#urn:cite2:tabulae:ls.v1:n24257#urn:cite2:tabulae:morphforms.v1:010003107#LexicalToken#50646",
       "urn:cite2:linglat:tkns.v1:2020_08_15_50647#Record 2020_08_15_50647#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:257_237.12.4#interficiendum#urn:cite2:tabulae:ls.v1:n24257#urn:cite2:tabulae:morphforms.v1:010003107#LexicalToken#50647",
       "urn:cite2:linglat:tkns.v1:2020_08_15_50648#Record 2020_08_15_50648#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:257_237.12.4#interficiendum#urn:cite2:tabulae:ls.v1:n24257#urn:cite2:tabulae:morphforms.v1:010003407#LexicalToken#50648",
       "urn:cite2:linglat:tkns.v1:2020_08_15_50649#Record 2020_08_15_50649#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:257_237.12.4#interficiendum#urn:cite2:tabulae:ls.v1:n24257#urn:cite2:tabulae:morphforms.v1:000000408#LexicalToken#50649")
     val oneNode = LatinCorpus(node)
     assert(oneNode.tokens.head.gerund)
     assert(oneNode.gerunds.size == 1)
     assert(oneNode.tokens.head.verbal)
   }
   it should "determine if an token is analyzed as a gerundive" in {
     val node = Vector(
       "urn:cite2:linglat:tkns.v1:2020_08_15_50645#Record 2020_08_15_50645#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:257_237.12.4#interficiendum#urn:cite2:tabulae:ls.v1:n24257#urn:cite2:tabulae:morphforms.v1:010001407#LexicalToken#50645",
       "urn:cite2:linglat:tkns.v1:2020_08_15_50646#Record 2020_08_15_50646#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:257_237.12.4#interficiendum#urn:cite2:tabulae:ls.v1:n24257#urn:cite2:tabulae:morphforms.v1:010003107#LexicalToken#50646",
       "urn:cite2:linglat:tkns.v1:2020_08_15_50647#Record 2020_08_15_50647#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:257_237.12.4#interficiendum#urn:cite2:tabulae:ls.v1:n24257#urn:cite2:tabulae:morphforms.v1:010003107#LexicalToken#50647",
       "urn:cite2:linglat:tkns.v1:2020_08_15_50648#Record 2020_08_15_50648#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:257_237.12.4#interficiendum#urn:cite2:tabulae:ls.v1:n24257#urn:cite2:tabulae:morphforms.v1:010003407#LexicalToken#50648",
       "urn:cite2:linglat:tkns.v1:2020_08_15_50649#Record 2020_08_15_50649#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:257_237.12.4#interficiendum#urn:cite2:tabulae:ls.v1:n24257#urn:cite2:tabulae:morphforms.v1:000000408#LexicalToken#50649")
     val oneNode = LatinCorpus(node)
     assert(oneNode.tokens.head.gerundive)
     assert(oneNode.gerundives.size == 1)
     assert(oneNode.tokens.head.verbal)
   }
   it should "determine if an token is analyzed as a supine" in {
     val node = Vector(
       "urn:cite2:linglat:tkns.v1:2020_08_15_3539#Record 2020_08_15_3539#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.4.37#ictu#urn:cite2:tabulae:ls.v1:n21333#urn:cite2:tabulae:morphforms.v1:010001300#LexicalToken#3539",
       "urn:cite2:linglat:tkns.v1:2020_08_15_3540#Record 2020_08_15_3540#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.4.37#ictu#urn:cite2:tabulae:ls.v1:n21333#urn:cite2:tabulae:morphforms.v1:010001500#LexicalToken#3540",
       "urn:cite2:linglat:tkns.v1:2020_08_15_3541#Record 2020_08_15_3541#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.4.37#ictu#urn:cite2:tabulae:ls.v1:n21321#urn:cite2:tabulae:morphforms.v1:000000509#LexicalToken#3541"
     )
     val oneNode = LatinCorpus(node)
     assert(oneNode.tokens.head.supine)
     assert(oneNode.supines.size == 1)
     assert(oneNode.tokens.head.verbal)

   }
   it should "determine if an token is analyzed as a pronoun" in {
     val node = Vector(
       "urn:cite2:linglat:tkns.v1:2020_08_14_25059#Record 2020_08_14_25059#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.2.0#id#urn:cite2:tabulae:ls.v1:n25029#urn:cite2:tabulae:morphforms.v1:010003101#LexicalToken#25059",
       "urn:cite2:linglat:tkns.v1:2020_08_14_25060#Record 2020_08_14_25060#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.2.0#id#urn:cite2:tabulae:ls.v1:n25029#urn:cite2:tabulae:morphforms.v1:010003401#LexicalToken#25060"
     )
     val oneNode = LatinCorpus(node)
     assert(oneNode.tokens.head.pronoun)
     assert(oneNode.pronouns.size == 1)
     assert(oneNode.tokens.head.substantive)
   }
   it should "determine if an token is analyzed as a noun" in {
     val node = Vector("urn:cite2:linglat:tkns.v1:2020_08_14_25007#Record 2020_08_14_25007#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.4#annos#urn:cite2:tabulae:ls.v1:n2698#urn:cite2:tabulae:morphforms.v1:010001100#LexicalToken#25007")
     val oneNode = LatinCorpus(node)
     assert(oneNode.tokens.head.noun)
     assert(oneNode.nouns.size == 1)
     assert(oneNode.tokens.head.substantive)
   }
   it should "determine if an token is analyzed as an adjective" in {
     val node = Vector("urn:cite2:linglat:tkns.v1:2020_08_14_25019#Record 2020_08_14_25019#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.13#mirae#urn:cite2:tabulae:ls.v1:n29235#urn:cite2:tabulae:morphforms.v1:020002112#LexicalToken#25019")
     val oneNode = LatinCorpus(node)
     assert(oneNode.tokens.head.adjective)
     assert(oneNode.adjectives.size == 1)
     assert(oneNode.tokens.head.substantive)
   }


   it should "determine if an token is analyzed as an uninflected preposition" in {
     val node = Vector("urn:cite2:linglat:tkns.v1:2020_08_14_25080#Record 2020_08_14_25080#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.2.10#in#urn:cite2:tabulae:ls.v1:n22111#urn:cite2:tabulae:morphforms.v1:00000000B#LexicalToken#25080")
     val oneNode = LatinCorpus(node)
     assert(oneNode.tokens.head.uninflected)
     assert(oneNode.tokens.head.preposition)
     assert(oneNode.prepositions.size == 1)
     assert(oneNode.uninflecteds.size == 1)
   }

   it should "determine if an token is analyzed as an uninflected conjunction" in {
     val node = Vector("urn:cite2:linglat:tkns.v1:2020_08_14_25092#Record 2020_08_14_25092#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.2.18#ut#urn:cite2:tabulae:ls.v1:n49975#urn:cite2:tabulae:morphforms.v1:00000000A#LexicalToken#25092")
     val oneNode = LatinCorpus(node)
     assert(oneNode.tokens.head.uninflected)
     assert(oneNode.tokens.head.conjunction)
     assert(oneNode.conjunctions.size == 1)
     assert(oneNode.uninflecteds.size == 1)
   }



   it should "determine if an token is analyzed as an adverb" in {
     val node = Vector("urn:cite2:linglat:tkns.v1:2020_08_14_25127#Record 2020_08_14_25127#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.2.31#non#urn:cite2:tabulae:ls.v1:n31151#urn:cite2:tabulae:morphforms.v1:000000013#LexicalToken#25127")
     val oneNode = LatinCorpus(node)
     assert(oneNode.tokens.head.adverb)
     assert(oneNode.adverbs.size == 1)
   }






}
