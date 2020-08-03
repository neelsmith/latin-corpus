---
title: Saving a LatinCorpus
layout: page
parent: Using a LatinCorpus
nav_order: 1
---

# Saving a `LatinCorpus`

Maybe you have a particular corpus you're interested in working with repeatedly (e.g., a selection of material you're using in a course).  It can be convenient to save that corpus as a CEX file that you can easily and quickly reload.

Assume you've built a corpus (see building page)

```scala mdoc:invisible
import edu.holycross.shot.ohco2._
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._
import edu.holycross.shot.latincorpus._
import edu.holycross.shot.tabulae._
import scala.io.Source
```

```scala mdoc:invisible
// citable corpus
val corpusUrl = "https://raw.githubusercontent.com/LinguaLatina/texts/master/texts/latin23/hyginus.cex"
val corpus = CorpusSource.fromUrl(corpusUrl, cexHeader = true)
```

```scala mdoc:invisible
// morphological info in SFST format
val fstUrl = "https://lingualatina.github.io/analysis/data/c108.fst"
val fstLines = Source.fromURL(fstUrl).getLines.toVector
```

```scala mdoc:silent
// voila
val latinCorpus = LatinCorpus.fromFstLines(corpus,Latin23Alphabet, fstLines, strict=false)
```

To serialize to a CITE Collection, you need to be able to expand tabulae's abbreviated identifiers to full URNs.  You do that wit a `UrnManager` from the `tabulae` library.  We'll use one we've already defined.


```scala mdoc:silent
import edu.holycross.shot.tabulae._
val urnManagerUrl = "https://raw.githubusercontent.com/neelsmith/tabulae/master/jvm/src/test/resources/datasets/analytical_types/urnregistry/collectionregistry.cex"
val manager = UrnManager.fromUrl(urnManagerUrl)
```

Write CEX:

```scala mdoc:silent
val cex = latinCorpus.cex(manager)
import java.io.PrintWriter
new PrintWriter("hyginus-lc.cex"){write(cex);close;}
```
