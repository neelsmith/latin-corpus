import edu.holycross.shot.latincorpus._
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.latin._
val hyginusUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-latc.cex"
val hyginus = LatinCorpus.fromUrl(hyginusUrl)

val urnManagerUrl = "https://raw.githubusercontent.com/neelsmith/tabulae/master/jvm/src/test/resources/datasets/analytical_types/urnregistry/collectionregistry.cex"
val manager = UrnManager.fromUrl(urnManagerUrl)




val lexcorp = LatinCorpus(hyginus.lexicalTokens)
val formUrns = lexcorp.analysisUrns(manager).map(_.form)
val labelled = formUrns.flatMap(u => if (ValidForm.labels.keySet.contains(u.toString)) {
  Some(ValidForm.labels(u.toString)) } else { None }
)



val distinctForms = lexcorp.tokens.flatMap(t => t.analysisUrns(manager)).map(_.form).distinct.filterNot(u => u.objectComponent  == "null")


val validForms = distinctForms.map(urn => ValidForm(urn))
/*val f = "jvm/src/test/resources/c108a.cex"
val lc = LatinCorpus.fromFile(f)
val citableUnits = lc.clusterByCitation
val c108a = citableUnits.head
*/




/*
def tenseValue(prop: MorphologicalProperty) : Tense = {
  prop match {
    case t: Tense =>  t
    case _ =>  throw new Exception(s"${prop}  is not a Tense")
  }
}
def moodValue(prop: MorphologicalProperty) : Mood = {
  prop match {
    case m: Mood => m
    case _ => throw new Exception(s"${prop} is not a Mood")
  }
}

def  tm (lc: LatinCorpus) : Vector[TenseMood]= {
  val options = lc.verbs.map(v => v.analyses.map(a => Vector(a.verbTense,a.verbMood) ) ).flatten
  val noNulls = options.filterNot(v => v(0) == None || v(1) == None )
  noNulls.map(v => TenseMood(
      tenseValue(v(0).get),
      moodValue(v(1).get)
    )
  )
}
*/
// ELIMINATE FORMS OF sum !
val sum = "ls.n46529"
val notToBe = hyginus.verbs.filterNot(_.matchesLexeme(sum))
val hygtm = notToBe.flatMap(_.tenseMood)
val grouped = hygtm.groupBy(tm => tm)
val counted =  grouped.map{ case (tm, v) => (tm, v.size) }
val sorted = counted.toVector.sortBy{ case (tm, count) => count}.reverse

println(sorted.mkString("\n"))


def profileNouns = {}

def profile = {
  println("Citable passages: " + lexcorp.clusterByCitation.size)
  println("Lexical tokens: " + lexcorp.size)
  println("Unanlayzed: " + lexcorp.noAnalysis.size)
  println("Tokens analyzed: " + lexcorp.analyzed.size)
  println("Distinct lexemes: " +   lexcorp.tokens.flatMap(t => t.analyses.map(a => LewisShort.label(a.lemmaId))).distinct.size)
}
//urn:cite2:hmt:ls.markdown:n46529
// urn:cite2:hmt:ls.markdown:n46529
