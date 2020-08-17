---
title: Building from FST output
layout: page
parent: Building a LatinCorpus
grand_parent: Using a LatinCorpus
nav_order: 1
---

**Version 5.3.0**

# Building a `LatinCorpus` from SFST output

To use the `fromFstLines` method of the `LatinCorpus` object, we need to supply a citable corpus with a specified orthography, and the SFST output of parsing the corpus' word list, as a Vector of Strings.

For this example, we'll load both the citable corpus and SFST output from URLs.


## Citable corpus

The `ohco2` library's `CorpusSource` object can read a citable corpus from a URL.

```scala
import edu.holycross.shot.ohco2._
// citable corpus
val url = "https://raw.githubusercontent.com/LinguaLatina/texts/master/texts/latin23/hyginus.cex"
val corpus = CorpusSource.fromUrl(url, cexHeader = true)
```


## SFST output as a Vector of Strings

We can use Scala's `Source` object to read lines of text from a URL.

```scala
// morphological info in SFST format
import scala.io.Source
val fstUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-fst.txt"
val fstLines = Source.fromURL(fstUrl).getLines.toVector
```


## The parsed corpus

The `MidOrthography` trait is defined in the `orthography` library.  For this text of Hyginus, we will use a concrete implementation from the `latin` library, so will import both `orthography` and `latin`.  

At that point, we can create a corpus with the `fromFstLines` method.

```scala
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._
import edu.holycross.shot.latincorpus._
val latinCorpus = LatinCorpus.fromFstLines(corpus, Latin23Alphabet, fstLines, strict=false)
```

The resulting `LatinCorpus` is citable by individual token, and associates a (possibly empty) list of analyses with each token.

Number of tokens in the corpus:

```scala
println(latinCorpus.size)
// 32459
```
