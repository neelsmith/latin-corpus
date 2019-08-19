---
title: "Implementing a Latin Corpus: overview"
layout: page
---


## Implementing a `LatinToken`

The citable text of a `LatinToken` is a `CitableNode` (from the `ohco2` library).  Its category is an `MidTokenCategory` (from the `projectvalidator` library).  Its associated list of morphological analyses is a Vector of `LemmatizedToken`s (from the `tabulae` library).  If the Vector of analyses is empty, that means the token could not be morphologically analyzed.


---
INSERT REAL WORD COMPILED EXAMPLE THROUGH `tut`
---

## Relations of main code libraries

The following stack of libaries provides successively higher orders of language-specific text processing:

1. `xcite`: concerned only with citation; not aware of what citation refers to
2. `ohco2`: concerned with a citable corpus.  The corpus is a sequence of nodes; each node associates a citation (a CTS URN) and a text string.  Not concerned with contents of the text string.
3. `midvalidator` trait: concerned only with contents of strings, not citation. Implementatons define a valid set of code points, and how they can be tokenized.  Using an `midvalidator` with an `ohco2` library, you can ensure that your citable content can be semantically tokenized.
4. `tabulae`:  concerned only with string values of lexical tokens. Supports both building binary parsers with the SFST toolkit, and working with the output of a parser.
5.  `latincorpus`:  concerned with a citable corpus in a defined orthography, with fully parsed lexical tokens.  Enables working with morphological analyses and textual contents within the structure of a citable corpus.
