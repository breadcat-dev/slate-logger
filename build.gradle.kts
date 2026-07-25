plugins {
    id("java")
    id("maven-publish")
}

group = "cat.breadcat"
version = "0.1.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {

}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            artifactId = rootProject.name
        }
    }
}
