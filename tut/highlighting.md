---
title: "Highlight text"
layout: page
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

val clustered = latinCorpus.clusterByCitation
val livy_1_4_1 = clustered(0)
```

We'll start with a `LatinTokenSequence`, in this case, Livy, 1.4.1.  It contains a vector of tokens.  How many?

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

The `LatinTokenSequence` can use all of this information in a variety of ways.  Let's do some old-fashioned Scala to read text contents of this sequence, by mapping each token to the text contents of its citable node, and combining them in a String.

```tut
livy_1_4_1.tokens.map(_.cn.text).mkString(" ")
```

The `LatinTokenSequence` can use a `MorphologyFilter` object to highlight tokens in formatted text.  Here's a filter for all first person singular forms.

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

(As a bonus, note how the `LatinTokenSequence` thoughtfully observes the token type, and adjusts white spacing appropriately for punctuation tokens.)
