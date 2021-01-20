plugins {
    java
    kotlin("jvm") version "1.4.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testCompile("junit", "junit", "4.12")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.json:json:20200518")
    implementation ("org.apache.commons:commons-lang3:3.6")
}
