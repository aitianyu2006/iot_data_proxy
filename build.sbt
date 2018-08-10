name := "iot_data_proxy"

scalaVersion := "2.11.11"

lazy val commonSettings = Seq(
  organization := "com.winstartech.proxy",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.11.11"
)


lazy val `data_proxy` = project
  .enablePlugins(JavaAppPackaging)
  .settings(commonSettings: _*)
  .settings(
    name := "`data_proxy",
    libraryDependencies ++= dependencies.type_conf ++ dependencies.akka ++ dependencies.mina
  )


lazy val dependencies = new {

  val type_conf = Seq("com.typesafe" % "config" % "1.3.1")
  val akka = Seq("com.typesafe.akka" %% "akka-actor" % "2.5.14")
  val mina = Seq("org.apache.mina" % "mina-core" % "2.0.19","org.apache.mina" % "mina-coap" % "3.0.0-M2")

}

//libraryDependencies ++= {
//  "com.typesafe.akka" %% "akka-actor" % "2.5.14",
//  "com.typesafe.akka" %% "akka-remote" % "2.5.14",
//  "com.typesafe.akka" %% "akka-cluster" % "2.5.14"
//}

//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.14"