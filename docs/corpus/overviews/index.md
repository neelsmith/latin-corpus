---
title: "Overviews"
layout: page
parent: Using a LatinCorpus
---


# Surveying a corpus



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

## Concordances

Find occurrences of a token; the result is a list of URNs.



```NOTSCALA
latinCorpus.tokenConcordance("est")
```

How many?

```tut
NOTSCALA.tokenConcordance("est").size
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
