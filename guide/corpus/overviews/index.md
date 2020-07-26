---
title: "Overviews"
layout: page
parent: Using a LatinCorpus
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


## Ambiguity

```tut
latinCorpus.tokenAmbiguity
latinCorpus.lexicalAmbiguity
```

## Histograms

Histograms of lexical tokens, of lexeme identifiers, and of labelled lexemes:

```tut
latinCorpus.lexTokenHistogram
latinCorpus.lexemeHistogram
latinCorpus.labelledLexemeHistogram
```

## Concordances

Find occurrences of a token; the result is a list of URNs.



```tut
latinCorpus.tokenConcordance("est")
```

How many?

```tut
latinCorpus.tokenConcordance("est").size
```

What are possible lexemes for this token?

```tut
latinCorpus.tokenLexemeIndex("est")
```

Find occurrences of a lexeme; the result is a list of URNs.

```tut
latinCorpus.passagesForLexeme("ls.n46529")
```

How many?

```tut
latinCorpus.passagesForLexeme("ls.n46529").size
```
