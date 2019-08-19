---
title: "Implementing a Latin Corpus: overview"
layout: page
---


## Implementing a `LatinToken`

The citable text of a `LatinToken` is a `CitableNode` (from the `ohco2` library).  Its category is an `MidTokenCategory` (from the `projectvalidator` library).  Its associated list of morphological analyses is a Vector of `LemmatizedToken`s (from the `tabulae` library).  If the Vector of analyses is empty, that means the token could not be morphologically analyzed.

```tut:invisible
import edu.holycross.shot.latincorpus._

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.tabulae._
import edu.holycross.shot.mid.validator._
```

### Example of building a `LatinToken:`

```tut:silent

// 1. A CitableNode
val urn = CtsUrn("urn:cts:latinLit:phi0959.phi006:1.1.1")
val cn = CitableNode(urn,"In")

// 2. An MidTokenCategory
val cateogory = LexicalToken

// 3. A Vector of analyses (in this case, only 1)
val analyses = Vector(
  IndeclinableForm("ls.n22111", "latcommon.n22111","latcommon.indeclinfl1", Preposition)
)

val latinToken = LatinToken(cn, cateogory, analyses)
```

### Example of using a `LatinToken`:

```
assert(latinToken.urn == urn)
assert(latinToken.text == "In")
assert(latinToken.category == LexicalToken)
assert(latinToken.analyses.size == 1)

val analysis = latinToken.analyses(0)
// Use Scala pattern matching to get type-specific analysis:
val indeclAnalysis : IndeclinableForm = analysis match {
  case indecl : IndeclinableForm => indecl
  case _ => throw new Exception("Hey, that wasn't an indeclinable!")
}
assert(indeclAnalysis.pos == Preposition)
```

## Relations of main code libraries

The following stack of libaries provides successively higher orders of language-specific text processing:

1. `xcite`: concerned only with citation; not aware of what citation refers to
2. `ohco2`: concerned with a citable corpus.  The corpus is a sequence of nodes; each node associates a citation (a CTS URN) and a text string.  Not concerned with contents of the text string.
3. `midvalidator` trait: concerned only with contents of strings, not citation. Implementatons define a valid set of code points, and how they can be tokenized.  Using an `midvalidator` with an `ohco2` library, you can ensure that your citable content can be semantically tokenized.
4. `tabulae`:  concerned only with string values of lexical tokens. Supports both building binary parsers with the SFST toolkit, and working with the output of a parser.
5.  `latincorpus`:  concerned with a citable corpus in a defined orthography, with fully parsed lexical tokens.  Enables working with morphological analyses and textual contents within the structure of a citable corpus.
