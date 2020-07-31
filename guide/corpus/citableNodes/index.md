---
title: "Citable nodes"
layout: page
parent: Using a LatinCorpus
nav_order: 2
---


```tut:invisible
import edu.holycross.shot.tabulae._
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._

import edu.holycross.shot.histoutils._

import edu.holycross.shot.latin._
import edu.holycross.shot.latincorpus._


import edu.holycross.shot.mid.validator._


val corpus = CorpusSource.fromFile(s"src/test/resources/cex/livy-mt.cex", cexHeader = true)
val parserOutput = "src/test/resources/fst/livy-mt-parsed.txt"

import scala.io.Source
val fst = Source.fromFile(parserOutput).getLines.toVector

val latinCorpus = LatinCorpus.fromFstLines(
    corpus,
    Latin23Alphabet,
    fst,
    strict = false
  )
```

Group tokens into citable units:

```tut:silent
val clusters = latinCorpus.clusterByCitation
```

Create a filter to select citable sequences:

```tut:silent
val morphFilter = MorphologyCollectionsFilter(clusters)
```

Now find citable sequences in which all substantives appear in a case given in a limiting list:

```tut:silent
val caseList = Vector(Nominative, Genitive)
val nomgenOnly = morphFilter.limitSubstantiveCase(caseList)
```

The result is a Vector.  How many?

```tut
nomgenOnly.size
```

If you just want the matching canonically citable node:

```tut:silent
val nomgenNodes = morphFilter.limitSubstantiveCaseNodes(caseList)
```

Pretty-print the first 5 of these:

```tut
nomgenNodes.take(5).map(node => node.urn + "  " + node.text).mkString("\n\n")
```

or if you just want the text:

```tut:silent
val nomgenText = morphFilter.limitSubstantiveCaseText(caseList)
```

Pretty-print the first 5 of these:

```tut
nomgenText.take(5).mkString("\n\n")
```
