plugins {
  eclipse
  java
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

repositories {
  mavenLocal()
  mavenCentral()
}
