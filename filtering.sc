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




def formFreqs = {
  for (vf <- validForms) yield {
    val count = lexcorp.tokens.filter(t => t.hasForm(vf.urn, manager)).size
    println(vf.label + ": " + count)
    (vf, count)
  }
}


def formsHisto(tokens: Vector[LatinParsedToken], umgr: UrnManager) = {
  val forms = tokens.flatMap(t => t.analysisUrns(manager)).map(a => a.form)
  val grouped = forms.groupBy(f => f).toVector
  grouped.map( pr => (pr._1, pr._2.size) ).sortBy(_._2).reverse
}

// ELIMINATE FORMS OF sum:
val sum = "ls.n46529"
val notToBe = hyginus.verbs.filterNot(_.matchesLexeme(sum))
val hygtm = notToBe.flatMap(_.tenseMood)
val grouped = hygtm.groupBy(tm => tm)
val counted =  grouped.map{ case (tm, v) => (tm, v.size) }
val sorted = counted.toVector.sortBy{ case (tm, count) => count}.reverse

println(sorted.mkString("\n"))


def profileNouns = {

}

def profile = {
  println("Citable passages: " + lexcorp.clusterByCitation.size)
  println("Lexical tokens: " + lexcorp.size)
  println("Unanlayzed: " + lexcorp.noAnalysis.size)
  println("Tokens analyzed: " + lexcorp.analyzed.size)
  println("Distinct lexemes: " +   lexcorp.tokens.flatMap(t => t.analyses.map(a => LewisShort.label(a.lemmaId))).distinct.size)
  println("Distinct (possible) forms: " + freqs.size)
}
//urn:cite2:hmt:ls.markdown:n46529
// urn:cite2:hmt:ls.markdown:n46529

val f = "jvm/src/test/resources/sect196a.cex"
val corp = LatinCorpus.fromFile(f)
