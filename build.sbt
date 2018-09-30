name := "lerna"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= {
  val akkaHttpVersion = "10.1.3"
  val scalaTestVersion = "3.0.4"
  Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.slick" %% "slick" % "3.2.3",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3",
    "mysql"               %  "mysql-connector-java"  % "5.1.38",


    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "org.mockito" % "mockito-core" % "2.7.22" % Test,
    "com.typesafe.akka" %% "akka-testkit" % "2.5.12" % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion
  ).map(_.exclude("org.slf4j", "*")) ++ Seq("ch.qos.logback" % "logback-classic" % "1.2.3")
}
