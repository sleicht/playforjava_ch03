name := "warehouse"
organization := "com.example"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.11"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

libraryDependencies += filters
libraryDependencies += "com.adrianhurt" %% "play-bootstrap" % "1.0-P25-B3"