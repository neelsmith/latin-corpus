import edu.holycross.shot.latincorpus._
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.latin._
val f = "jvm/src/test/resources/c108a.cex"
val lc = LatinCorpus.fromFile(f)
val citableUnits = lc.clusterByCitation
val c108a = citableUnits.head


//def tmForPair

val opties = c108a.verbs.map(_.analyses.map(a => List(a.verbTense,a.verbMood) ) )

 val realValues = opties.filter(_.size == 2).filter( l => ((l(0) != None) && (l(1)  != None) ))
