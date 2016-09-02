name := """lunch-mator"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  cache,
  ws,
  specs2 % Test,
  "org.postgresql" % "postgresql" % "9.4-1204-jdbc4",
  "com.typesafe.play" %% "play-slick" % "1.1.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.0",
  "org.webjars.npm" % "angular__common" % "2.0.0-rc.5",
  "org.webjars.npm" % "angular__compiler" % "2.0.0-rc.5",
  "org.webjars.npm" % "angular__core" % "2.0.0-rc.5",
  "org.webjars.npm" % "angular__forms" % "0.3.0",
  "org.webjars.npm" % "angular__http" % "2.0.0-rc.5",
  "org.webjars.npm" % "angular__platform-browser" % "2.0.0-rc.5",
  "org.webjars.npm" % "angular__platform-browser-dynamic" % "2.0.0-rc.5",
  "org.webjars.npm" % "angular__router" % "3.0.0-rc.1",
  "org.webjars.npm" % "angular__upgrade" % "2.0.0-rc.5",
  "org.webjars.npm" % "core-js" % "2.4.0",
  "org.webjars.npm" % "reflect-metadata" % "0.1.3",
  "org.webjars.npm" % "rxjs" % "5.0.0-beta.6",
  "org.webjars.npm" % "systemjs" % "0.19.27",
  "org.webjars.npm" % "zone.js" % "0.6.12",
  "org.webjars.npm" % "angular2-in-memory-web-api" % "0.0.15",
  "org.webjars.npm" % "bootstrap-sass" % "3.3.6"
)

TypescriptKeys.moduleKind := "commonjs"

TypescriptKeys.sourceMap := true

TypescriptKeys.experimentalDecorators := true

TypescriptKeys.emitDecoratorMetadata := true

TypescriptKeys.moduleResolutionKind := "Classic"

TypescriptKeys.paths := Map(
  "@angular/common" -> List("angular__common/index"),
  "@angular/compiler" -> List("angular__compiler/index"),
  "@angular/core" -> List("angular__core/index"),
  "@angular/forms" -> List("angular__forms/index"),
  "@angular/http" -> List("angular__http/index"),
  "@angular/platform-browser" -> List("angular__platform-browser/index"),
  "@angular/platform-browser-dynamic" -> List("angular__platform-browser-dynamic/index"),
  "@angular/router" -> List("angular__router/index"),
  "@angular/router-deprecated" -> List("angular__router-deprecated/index"),
  "@angular/upgrade" -> List("angular__upgrade/index"),
  "@angular/core/testing" -> List("angular__core/testing"),
  "@angular/http/testing" -> List("angular__http/testing")
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
