name := """platter"""
organization := "org.extrasalt"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"
libraryDependencies += guice
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test


//
//libraryDependencies += "com.mongodb.casbah" %% "casbah" % "2.1.5-1"
//libraryDependencies += "com.novus" %% "salat-core" % "0.0.8-20120223"
//libraryDependencies += "commons-codec" % "commons-codec" % "1.6"

// https://mvnrepository.com/artifact/org.mongodb.scala/mongo-scala-driver_2.11



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "org.extrasalt.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "org.extrasalt.binders._"
