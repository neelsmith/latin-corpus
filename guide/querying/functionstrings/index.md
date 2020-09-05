---
title: "Matching and filtering: citable forms and function strings"
layout: page
nav_order: 3
parent: Matching and filtering data sets
---

> *Documentation compiled with version* **@VERSION@**


# Matching and filtering: citable forms and function strings

```scala mdoc:invisible
import edu.holycross.shot.latincorpus._
val hyginusUrl = "https://raw.githubusercontent.com/LinguaLatina/analysis/master/data/hyginus/hyginus-latc.cex"
val hyginus = LatinCorpus.fromUrl(hyginusUrl)
```

We've loaded a `LatinCorpus` of the *Fabulae* of Hyginus. (See [an example of how to do that](../../datamodels/parsedTokenSequence/).)



## `CitableForm`s

```scala mdoc:invisible
val citableForm = hyginus.citableForms().tail.head
```
We'll look at one that has a valid form for a conjunction.  The `CitableForm` is identified with the URN for the token.

```scala mdoc
citableForm.urn
```

It has a `ValidForm` option, which in turn has a URN for its morphological analysis.

```scala mdoc
val validForm = citableForm.form.get
validForm.urn
```

A `CitableForm`s can compose a descriptive "function strings" describing a broader syntactic category of a form.


```scala mdoc
citableForm.functionString
```


## Filtering `CitableForm`s

A token sequence can extract `CitableForm`s, either in a flat list, or grouped by token.


```scala mdoc
println("All tokens: " + hyginus.size)
println("Lexical tokens: " + hyginus.lexicalTokens.size)
println("Groups of citable forms clustered by token: " + hyginus.citableFormsPerToken().size)
println("Flat list of all citable forms: " + hyginus.citableForms().size)
```

Similarly, a token sequence can extract their descriptive strings, either in a flat list, or grouped by token.

```scala mdoc
println("Groups of function strings clustered by token: " + hyginus.functionStringsPerToken().size)
println("Flat list of all function strings: " + hyginus.functionStrings().size)
```

Filtering example:

```scala mdoc:silent
val citableForms = hyginus.citableForms()
val conjunctions = citableForms.filter(cf => cf.functionString.contains("conjunction"))
```

Display a few:

```scala mdoc
println(conjunctions.take(20).map(cf =>  cf.urn + ", " + cf.text).mkString("\n"))
```
