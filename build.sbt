ThisBuild / scalaVersion := "2.12.7"
ThisBuild / organization := "edu.holycross.shot"

lazy val latincorpus = (project in file("."))
  .settings(
    name := "latincorpus",
    version := "1.1.0",
    licenses += ("GPL-3.0",url("https://opensource.org/licenses/gpl-3.0.html")),

    resolvers += Resolver.jcenterRepo,
    resolvers += Resolver.bintrayRepo("neelsmith", "maven"),

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.1" % "test",
      "org.scala-lang.modules" %% "scala-xml" % "1.0.6",

      "edu.holycross.shot.cite" %% "xcite" % "4.1.0",
      "edu.holycross.shot" %% "ohco2" % "10.14.0",

      "edu.holycross.shot" %% "midvalidator" % "8.0.0",

      "edu.holycross.shot" %% "greek" % "2.4.0",
      "edu.holycross.shot" %% "latphone" % "2.5.2",
      "edu.holycross.shot" %% "tabulae" % "5.3.1",

      "edu.holycross.shot" %% "histoutils" % "2.1.0",



      "com.github.pathikrit" %% "better-files" % "3.5.0"
    ),
    tutTargetDirectory := file("docs"),
    tutSourceDirectory := file("tut"),

  )
  enablePlugins(TutPlugin)
