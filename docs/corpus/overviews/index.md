---
title: "Overviews"
layout: page
parent: Using a LatinCorpus
---


# Surveying a corpus






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



```scala
latinCorpus.tokenConcordance("est")
// res0: Vector[CtsUrn] = Vector(
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.1"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.1"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.3"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.6"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:3pr.2"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:3pr.3"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:3pr.4"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:4pr.5"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:7pr.1"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:7pr.1"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:7pr.4"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:7pr.5"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:7pr.5"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:8pr.4"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:9pr.2"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:9pr.4"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:12pr.1"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:12pr.4"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.1"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.1"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.1"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.3"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.4"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.8"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.10"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.13"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.16"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.25"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.26"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.27"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.33"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:14pr.33"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:16pr.2"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:19pr.2"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:22pr.4"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:22pr.4"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:22pr.4"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:23pr.2"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:23pr.3"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:23pr.4"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:23pr.4"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:23pr.5"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:24pr.3"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:24pr.4"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:24pr.5"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:24pr.5"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:26pr.1"),
//   CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:27pr.1"),
// ...
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
