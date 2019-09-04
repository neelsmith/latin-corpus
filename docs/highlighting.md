---
title: "Highlighting morphological features"
layout: page
---





## What's in a token

We'll start with a `LatinTokenSequence`, in this case, Livy, 1.4.1.  It contains a vector of tokens.  How many?

```scala
scala> livy_1_4_1.tokens.size
res2: Int = 23
```

Individual tokens carry a lot of information, including a citable node of text, and a list of possible morphological analyses:

```scala
scala> livy_1_4_1.tokens(0)
res3: edu.holycross.shot.latincorpus.LatinToken = LatinToken(CitableNode(urn:cts:omar:stoa0179.stoa001.omar_tkns:1.4.1.0,sed),LexicalToken,Vector(uninflected form: conjunction))

scala> // a citable node:
     | livy_1_4_1.tokens(0).cn
res5: edu.holycross.shot.ohco2.CitableNode = CitableNode(urn:cts:omar:stoa0179.stoa001.omar_tkns:1.4.1.0,sed)

scala> // possible analyses:
     | livy_1_4_1.tokens(0).analyses
res7: Vector[edu.holycross.shot.tabulae.LemmatizedForm] = Vector(uninflected form: conjunction)
```

The `LatinTokenSequence` can use all of this information in a variety of ways.  Let's do some old-fashioned Scala to read text contents of this sequence, by mapping each token to the text contents of its citable node, and combining them in a String.

```scala
scala> livy_1_4_1.tokens.map(_.cn.text).mkString(" ")
res8: String = sed debebatur , ut opinor , fatis tantae origo urbis maximique secundum deorum opes imperii principium . compressa cum geminum partum edidisset ,
```


## Highlight a single feature

The `LatinTokenSequence` can use a `MorphologyFilter` object to highlight tokens in formatted text.  Here's a filter for all first person singular forms.

```scala
scala>  val mf = MorphologyFilter(person = Some(First), grammaticalNumber = Some(Singular))
mf: edu.holycross.shot.tabulae.MorphologyFilter = MorphologyFilter(None,Some(First),Some(Singular),None,None,None,None,None,None,None)
 ```


Here's Livy 1.4.1 with first singulars highlighted.

```scala
scala> livy_1_4_1.highlightForms(mf)
res9: String = sed debebatur, ut **opinor**, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisset,
```

What does that output look like in a markdown environment?

>sed debebatur, ut **opinor**, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisse


Default highlighting uses markdown, but you can specify bracketing opening and closing text.  Let's use some HTML.

```scala
scala> val open = "<span style=\"color:green\">"
open: String = <span style="color:green">

scala> val closer = "</span>"
closer: String = </span>

scala> livy_1_4_1.highlightForms(mf, open, closer)
res10: String = sed debebatur, ut <span style="color:green">opinor</span>, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisset,
```

What does that look like when it's rendered?


> sed debebatur  ut <span style="color:green">opinor</span>, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisset,

(As a bonus, note how the `LatinTokenSequence` thoughtfully observes the token type, and adjusts white spacing appropriately for punctuation tokens.)

## Highlighting multiple features

You can attach a `MorphologyFilter` to opening and closing highlighting strings to create a `Highlighter`.  Here, too, the default highlighting strings are markdown strong emphasis ("**").  We'll let verb forms use the default, and define a distinct highlighter for nouns.

```scala
val allVerbs = MorphologyFilter(pos = Some("verb"))
val verbsHL = Highlighter(allVerbs)

val allNouns = MorphologyFilter(pos = Some("noun"))
val nounsHL = Highlighter(allNouns, opening = "*", closing = "*")

val hiliters = Vector(verbsHL, nounsHL)
```

Now we can just pass the `hiliters` list in, to get this result.

```scala
scala> livy_1_4_1.highlightForms(hiliters)
res11: String = sed **debebatur**, ut **opinor**, *fatis* tantae *origo* *urbis* maximique secundum *deorum* *opes* *imperii* *principium*. compressa cum geminum partum edidisset,
```

What does that look like in a markdown environment?

>sed **debebatur**, ut **opinor**, *fatis* tantae *origo* *urbis* maximique secundum *deorum* *opes* *imperii* *principium*. compressa cum geminum partum edidisset,

And of course we can use any kind of highlighting we like.  Let's use some HTML to make nouns blue and verbs green.

```scala
val blue = "<span style=\"color:blue\">"
val green = "<span style=\"color:green\">"
val closer = "</span>"


val nounsHL = Highlighter(allNouns, opening = blue, closing = closer)
val verbsHL = Highlighter(allVerbs,opening = green, closing = closer)

val colorHiliters = Vector(verbsHL, nounsHL)
```

Here's the result.

```scala
scala> livy_1_4_1.highlightForms(colorHiliters)
res12: String = sed <span style="color:green">debebatur</span>, ut <span style="color:green">opinor</span>, <span style="color:blue">fatis</span> tantae <span style="color:blue">origo</span> <span style="color:blue">urbis</span> maximique secundum <span style="color:blue">deorum</span> <span style="color:blue">opes</span> <span style="color:blue">imperii</span> <span style="color:blue">principium</span>. compressa cum geminum partum edidisset,
```

And here's what it looks like in an HTML setting.

>sed <span style="color:green">debebatur</span>, ut <span style="color:green">opinor</span>, <span style="color:blue">fatis</span> tantae <span style="color:blue">origo</span> <span style="color:blue">urbis</span> maximique secundum <span style="color:blue">deorum</span> <span style="color:blue">opes</span> <span style="color:blue">imperii</span> <span style="color:blue">principium</span>. compressa cum geminum partum edidisset,
