---
title: "Highlighting morphological features"
layout: page
parent: Using a LatinCorpus
---

**Version 5.0.0**

# Highlighting morphological features

Load selections from Livy (as on [this page](../citableNodes/)).



```scala
val latinCorpus = LatinCorpus.fromFstLines(corpus, Latin24Alphabet, fstLines, strict=false)
```

Cluster, and look at first cluster:

```scala
val clustered = latinCorpus.clusterByCitation
val firstCluster = clustered.head
```

Survey the first cluster: how many tokens?

```scala
println(firstCluster.tokens.size)
// 25
```


Individual tokens carry a lot of information, including a citable node of text, and a list of possible morphological analyses:

```scala
firstCluster.tokens.head
// res1: LatinParsedToken = LatinParsedToken(
//   CitableNode(CtsUrn("urn:cts:omar:stoa0179.stoa001.omar_tkns:1.4.1.0"), "sed"),
//   LexicalToken,
//   Vector(
//     IndeclinableForm(
//       "ls.n43291",
//       "latcommon.indecln43291",
//       "indeclinfl.2",
//       Conjunction
//     )
//   )
// )
// a citable node:
firstCluster.tokens.head.cn
// res2: CitableNode = CitableNode(
//   CtsUrn("urn:cts:omar:stoa0179.stoa001.omar_tkns:1.4.1.0"),
//   "sed"
// )
// possible analyses:
firstCluster.tokens.head.analyses
// res3: Vector[edu.holycross.shot.tabulae.LemmatizedForm] = Vector(
//   IndeclinableForm(
//     "ls.n43291",
//     "latcommon.indecln43291",
//     "indeclinfl.2",
//     Conjunction
//   )
// )
```


The `LatinParsedTokenSequence` can use all of this information in a variety of ways.  Let's do some old-fashioned Scala to read text contents of this sequence, by mapping each token to the text contents of its citable node, and combining them in a String.


```scala
firstCluster.tokens.map(_.cn.text).mkString(" ")
// res4: String = "sed debebatur , ut opinor , fatis tantae origo urbis maximique secundum deorum opes imperii principium . vi compressa Vestalis cum geminum partum edidisset ,"
```


## Highlight a single feature

The `LatinParsedTokenSequence` can use a `MorphologyFilter` object to highlight tokens in formatted text.  Here's a filter for all first person singular forms.

```scala
import edu.holycross.shot.tabulae._
val mf = MorphologyFilter(person = Some(First), grammaticalNumber = Some(Singular))
// mf: MorphologyFilter = MorphologyFilter(
//   None,
//   Some(First),
//   Some(Singular),
//   None,
//   None,
//   None,
//   None,
//   None,
//   None,
//   None
// )
```

Here's the first cluster with first singulars highlighted:

```scala
firstCluster.highlightForms(mf)
// res5: String = "sed debebatur, ut **opinor**, fatis tantae origo urbis maximique secundum deorum opes imperii principium. vi compressa Vestalis cum geminum partum edidisset,"
```

In a markdown-aware environment, that will look like this:

>sed debebatur, ut **opinor**, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisse





By default, highlighting uses markdown, but you can specify bracketing opening and closing text.  Let's use some HTML.

```scala
val open = "<span style=\"color:green\">"
// open: String = "<span style=\"color:green\">"
val closer = "</span>"
// closer: String = "</span>"
firstCluster.highlightForms(mf, open, closer)
// res6: String = "sed debebatur, ut <span style=\"color:green\">opinor</span>, fatis tantae origo urbis maximique secundum deorum opes imperii principium. vi compressa Vestalis cum geminum partum edidisset,"
```

When rendered in a markdown or HTML environment, that looks like this:


> sed debebatur  ut <span style="color:green">opinor</span>, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisset,


(As a bonus, note how the `LatinParsedTokenSequence` thoughtfully observes the token type, and adjusts white spacing appropriately for punctuation tokens.)




## Highlighting multiple features

You can attach a `MorphologyFilter` to opening and closing highlighting strings to create a `Highlighter`.  Here, too, the default highlighting strings are markdown strong emphasis (two asterisks).  We'll let verb forms use the default, and define a distinct highlighter for nouns.



```scala
val allVerbs = MorphologyFilter(pos = Some("verb"))
val verbsHL = Highlighter(allVerbs)

val allNouns = MorphologyFilter(pos = Some("noun"))
val nounsHL = Highlighter(allNouns, opening = "*", closing = "*")

val hiliters = Vector(verbsHL, nounsHL)
```

Now we can just pass the `hiliters` list in, to get this result.

```scala
firstCluster.highlightForms(hiliters)
// res7: String = "sed **debebatur**, ut **opinor**, *fatis* tantae *origo* *urbis* maximique secundum *deorum* *opes* *imperii* *principium*. *vi* compressa Vestalis cum geminum partum edidisset,"
```

What does that look like in a markdown environment?

>sed **debebatur**, ut **opinor**, *fatis* tantae *origo* *urbis* maximique secundum *deorum* *opes* *imperii* *principium*. compressa cum geminum partum edidisset,

And of course we can use any kind of highlighting we like.  Let's use some HTML to make nouns blue and verbs green.

```scala
val blue = "<span style=\"color:blue\">"
val green = "<span style=\"color:green\">"

val blueNouns = Highlighter(allNouns, opening = blue, closing = closer)
val greenVerbs = Highlighter(allVerbs,opening = green, closing = closer)

val colorHiliters = Vector(greenVerbs, blueNouns)
```

Here's the result.

```scala
firstCluster.highlightForms(colorHiliters)
// res8: String = "sed <span style=\"color:green\">debebatur</span>, ut <span style=\"color:green\">opinor</span>, <span style=\"color:blue\">fatis</span> tantae <span style=\"color:blue\">origo</span> <span style=\"color:blue\">urbis</span> maximique secundum <span style=\"color:blue\">deorum</span> <span style=\"color:blue\">opes</span> <span style=\"color:blue\">imperii</span> <span style=\"color:blue\">principium</span>. <span style=\"color:blue\">vi</span> compressa Vestalis cum geminum partum edidisset,"
```

And here's what it looks like in an HTML setting.

>sed <span style="color:green">debebatur</span>, ut <span style="color:green">opinor</span>, <span style="color:blue">fatis</span> tantae <span style="color:blue">origo</span> <span style="color:blue">urbis</span> maximique secundum <span style="color:blue">deorum</span> <span style="color:blue">opes</span> <span style="color:blue">imperii</span> <span style="color:blue">principium</span>. compressa cum geminum partum edidisset,
