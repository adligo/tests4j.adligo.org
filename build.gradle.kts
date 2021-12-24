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



val tag : String = project.getProperties().get("tag") as String

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = "org.adligo"
      artifactId = "tests4j"
      version = tag
      from(components["java"])
    }
  }
}


repositories {
  mavenLocal()
  mavenCentral()
}




