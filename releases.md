# `latincorpus`: release notes

**2.1.1**: Updates library dependencies, and correctly uses `+` to demarcate morpheme boundaries.


**2.1.0**: Adds `hover` function to `LatinTokenSequence`s.


**2.0.0**: Removes the `LewisShort` object, instead using the object in the updated `tabulae` library.

**1.6.0**: Adds `Highlighter` class.  `LatinTokenSequence` can now highlight text from a Vector of `Highlighter`s.

**1.5.0**:  Token sequences can highlight token strings by part of speech, using configurable open/close highlighting strings.


**1.4.0**: `multipleLexemesHistogram` adds percent of total token occurences to item string.

**1.3.0**: Adds `multipleLexemesHistogram` function to `LatinCorpus`.

**1.2.1**:  Implement logging with airframe framework (issue #22).

**1.2.0**: Adds `strict` option to `fromFstLines` and `fromAnalyses` to permit continuing after failure on an individual token.

**1.1.0**:  Adds functions to `LatinCorpus` for finding histograms and concordances of tokens, lexemes, and lemmatized forms.

**1.0.0**: initial release
