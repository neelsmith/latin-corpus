
import edu.holycross.shot.ohco2._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._
import edu.holycross.shot.latincorpus._
import scala.io.Source
import java.io.PrintWriter


// citable corpus
val textUrl = "https://raw.githubusercontent.com/LinguaLatina/texts/master/texts/latin23/hyginus.cex"
val corpus = CorpusSource.fromUrl(textUrl, cexHeader = true)


val fstUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus-fst.txt"
val fstLines = Source.fromURL(fstUrl).getLines.toVector


println("Building LatinCorpus...")
val latinCorpus = LatinCorpus.fromFstLines(corpus,Latin23Alphabet, fstLines, strict=false)
println("Done.")

import edu.holycross.shot.tabulae._
val urnManagerUrl = "https://raw.githubusercontent.com/LinguaLatina/morphology/master/urnmanager/config.cex"
val manager = UrnManager.fromUrl(urnManagerUrl)

val cex = latinCorpus.cex(manager)

val cexFile = "hyginus-latc.cex"
new PrintWriter(cexFile){write(cex);close;}
println("CEX representation of LatinCorpus written to " + cexFile)
