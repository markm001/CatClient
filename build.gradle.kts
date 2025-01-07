plugins {
    id("java")
    id("maven-publish")
}

group = "com.ccat"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Testing
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.14.0")
    testImplementation("org.assertj:assertj-core:3.26.3")

    // Parse
    implementation("com.google.code.gson:gson:2.11.0")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.8")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications.create<MavenPublication>("libs") {
        groupId = "com.ccat"
        artifactId = "c4tclient"
        version = "1.0"

        from(components["java"])
    }
    repositories.maven("/tmp/utils")
}