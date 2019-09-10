---
title: "Formatting features for tool-tip display"
layout: page
---




Let's look at another way you can format  a `LatinTokenSequence`, in this case, Livy, 1.4.1.  Here's how many tokens we're working with:

```scala
scala> livy_1_4_1.tokens.size
res2: Int = 23
```




The `LatinTokenSequence` can use a list of `MorphologyFilter`s to attach tool tips to tokens matching the filter. The tooltips display morphological information when users hover over the token in an HTML environment.  Let's create a filter to select all verb forms.

```scala
scala>  val mf = MorphologyFilter(pos = Some("verb"))
mf: edu.holycross.shot.tabulae.MorphologyFilter = MorphologyFilter(Some(verb),None,None,None,None,None,None,None,None,None)
 ```


## Raw

Here's Livy 1.4.1 with morphological information attached to verb forms.

```scala
scala> livy_1_4_1.hover(Vector(mf))
res3: String =
"sed <span color="green"><a href="#" data-tooltip="verb: third, singular, imperfect, indicative, passive" class="hoverclass">debebatur</a></span> , ut <span color="green"><a href="#" data-tooltip="verb: first, singular, present, indicative, passive" class="hoverclass">opinor</a></span> , fatis tantae origo urbis maximique secundum deorum opes imperii principium . compressa cum geminum partum edidisset ,



<style>
a.hoverclass {
  position: relative ;
}
a.hoverclass:hover::after {
  content: attr(data-tooltip) ;
  position: absolute ;
  top: 1.1em ;
  left: 1em ;
  min-width: 200px ;
  border: 1px #808080 solid ;
  padding: 8px ;
  z-index: 1 ;
  color: silver;
  background-color: white;
}
</style>
"
```


## Cooked

Not beautiful, but here's what it looks like in a markdown environment.  If you hover over the highlighted verbs, you should see their morphological identification.

>sed <span color="green"><a href="#" data-tooltip="verb: third, singular, imperfect, indicative, passive" class="hoverclass">debebatur</a></span> , ut <span color="green"><a href="#" data-tooltip="verb: first, singular, present, indicative, passive" class="hoverclass">opinor</a></span> , fatis tantae origo urbis maximique secundum deorum opes imperii principium . compressa cum geminum partum edidisset ,
><style> a.hoverclass {  position: relative ; }
> a.hoverclass:hover::after { content: attr(data-tooltip) ;   position: absolute ;  top: 1.1em ;   left: 1em ;   min-width: 200px ;   border: 1px #808080 solid ;
  padding: 8px ;  z-index: 1 ;  color: silver;   background-color: white; } </style>
