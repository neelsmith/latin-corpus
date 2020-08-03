---
title: Using FST output
layout: page
parent: Building a LatinCorpus
grand_parent: Using a LatinCorpus
nav_order: 1
---

**Version @VERSION@**

# Building a `LatinCorpus` from SFST output

```scala mdoc:silent
import edu.holycross.shot.ohco2._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._
import edu.holycross.shot.latincorpus._
import scala.io.Source
```

```scala mdoc:silent
// citable corpus
val url = "https://raw.githubusercontent.com/LinguaLatina/texts/master/texts/latin23/hyginus.cex"
val corpus = CorpusSource.fromUrl(url, cexHeader = true)
```

```scala mdoc:silent
// morphological info in SFST format
val fstUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-fst.txt"
val fstLines = Source.fromURL(fstUrl).getLines.toVector
```

```scala mdoc:silent
// voila
val latinCorpus = LatinCorpus.fromFstLines(corpus,Latin23Alphabet, fstLines, strict=false)
```
