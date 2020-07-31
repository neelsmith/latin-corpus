---
title: Building a LatinCorpus
layout: page
parent: Using a LatinCorpus
nav_order: 0
---

# Building a `LatinCorpus`

Two ways:

- tokenizable corpus + sfst output
- from CEX serialization of the parsed token data model


## From SFST output

```scala
import edu.holycross.shot.ohco2._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._
import edu.holycross.shot.latincorpus._
import scala.io.Source
```

```scala
// citable corpus
val url = "https://raw.githubusercontent.com/LinguaLatina/texts/master/texts/latin23/hyginus.cex"
val corpus = CorpusSource.fromUrl(url, cexHeader = true)
```

```scala
// morphological info in SFST format
val fstUrl = "https://lingualatina.github.io/analysis/data/c108.fst"
val fstLines = Source.fromURL(fstUrl).getLines.toVector
```

```scala
// voila
val latinCorpus = LatinCorpus.fromFstLines(corpus,Latin23Alphabet, fstLines, strict=false)
```
