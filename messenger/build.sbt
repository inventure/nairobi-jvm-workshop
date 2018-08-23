name := """messenger"""
organization := "co.tala"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  guice,
  "com.h2database" % "h2" % "1.4.192",
  "com.typesafe.play" %% "play-slick" % "3.0.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "co.tala.nairobijvm.messenger.controllers._"

// Adds additional packages into resources/routes
// play.sbt.routes.RoutesKeys.routesImport += "co.tala.nairobijvm.messenger.binders._"

// disable default Play layout to enable SBT layout
disablePlugins(PlayLayoutPlugin)
PlayKeys.playMonitoredFiles ++= (sourceDirectories in(Compile, TwirlKeys.compileTemplates)).value
