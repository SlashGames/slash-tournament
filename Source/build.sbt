name := "slash-tournament"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.27",
  "com.google.guava" % "guava" % "16.0.1"
)     

play.Project.playJavaSettings
