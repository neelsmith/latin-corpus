---
title: "Overview of data structures"
layout: page
nav_order: 2
---

**Version @VERSION@**


# Overview of data structures

## The `LatinCorpus`

### Structures the `LatinCorpus` builds on


The `LatinCorpus` associates morphological analyses with classified tokens from a citable text corpus.

All content in a `LatinCorpus` is identified by `CtsUrn`s and `Cite2Urn`s (from the `cite` library).  

The morphological analyses are a list of `LemmatizedForm`s (from the `tabulae` library).  Classified tokens are created from a citable corpus (a `Corpus` in the `ohco2` library) and an orthographic system (an implementation of the MID `orthography` trait).


In order to work with the components of a `LatinCorpus`, you will often import all of these libraries.

```scala mdoc
// The latincorpus library:
import edu.holycross.shot.latincorpus._
// Other libraries you'll frequently use:
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.orthography._
```


### Building a `LatinCorpus`

One way to create a `LatinCorpus` is with the `fromFstLines` method of the `LatinCorpus`.  You provide a `Corpus`, an `MidOrthography`, and the output from an FST parser, as a Vector Strings.


For this example, we'll create both `Corpus` and a list of FST output by loading data available from URLs.

```scala mdoc:silent
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
```

The specific MID Orthography we want to use is the `Latin23Alphabet`, available from the `latin` library. With all of these in hand, we can create a `LatinCorpus`.

```scala mdoc:silent
import edu.holycross.shot.latin._
val latinCorpus = LatinCorpus.fromFstLines(chapter,Latin23Alphabet, fstLines, strict=false)
```

## The `LatinParsedToken`



The fundamental component of a `LatinCorpus` is a series of `LatinParsedToken`s: classified tokens in a citable passage of text, with associated morphological analyses.

How many tokens are in this chapter?

```scala mdoc
latinCorpus.tokens.size
```


### Example of using a `LatinParsedToken`

Let's look at a single token.

```scala mdoc

val tkn = latinCorpus.tokens(9)
tkn.urn
tkn.text
tkn.category
tkn.analyses.size
```

Let's take a single token, and look at a couple of the numerous methods provided by the `LemmatizedToken` class.


```scala mdoc
val tknAnalysis = tkn.analyses.head

tknAnalysis.lemmaId
tknAnalysis.formUrn
tknAnalysis.formLabel
```


## Summary of relations of main code libraries

The following stack of libaries provides successively higher orders of language-specific text processing:

1. `xcite`: concerned only with citation; not aware of what citation refers to
2. `ohco2`: concerned with a citable corpus.  The corpus is a sequence of nodes; each node associates a citation (a CTS URN) and a text string.  Not concerned with contents of the text string.
3. `orthography` trait: concerned only with contents of strings, not citation. Implementatons define a valid set of code points, and how they can be tokenized.  Using an `orthography` with an `ohco2` library, you can ensure that your citable content can be semantically tokenized.
4. `tabulae`:  concerned only with string values of lexical tokens. Supports both building binary parsers with the SFST toolkit, and working with the output of a parser.
5.  `latincorpus`:  concerned with a citable corpus in a defined orthography, with fully parsed lexical tokens.  Enables working with morphological analyses and textual contents within the structure of a citable corpus.
