---
title: "Matching and filtering: citable forms and function strings"
layout: page
nav_order: 3
parent: Matching and filtering data sets
---

> *Documentation compiled with version* **7.0.0-pr6**


# Matching and filtering: citable forms and function strings


We've loaded a `LatinCorpus` of the *Fabulae* of Hyginus. (See [an example of how to do that](../../datamodels/parsedTokenSequence/).)



## `CitableForm`s

We'll look at one that has a valid form for a conjunction.  The `CitableForm` is identified with the URN for the token.

```scala
citableForm.urn
// res0: edu.holycross.shot.cite.CtsUrn = CtsUrn(
//   "urn:cts:latinLit:stoa1263.stoa001.hc_tkns:t.1.1"
// )
```

It has a `ValidForm` option, which in turn has a URN for its morphological analysis.

```scala
val validForm = citableForm.form.get
// validForm: edu.holycross.shot.tabulae.ValidForm = ValidUninflectedForm(
//   Cite2Urn("urn:cite2:tabulae:morphforms.v1:00000000A"),
//   Conjunction
// )
validForm.urn
// res1: edu.holycross.shot.cite.Cite2Urn = Cite2Urn(
//   "urn:cite2:tabulae:morphforms.v1:00000000A"
// )
```

A `CitableForm`s can compose a descriptive "function strings" describing a broader syntactic category of a form.


```scala
citableForm.functionString
// res2: String = "conjunction"
```


## Filtering `CitableForm`s

A token sequence can extract `CitableForm`s, either in a flat list, or grouped by token.


```scala
println("All tokens: " + hyginus.size)
// All tokens: 32463
println("Lexical tokens: " + hyginus.lexicalTokens.size)
// Lexical tokens: 27705
println("Groups of citable forms clustered by token: " + hyginus.citableFormsPerToken().size)
// Groups of citable forms clustered by token: 27705
println("Flat list of all citable forms: " + hyginus.citableForms().size)
// Flat list of all citable forms: 50102
```

Similarly, a token sequence can extract their descriptive strings, either in a flat list, or grouped by token.

```scala
println("Groups of function strings clustered by token: " + hyginus.functionStringsPerToken().size)
// Groups of function strings clustered by token: 27705
println("Flat list of all function strings: " + hyginus.functionStrings().size)
// Flat list of all function strings: 50102
```

Filtering example:

```scala
val citableForms = hyginus.citableForms()
val conjunctions = citableForms.filter(cf => cf.functionString.contains("conjunction"))
```

Display a few:

```scala
println(conjunctions.take(20).map(cf =>  cf.urn + ", " + cf.text).mkString("\n"))
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:t.1.1, EX
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.1.0, Ex
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.1.3, ex
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.1.5, et
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.1.11, ex
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.1.13, et
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.2.0, Ex
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.2.2, et
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.3.0, Ex
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.3.2, et
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.3.23, et
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.3.30, et
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.4.0, Ex
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.4.2, et
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.5.0, Ex
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.5.2, et
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.6.0, Ex
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.6.2, et
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.7.0, Ex
// urn:cts:latinLit:stoa1263.stoa001.hc_tkns:pr.7.2, et
```
