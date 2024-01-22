name := "AlbumsChallenge"

scalaVersion := "2.13.12"

val springBootVersion = "3.2.2"

libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion,
  "org.springframework.boot" % "spring-boot-starter-cache" % springBootVersion,
  "org.springframework" % "spring-context" % "6.1.3",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.16.1",
  "org.scalameta" %% "munit" % "1.0.0-M10" % Test
)
