


## Latin language processing

This stack of libaries provides successively higher orders of language-specific text processing:

1. `xcite`: concerned only with citation; not aware of what citation refers to
2. `ohco2`: concerned with a citable corpus.  The corpus is a sequence of nodes; each node associates a citation (a CTS URN) and a text string.  Not concerned with contents of the text string.
3. `midvalidator` trait: concerned only with contents of strings, not citation. Implementatons define a valid set of code points, and how they can be tokenized.  Using an `midvalidator` with an `ohco2` library, you can ensure that your citable content can be semantically tokenized.
- `tabulae`:  concerned only with string values of lexical tokens. Supports both building binary parsers with the SFST toolkit, and working with the output of a parser.
-  `latincorpus`:  concerned with a citable corpus in a defined orthography, with fully parsed lexical tokens.  Enables working with morphological analyses and textual contents within the structure of a citable corpus.
