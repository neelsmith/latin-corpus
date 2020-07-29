---
title: "Overviews"
layout: page
parent: Using a LatinCorpus
---


# Surveying a corpus




```scala
// The latincorpus library:
import edu.holycross.shot.latincorpus._
// Other libraries you'll frequently use:
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._

// Create an ohco2 Corpus:
val url = "https://raw.githubusercontent.com/LinguaLatina/texts/master/texts/latin23/hyginus.cex"
val corpus = CorpusSource.fromUrl(url, cexHeader = true)
// Load corresponding parsing data
import scala.io.Source
val fstUrl = "https://lingualatina.github.io/analysis/data/c108.fst"
val fstLines = Source.fromURL(fstUrl).getLines.toVector

import edu.holycross.shot.latin._
```


See this page for an example of how to [build a `LatinCorpus`](https://neelsmith.github.io/latin-corpus/libraries/)


```scala
val latinCorpus = LatinCorpus.fromFstLines(corpus,Latin23Alphabet, fstLines, strict=false)
```


## Character set

- identify invalid characters, and summarize character set usage

## Classified tokens

- identify invalid tokens, and summarize token usage

## Concordances

Find occurrences of a token; the result is a list of URNs.

How many times does the token *edixit* appear in Hyginus?

```scala
latinCorpus.tokenConcordance("edixit")
// res0: Vector[CtsUrn] = Vector(
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:67pr.5"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:72a.1"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.2")
// )
```


What are possible lexemes for this token?

```scala
latinCorpus.tokenLexemeIndex("edixit")
// res1: Vector[String] = Vector("ls.n15140")
```

Find occurrences of a lexeme; the result is a list of URNs.

```scala
latinCorpus.passagesForLexeme("ls.n15140")
// res2: Vector[CtsUrn] = Vector()
```




## Ambiguity

```scala
latinCorpus.tokenAmbiguity
// res3: Double = 1.201610305958132
latinCorpus.lexicalAmbiguity
// res4: Double = 1.0412474849094568
```

## Histograms

Histograms of lexical tokens, of lexeme identifiers, and of labelled lexemes:

```NOTSCALA
latinCorpus.lexTokenHistogram
latinCorpus.lexemeHistogram
latinCorpus.labelledLexemeHistogram
```
