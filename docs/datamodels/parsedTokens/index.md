---
title: The LatinParsedToken
layout: page
nav_order: 1
has_children: true
parent: Data models
---




**Version 7.0.0-preview**


# The `LatinParsedToken`

## Background

The `LatinParsedToken` is the atomic unit in the `latincorpus` library.  It presumes that for any text represented as a String or sequence of code points, you can associate an orthographic system capable of parsing 100% of the string into a series of classified tokens.  You can subsequently apply a morphological parser to tokens classified as lexical tokens, and by associating morphological analyses with each lexical token, create a sequence of `LatinParsedToken`s.  An example of this background work is described on [the Lingua Latina Legenda project's website](https://lingualatina.github.io/analysis/).




## Basic identity

A `LatinParsedToken` is a single token categorized as a `LexicalToken`, `NumericToken`, `PunctuationToken` or `PraenomenToken`.  

It is citable by a CTS URN that extends the canonical citation scheme for the text by one level to create a canonical citation for individual tokens. We'll use as an example a token identified as `32` within the canonically citable passage  `urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1`

```scala
token.urn
// res0: edu.holycross.shot.cite.CtsUrn = CtsUrn(
//   "urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.32"
// )
token.text
// res1: String = "scripserunt"
token.category
// res2: edu.holycross.shot.mid.orthography.MidTokenCategory = LexicalToken
```



## Associated morphological analyses

The token has a (possibly empty) Vector of morphological analyses; the Vector will be empty for tokens that are not categorized as `Lexical`  tokens, and for lexical tokens that have not been parsed.  Each analysis in the Vector is a `LemmatizedForm` (from the `tabulae` library).


```scala
token.analyses.size
// res3: Int = 1
token.analyses.head
// res4: LemmatizedForm = VerbForm(
//   "ls.n43092",
//   "",
//   "",
//   Third,
//   Plural,
//   Perfect,
//   Indicative,
//   Active
// )
```

The token has a series of Boolean functions, determining if any analyses belong to a given type of `LemmatizedForm`.  For the token in this example with the text `scripserunt`, the single analysis is specifically a finite verb type.  It also belongs to the broader `verbal` category that includes non-finite forms such as infinitives and participles.

```scala
token.finiteVerb
// res5: Boolean = true
token.verbal
// res6: Boolean = true
token.noun
// res7: Boolean = false
token.substantive
// res8: Boolean = false
```


For a given morphological property, you can extract all values found in the Vector of analyses.

```scala
token.valuesForCategory(PersonValues)
// res9: Vector[MorphologicalProperty] = Vector(Third)
token.valuesForCategory(TenseValues)
// res10: Vector[MorphologicalProperty] = Vector(Perfect)
token.valuesForCategory(MoodValues)
// res11: Vector[MorphologicalProperty] = Vector(Indicative)
token.valuesForCategory(VoiceValues)
// res12: Vector[MorphologicalProperty] = Vector(Active)
```

If no values are found for that property, the result is an empty Vector.

```scala
token.valuesForCategory(GenderValues)
// res13: Vector[MorphologicalProperty] = Vector()
```

## Lexical identity

You can test whether any of the analyses match a given lexeme, or any in a list of lexemes.

```scala
token.matchesLexeme("ls.n43092")
// res14: Boolean = true
token.matchesLexeme("fake.id")
// res15: Boolean = false
token.matchesAny(Vector("ls.n43092", "fake.id"))
// res16: Boolean = true
```



## URN expansion and serialization

The `tabulae` library's `LemmatizedForm` uses abbreviated identifiers.  The URNs can be expanded  by converting `LemamtizedForm`s to `LemmatizedFormUrns`.

```scala
val expanded  = token.analysisUrns()
// expanded: Vector[LemmatizedFormUrns] = Vector(
//   LemmatizedFormUrns(
//     CtsUrn("urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.32"),
//     "scripserunt",
//     Cite2Urn("urn:cite2:tabulae:ls.v1:n43092"),
//     Cite2Urn("urn:cite2:tabulae:morphforms.v1:324110004"),
//     LexicalToken
//   )
// )
```

These are useful for serializing to a plain-text representation which could be written to a file.
```scala
val cex = expanded.map(analysis => analysis.cex())
// cex: Vector[String] = Vector(
//   "urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.32#scripserunt#urn:cite2:tabulae:ls.v1:n43092#urn:cite2:tabulae:morphforms.v1:324110004#LexicalToken"
// )
import java.io.PrintWriter
new PrintWriter("onetoken.cex"){write(cex.mkString("\n"));close;}
// res17: PrintWriter = repl.MdocSession$App$$anon$1@37968445
```
