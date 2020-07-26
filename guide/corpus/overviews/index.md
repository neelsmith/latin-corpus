---
title: "Overviews"
layout: page
parent: Using a LatinCorpus
---


# Surveying a corpus




```scala mdoc:invisible
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
// We'll select a single chapter to use, e.g., for a class
// assignment:
val chapter = corpus ~~ CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc:108a")
// Load corresponding parsing data
import scala.io.Source
val fstUrl = "https://lingualatina.github.io/analysis/data/c108.fst"
val fstLines = Source.fromURL(fstUrl).getLines.toVector

import edu.holycross.shot.latin._
```


See this page for an example of how to [build a `LatinCorpus`](https://neelsmith.github.io/latin-corpus/libraries/)


```scala mdoc:silent
val latinCorpus = LatinCorpus.fromFstLines(corpus,Latin23Alphabet, fstLines, strict=false)
```


## Character set

- identify invalid characters, and summarize character set usage

## Classified tokens

- identify invalid tokens, and summarize token usage

## Concordances

Find occurrences of a token; the result is a list of URNs.

How many times does the token *edixit* appear in Hyginus?

```scala mdoc
latinCorpus.tokenConcordance("edixit")
```


What are possible lexemes for this token?

```scala mdoc
latinCorpus.tokenLexemeIndex("edixit")
```

Find occurrences of a lexeme; the result is a list of URNs.

```scala mdoc
latinCorpus.passagesForLexeme("ls.n46529")
```

How many?

```NOTSCALA
latinCorpus.passagesForLexeme("ls.n46529").size
```



## Ambiguity

```tut
latinCorpus.tokenAmbiguity
latinCorpus.lexicalAmbiguity
```

## Histograms

Histograms of lexical tokens, of lexeme identifiers, and of labelled lexemes:

```NOTSCALA
latinCorpus.lexTokenHistogram
latinCorpus.lexemeHistogram
latinCorpus.labelledLexemeHistogram
```
