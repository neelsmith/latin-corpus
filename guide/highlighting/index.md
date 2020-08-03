---
title: "Highlighting morphological features"
layout: page
parent: Using a LatinCorpus
---

**Version @VERSION@**

# Highlighting morphological features

Load selections from Livy (as on [this page](../citableNodes/)).


```scala mdoc:invisible
import edu.holycross.shot.ohco2._
// citable corpus of Minkova-Tunberg selections of Livy
val textFile = "jvm/src/test/resources/cex/livy-mt.cex"
val corpus = CorpusSource.fromFile(textFile, cexHeader = true)
// SFST data
import scala.io.Source
val fstFile = "jvm/src/test/resources/fst/livy-mt-parsed.txt"
val fstLines = Source.fromFile(fstFile).getLines.toVector
import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.latin._
import edu.holycross.shot.latincorpus._
```

```scala mdoc:silent
val latinCorpus = LatinCorpus.fromFstLines(corpus, Latin24Alphabet, fstLines, strict=false)
```

Cluster, and look at first cluster:

```scala mdoc:silent
val clustered = latinCorpus.clusterByCitation
val firstCluster = clustered.head
```

Survey the first cluster: how many tokens?

```scala mdoc
println(firstCluster.tokens.size)
```


Individual tokens carry a lot of information, including a citable node of text, and a list of possible morphological analyses:

```scala mdoc
firstCluster.tokens.head
// a citable node:
firstCluster.tokens.head.cn
// possible analyses:
firstCluster.tokens.head.analyses
```


The `LatinParsedTokenSequence` can use all of this information in a variety of ways.  Let's do some old-fashioned Scala to read text contents of this sequence, by mapping each token to the text contents of its citable node, and combining them in a String.


```scala mdoc
firstCluster.tokens.map(_.cn.text).mkString(" ")
```


## Highlight a single feature

The `LatinParsedTokenSequence` can use a `MorphologyFilter` object to highlight tokens in formatted text.  Here's a filter for all first person singular forms.

```scala mdoc
import edu.holycross.shot.tabulae._
val mf = MorphologyFilter(person = Some(First), grammaticalNumber = Some(Singular))
```

Here's the first cluster with first singulars highlighted:

```scala mdoc
firstCluster.highlightForms(mf)
```

In a markdown-aware environment, that will look like this:

>sed debebatur, ut **opinor**, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisse





By default, highlighting uses markdown, but you can specify bracketing opening and closing text.  Let's use some HTML.

```scala mdoc
val open = "<span style=\"color:green\">"
val closer = "</span>"
firstCluster.highlightForms(mf, open, closer)
```

When rendered in a markdown or HTML environment, that looks like this:


> sed debebatur  ut <span style="color:green">opinor</span>, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisset,


(As a bonus, note how the `LatinParsedTokenSequence` thoughtfully observes the token type, and adjusts white spacing appropriately for punctuation tokens.)




## Highlighting multiple features

You can attach a `MorphologyFilter` to opening and closing highlighting strings to create a `Highlighter`.  Here, too, the default highlighting strings are markdown strong emphasis (two asterisks).  We'll let verb forms use the default, and define a distinct highlighter for nouns.



```scala mdoc:silent
val allVerbs = MorphologyFilter(pos = Some("verb"))
val verbsHL = Highlighter(allVerbs)

val allNouns = MorphologyFilter(pos = Some("noun"))
val nounsHL = Highlighter(allNouns, opening = "*", closing = "*")

val hiliters = Vector(verbsHL, nounsHL)
```

Now we can just pass the `hiliters` list in, to get this result.

```scala mdoc
firstCluster.highlightForms(hiliters)
```

What does that look like in a markdown environment?

>sed **debebatur**, ut **opinor**, *fatis* tantae *origo* *urbis* maximique secundum *deorum* *opes* *imperii* *principium*. compressa cum geminum partum edidisset,

And of course we can use any kind of highlighting we like.  Let's use some HTML to make nouns blue and verbs green.

```scala mdoc:silent
val blue = "<span style=\"color:blue\">"
val green = "<span style=\"color:green\">"

val blueNouns = Highlighter(allNouns, opening = blue, closing = closer)
val greenVerbs = Highlighter(allVerbs,opening = green, closing = closer)

val colorHiliters = Vector(greenVerbs, blueNouns)
```

Here's the result.

```scala mdoc
firstCluster.highlightForms(colorHiliters)
```

And here's what it looks like in an HTML setting.

>sed <span style="color:green">debebatur</span>, ut <span style="color:green">opinor</span>, <span style="color:blue">fatis</span> tantae <span style="color:blue">origo</span> <span style="color:blue">urbis</span> maximique secundum <span style="color:blue">deorum</span> <span style="color:blue">opes</span> <span style="color:blue">imperii</span> <span style="color:blue">principium</span>. compressa cum geminum partum edidisset,
