---
title: "Parsed tokens"
layout: page
parent: Using a LatinCorpus
nav_order: 2
---


**Version @VERSION@**

# Working with parsed tokens

Get the citable corpus and SFST analyses to build a `LatinCorpus` of Minkova and Tunberg's selections from Livy:

```scala mdoc:silent
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
```scala mdoc:silent
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._
import edu.holycross.shot.latincorpus._
val latinCorpus = LatinCorpus.fromFstLines(corpus, Latin24Alphabet, fstLines, strict=false)
```

```scala mdoc:silent
val clusters = latinCorpus.clusterByCitation
val morphFilter = MorphologyCollectionsFilter(clusters)
```

```scala mdoc:silent
import edu.holycross.shot.tabulae._
val caseList = Vector(Nominative, Genitive)
val nomgenOnly = morphFilter.limitSubstantiveCase(caseList)
val nomgenNodes = morphFilter.limitSubstantiveCaseNodes(caseList)
```



Pretty-printing examples:

```scala mdoc
nomgenNodes.take(5).map(node => node.urn + "  " + node.text).mkString("\n\n")
```

or if you just want the text:

```scala mdoc
val nomgenText = morphFilter.limitSubstantiveCaseText(caseList)
```

Pretty-print the first 5 of these:

```scala mdoc
nomgenText.take(5).mkString("\n\n")
```
