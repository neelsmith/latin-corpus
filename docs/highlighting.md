---
title: "Highlight text"
layout: page
---




We'll start with a `LatinTokenSequence`, in this case, Livy, 1.4.1.  It contains a vector of tokens.  How many?

```scala
scala> livy_1_4_1.tokens.size
res0: Int = 23
```

Individual tokens carry a lot of information, including a citable node of text, and a list of possible morphological analyses:

```scala
scala> livy_1_4_1.tokens(0)
res1: edu.holycross.shot.latincorpus.LatinToken = LatinToken(CitableNode(urn:cts:omar:stoa0179.stoa001.omar_tkns:1.4.1.0,sed),LexicalToken,Vector(uninflected form: conjunction))

scala> // a citable node:
     | livy_1_4_1.tokens(0).cn
res3: edu.holycross.shot.ohco2.CitableNode = CitableNode(urn:cts:omar:stoa0179.stoa001.omar_tkns:1.4.1.0,sed)

scala> // possible analyses:
     | livy_1_4_1.tokens(0).analyses
res5: Vector[edu.holycross.shot.tabulae.LemmatizedForm] = Vector(uninflected form: conjunction)
```

The `LatinTokenSequence` can use all of this information in a variety of ways.  Let's do some old-fashioned Scala to read text contents of this sequence, by mapping each token to the text contents of its citable node, and combining them in a String.

```scala
scala> livy_1_4_1.tokens.map(_.cn.text).mkString(" ")
res6: String = sed debebatur , ut opinor , fatis tantae origo urbis maximique secundum deorum opes imperii principium . compressa cum geminum partum edidisset ,
```

The `LatinTokenSequence` can use a `MorphologyFilter` object to highlight tokens in formatted text.  Here's a filter for all first person singular forms.

```scala
scala>  val mf = MorphologyFilter(person = Some(First), grammaticalNumber = Some(Singular))
mf: edu.holycross.shot.tabulae.MorphologyFilter = MorphologyFilter(None,Some(First),Some(Singular),None,None,None,None,None,None,None)
 ```


Here's Livy 1.4.1 with first singulars highlighted.

```scala
scala> livy_1_4_1.highlightForms(mf)
res7: String = sed debebatur, ut **opinor**, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisset,
```

What does that output look like in a markdown environment?

>sed debebatur, ut **opinor**, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisse


Default highlighting uses markdown, but you can specifify bracketing opening and closing text.  Let's use some HTML.

```scala
scala> val open = "<span style=\"color:green\">"
open: String = <span style="color:green">

scala> val closer = "</span>"
closer: String = </span>

scala> livy_1_4_1.highlightForms(mf, open, closer)
res8: String = sed debebatur, ut <span style="color:green">opinor</span>, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisset,
```

What does that look like when it's rendered?


> sed debebatur  ut <span style="color:green">opinor</span>, fatis tantae origo urbis maximique secundum deorum opes imperii principium. compressa cum geminum partum edidisset,

(As a bonus, note how the `LatinTokenSequence` thoughtfully observe the token type, and adjusts white spacing appropriately for punctuation tokens.)
