name := """cheers"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.webjars" % "foundation" % "5.5.1",
  "org.webjars" % "jquery" % "2.1.3",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23"
)
