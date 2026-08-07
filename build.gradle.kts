plugins {
    id("java")
    id("maven-publish")
}

group = "cat.breadcat.slate"
version = "0.4.0-beta"

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
