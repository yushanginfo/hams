import HisDepends.*
import _root_.org.beangle.parent.Settings.*

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
    id = "chaostone",
    name = "Tihua Duan",
    email = "duantihua@gmail.com",
    url = url("http://github.com/duantihua")
  )
)

ThisBuild / description := "HAMS"
ThisBuild / homepage := Some(url("http://yushanginfo.github.io/hams/index.html"))
ThisBuild / resolvers += Resolver.mavenLocal

lazy val root = (project in file("."))
  .settings()
  .aggregate(core, base, wallet, account, leave, ebuy)

lazy val core = (project in file("core"))
  .settings(
    name := "hams-core",
    common,
    crossPaths := false,
    libraryDependencies ++= appDepends
  )

lazy val base = (project in file("base"))
  .enablePlugins(WarPlugin, TomcatPlugin)
  .settings(
    name := "hams-base",
    common,
    crossPaths := false,
    libraryDependencies ++= appDepends
  ).dependsOn(core)

lazy val wallet = (project in file("wallet"))
  .enablePlugins(WarPlugin, TomcatPlugin)
  .settings(
    name := "hams-wallet",
    common,
    crossPaths := false,
    libraryDependencies ++= appDepends
  ).dependsOn(core)

lazy val account = (project in file("account"))
  .enablePlugins(WarPlugin, TomcatPlugin)
  .settings(
    name := "hams-account",
    common,
    crossPaths := false,
    libraryDependencies ++= appDepends
  ).dependsOn(core)

lazy val leave = (project in file("leave"))
  .enablePlugins(WarPlugin, TomcatPlugin)
  .settings(
    name := "hams-leave",
    common,
    crossPaths := false,
    libraryDependencies ++= appDepends
  ).dependsOn(core)

lazy val ebuy = (project in file("ebuy"))
  .enablePlugins(WarPlugin, TomcatPlugin)
  .settings(
    name := "hams-ebuy",
    common,
    crossPaths := false,
    libraryDependencies ++= appDepends
  ).dependsOn(core)

val cxfVersion = "4.0.2"
val cxf_frontend_jaxws = "org.apache.cxf" % "cxf-rt-frontend-jaxws" % cxfVersion
val cxf_transports_http = "org.apache.cxf" % "cxf-rt-transports-http" % cxfVersion

lazy val landray = (project in file("landray"))
  .enablePlugins(WarPlugin, TomcatPlugin)
  .settings(
    name := "hams-landray",
    common,
    crossPaths := false,
    libraryDependencies ++= appDepends,
    libraryDependencies ++= Seq(cxf_frontend_jaxws, cxf_transports_http)
  ).dependsOn(core)

publish / skip := true
