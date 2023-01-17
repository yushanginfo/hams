import _root_.org.beangle.parent.Settings._
import HisDepends._

ThisBuild / organization := "net.yushanginfo.hams"
ThisBuild / version := "0.0.1-SNAPSHOT"

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/yushanginfo/hams"),
    "scm:git@github.com:yushanginfo/hams.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id    = "chaostone",
    name  = "Tihua Duan",
    email = "duantihua@gmail.com",
    url   = url("http://github.com/duantihua")
  )
)

ThisBuild / description := "HAMS"
ThisBuild / homepage := Some(url("http://yushanginfo.github.io/hams/index.html"))
ThisBuild / resolvers += Resolver.mavenLocal

lazy val root = (project in file("."))
  .settings()
  .aggregate(core,base,wallet)

lazy val core = (project in file("core"))
  .settings(
    name := "hams-core",
    common,
    crossPaths := false,
    libraryDependencies ++= appDepends
  )

lazy val base = (project in file("base"))
  .enablePlugins(WarPlugin,TomcatPlugin)
  .settings(
    name := "hams-base",
    common,
    crossPaths := false,
    libraryDependencies ++= appDepends
  ).dependsOn(core)

lazy val wallet = (project in file("wallet"))
  .enablePlugins(WarPlugin,TomcatPlugin)
  .settings(
    name := "hams-wallet",
    common,
    crossPaths := false,
    libraryDependencies ++= appDepends
  ).dependsOn(core)

publish / skip := true
