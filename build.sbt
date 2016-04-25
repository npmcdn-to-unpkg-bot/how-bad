name := "how-bad"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "postgresql" % "postgresql" % "8.4-702.jdbc4"
)

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
