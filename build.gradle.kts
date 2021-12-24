import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
  `maven-publish`
  eclipse
  java
  signing
}

dependencies {
  implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
}


sourceSets {
  main {
    java {
      srcDirs("src")
    }
  }
}



fun getProp(key: String, default: String): String {
  var r : String = default
  if (project.hasProperty(key)) {
    r = project.getProperties().get(key) as String
  }
  return r
}

fun getTag(): String {
  val current = LocalDateTime.now()
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
  val snapshot = "SNAPSHOT_" + current.format(formatter)
  //println(snapshot)
  return getProp("tag", snapshot)
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = "org.adligo"
      artifactId = "tests4j"
      version = getTag()
      from(components["java"])
    }
  }
}


repositories {
  mavenLocal()
  mavenCentral()
}




