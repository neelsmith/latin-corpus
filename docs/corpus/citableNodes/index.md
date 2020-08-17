---
title: "Parsed tokens"
layout: page
parent: Using a LatinCorpus
nav_order: 2
---


**Version 5.3.0**

# Working with parsed tokens

Get the citable corpus and SFST analyses to build a `LatinCorpus` of Minkova and Tunberg's selections from Livy:

```scala
import edu.holycross.shot.ohco2._
// citable corpus of Minkova-Tunberg selections of Livy
val textFile = "jvm/src/test/resources/cex/livy-mt.cex"
val corpus = CorpusSource.fromFile(textFile, cexHeader = true)
// SFST data
import scala.io.Source
val fstFile = "jvm/src/test/resources/fst/livy-mt-parsed.txt"
val fstLines = Source.fromFile(fstFile).getLines.toVector
```

And create the parsed corpus:
```scala
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._
import edu.holycross.shot.latincorpus._
val latinCorpus = LatinCorpus.fromFstLines(corpus, Latin24Alphabet, fstLines, strict=false)
```

```scala
val clusters = latinCorpus.clusterByCitation
val morphFilter = MorphologyCollectionsFilter(clusters)
```

```scala
import edu.holycross.shot.tabulae._
val caseList = Vector(Nominative, Genitive)
val nomgenOnly = morphFilter.limitSubstantiveCase(caseList)
val nomgenNodes = morphFilter.limitSubstantiveCaseNodes(caseList)
```



Pretty-printing examples:

```scala
nomgenNodes.take(5).map(node => node.urn + "  " + node.text).mkString("\n\n")
// res0: String = """urn:cts:omar:stoa0179.stoa001.omar_tkns:1.4.8  ita geniti itaque educati, cum primum adolevit aetas, nec in stabulis nec ad pecora segnes, venando peragrare saltus.
// 
// urn:cts:omar:stoa0179.stoa001.omar_tkns:1.26.4  abi hinc cum inmaturo amore ad sponsum inquit, oblita fratrum mortuorum vivi que, oblita patriae.
// 
// urn:cts:omar:stoa0179.stoa001.omar_tkns:1.55.8  praeterquam quod antiquior est, crediderim quadraginta ea sola talenta fuisse,
// 
// urn:cts:omar:stoa0179.stoa001.omar_tkns:1.58.12  conclamat vir pater que.
// 
// urn:cts:omar:stoa0179.stoa001.omar_tkns:2.12.12  cum rex simul ira infensus periculoque conterritus circumdari ignes minitabundus iuberet,"""
```

or if you just want the text:

```scala
val nomgenText = morphFilter.limitSubstantiveCaseText(caseList)
// nomgenText: Vector[String] = Vector(
//   "ita geniti itaque educati, cum primum adolevit aetas, nec in stabulis nec ad pecora segnes, venando peragrare saltus.",
//   "abi hinc cum inmaturo amore ad sponsum inquit, oblita fratrum mortuorum vivi que, oblita patriae.",
//   "praeterquam quod antiquior est, crediderim quadraginta ea sola talenta fuisse,",
//   "conclamat vir pater que.",
//   "cum rex simul ira infensus periculoque conterritus circumdari ignes minitabundus iuberet,",
//   "inde Lavinium recepit; tum deinceps Corbionem, Vitelliam, Trebium, Lavicos, Pedum cepit.",
//   "tum matronae ad Veturiam, matrem Coriolani, Volumniamque uxorem frequentes coeunt. id publicum consilium an muliebris timor fuerit, parum convenit;",
//   "Decemviri creati Ap. Claudius, T. Genucius, P. Sestius, L. Veturius, C. Iulius, A. Manlius, P. Sulpicius, P. Curiatius, T. Romilius, Sp. Postumius.",
//   "centum viginti lictores forum inpleverant et cum fascibus secures inligatas praeferebant; nec attinuisse demi securem, cum sine provocatione creati essent, interpretabantur.",
//   "ut taedio praesentium consules duo tandem et status pristinus rerum in desiderium veniant.",
//   "quocumque clamor hostium, mulierum puerorumque ploratus, sonitus flammae et fragor ruentium tectorum avertisset, paventes ad omnia animos oraque et oculos flectebant velut ad spectaculum a fortuna positi occidentis patriae nec ullius rerum suarum relicti praeterquam corporum vindices,",
//   "tribuni militum creati T. Quinctius, Ser. Cornelius, Ser. Sulpicius, Sp. Servilius, L. Papirius, L. Veturius."
// )
```

Pretty-print the first 5 of these:

```scala
nomgenText.take(5).mkString("\n\n")
// res1: String = """ita geniti itaque educati, cum primum adolevit aetas, nec in stabulis nec ad pecora segnes, venando peragrare saltus.
// 
// abi hinc cum inmaturo amore ad sponsum inquit, oblita fratrum mortuorum vivi que, oblita patriae.
// 
// praeterquam quod antiquior est, crediderim quadraginta ea sola talenta fuisse,
// 
// conclamat vir pater que.
// 
// cum rex simul ira infensus periculoque conterritus circumdari ignes minitabundus iuberet,"""
```
