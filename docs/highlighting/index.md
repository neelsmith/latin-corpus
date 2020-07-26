---
title: "Highlighting morphological features"
layout: page
parent: Using a LatinCorpus
---

```tut:invisible
// invisibly import libraries, and load data.
//
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

val clustered = latinCorpus.clusterByCitation
val livy_1_4_1 = clustered(0)
```


## What's in a token

We'll start with a `LatinParsedTokenSequence`, in this case, Livy, 1.4.1.  It contains a vector of tokens.  How many?

```tut
livy_1_4_1.tokens.size
```

Individual tokens carry a lot of information, including a citable node of text, and a list of possible morphological analyses:

```tut
livy_1_4_1.tokens(0)


// a citable node:
livy_1_4_1.tokens(0).cn
// possible analyses:
livy_1_4_1.tokens(0).analyses
```

The `LatinParsedTokenSequence` can use all of this information in a variety of ways.  Let's do some old-fashioned Scala to read text contents of this sequence, by mapping each token to the text contents of its citable node, and combining them in a String.

```tut
livy_1_4_1.tokens.map(_.cn.text).mkString(" ")
```


## Highlight a single feature

The `LatinParsedTokenSequence` can use a `MorphologyFilter` object to highlight tokens in formatted text.  Here's a filter for all first person singular forms.

```tut
 val mf = MorphologyFilter(person = Some(First), grammaticalNumber = Some(Singular))
 ```


Here's Livy 1.4.1 with first singulars highlighted.

```tut
livy_1_4_1.highlightForms(mf)
```

What does that output look like in a markdown environment?

>sed debebatur, ut **opinor**, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisse


Default highlighting uses markdown, but you can specify bracketing opening and closing text.  Let's use some HTML.

```tut
val open = "<span style=\"color:green\">"
val closer = "</span>"
livy_1_4_1.highlightForms(mf, open, closer)

```

What does that look like when it's rendered?


> sed debebatur  ut <span style="color:green">opinor</span>, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisset,

(As a bonus, note how the `LatinParsedTokenSequence` thoughtfully observes the token type, and adjusts white spacing appropriately for punctuation tokens.)

## Highlighting multiple features

You can attach a `MorphologyFilter` to opening and closing highlighting strings to create a `Highlighter`.  Here, too, the default highlighting strings are markdown strong emphasis ("**").  We'll let verb forms use the default, and define a distinct highlighter for nouns.

```tut:silent
val allVerbs = MorphologyFilter(pos = Some("verb"))
val verbsHL = Highlighter(allVerbs)

val allNouns = MorphologyFilter(pos = Some("noun"))
val nounsHL = Highlighter(allNouns, opening = "*", closing = "*")

val hiliters = Vector(verbsHL, nounsHL)
```

Now we can just pass the `hiliters` list in, to get this result.

```tut
livy_1_4_1.highlightForms(hiliters)
```

What does that look like in a markdown environment?

>sed **debebatur**, ut **opinor**, *fatis* tantae *origo* *urbis* maximique secundum *deorum* *opes* *imperii* *principium*. compressa cum geminum partum edidisset,

And of course we can use any kind of highlighting we like.  Let's use some HTML to make nouns blue and verbs green.

```tut:silent
val blue = "<span style=\"color:blue\">"
val green = "<span style=\"color:green\">"
val closer = "</span>"


val nounsHL = Highlighter(allNouns, opening = blue, closing = closer)
val verbsHL = Highlighter(allVerbs,opening = green, closing = closer)

val colorHiliters = Vector(verbsHL, nounsHL)
```

Here's the result.

```tut
livy_1_4_1.highlightForms(colorHiliters)
```

And here's what it looks like in an HTML setting.

>sed <span style="color:green">debebatur</span>, ut <span style="color:green">opinor</span>, <span style="color:blue">fatis</span> tantae <span style="color:blue">origo</span> <span style="color:blue">urbis</span> maximique secundum <span style="color:blue">deorum</span> <span style="color:blue">opes</span> <span style="color:blue">imperii</span> <span style="color:blue">principium</span>. compressa cum geminum partum edidisset,
