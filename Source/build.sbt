name := "slash-tournament"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "com.google.guava" % "guava" % "16.0.1"
)     

play.Project.playJavaSettings
