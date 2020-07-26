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



```scala mdoc:silent
val latinCorpus = LatinCorpus.fromFstLines(chapter,Latin23Alphabet, fstLines, strict=false)
```


## Character set

## Classified tokens



## Concordances

Find occurrences of a token; the result is a list of URNs.



```NOTSCALA
latinCorpus.tokenConcordance("est")
```

How many?

```NOTSCALA
latinCorpus.tokenConcordance("est").size
```

What are possible lexemes for this token?

```NOTSCALA
latinCorpus.tokenLexemeIndex("est")
```

Find occurrences of a lexeme; the result is a list of URNs.

```NOTSCALA
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
