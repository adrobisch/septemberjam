name := "septemberjam"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "com.google.code.gson" % "gson" % "2.3",
  "com.jme3" % "jme3-core" % "3.1.0-snapshot-github",
  "com.jme3" % "jme3-desktop" % "3.1.0-snapshot-github",
  "com.jme3" % "jme3-lwjgl" % "3.1.0-snapshot-github",
  "com.jme3" % "jme3-bullet" % "3.1.0-snapshot-github",
  "com.jme3" % "jme3-jbullet" % "3.1.0-snapshot-github",
  "com.jme3" % "jme3-testdata" % "3.1.0-snapshot-github"
)

resolvers += "jme3 cloudbees snapshot repository" at "https://repository-drobisch.forge.cloudbees.com/snapshot"
