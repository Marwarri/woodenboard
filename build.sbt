name := """WoodenBoard"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.webjars" % "angularjs" % "1.3.0-beta.2",
  "org.webjars" % "requirejs" % "2.1.11-1",
  "org.apache.httpcomponents" % "httpclient" % "4.3.1",
  "org.apache.httpcomponents" % "httpcore" % "4.3.1"
)

