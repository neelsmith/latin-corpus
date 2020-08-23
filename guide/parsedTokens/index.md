---
title: The LatinParsedToken
layout: page
nav_order: 1
has_children: true
---




**Version @VERSION@**


# The `LatinParsedToken`

```scala mdoc:invisible
//
// Load a token to use as example on this page:
//
import edu.holycross.shot.latincorpus._
import edu.holycross.shot.tabulae._
val cexLine = Vector("urn:cite2:linglat:tkns.v1:2020_08_15_25264#Record 2020_08_15_25264#urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1.32#scripserunt#urn:cite2:tabulae:ls.v1:n43092#urn:cite2:tabulae:morphforms.v1:324110004#LexicalToken#25264")
val corpus = LatinCorpus(cexLine)
val token = corpus.tokens.head
```

## Basic identity

A `LatinParsedToken` is a single token categorized as a `LexicalToken`, `NumericToken`, `PunctuationToken` or `PraenomenToken`.

It is citable by a CTS URN that extends the canonical citation scheme for the text by one level to create a canonical citation for individual tokens. We'll use as an example a token identified as `32` within the canonically citable passage  `urn:cts:latinLit:stoa1263.stoa001.hc_tkns:108a.1`

```scala mdoc
token.urn
token.text
token.category
```



## Associated morphological analyses

The token has a (possibly empty) Vector of morphological analyses; the Vector will be empty for tokens that are not categorized as `Lexical`  tokens, and for lexical tokens that have not been parsed.  Each analysis in the Vector is a `LemmatizedForm` (from the `tabulae` library).


```scala mdoc
token.analyses.size
token.analyses.head
```

The token has a series of Boolean functions, determining if any analyses belong to a given type of `LemmatizedForm`.  For the token in this example with the text `scripserunt`, the single analysis is specifically a finite verb type.  It also belongs to the broader `verbal` category that includes non-finite forms such as infinitives and participles.

```scala mdoc
token.finiteVerb
token.verbal
token.noun
token.substantive
```


For a given morphological property, you can extract all values found in the Vector of analyses.

```scala mdoc
token.valuesForCategory(PersonValues)
token.valuesForCategory(TenseValues)
token.valuesForCategory(MoodValues)
token.valuesForCategory(VoiceValues)
//token.valuesForCategory(GrammaticalNumberValues)
```

If no values are found for that property, the result is an empty Vector.

```scala mdoc
token.valuesForCategory(GenderValues)
```

## Lexical identity

You can test whether any of the analyses match a given lexeme, or any in a list of lexemes.

```scala mdoc
token.matchesLexeme("ls.n43092")
token.matchesLexeme("fake.id")
token.matchesAny(Vector("ls.n43092", "fake.id"))
```



## URN expansion and serialization

The `tabulae` library's `LemmatizedForm` uses abbreviated identifiers.  The URNs can be expanded  by converting `LemamtizedForm`s to `LemmatizedFormUrns`.

```scala mdoc
val expanded  = token.analysisUrns()
```

These are useful for serializing to a plain-text representation which could be written to a file.
```scala mdoc
val cex = expanded.map(analysis => analysis.cex())
import java.io.PrintWriter
new PrintWriter("onetoken.cex"){write(cex.mkString("\n"));close;}
```
