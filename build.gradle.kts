plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.flywaydb:flyway-core:10.14.0")
    implementation("com.zaxxer:HikariCP:5.1.0")
    compileOnly("org.projectlombok:lombok:1.18.32")
    implementation("org.postgresql:postgresql:42.7.3")
    // https://mvnrepository.com/artifact/org.flywaydb/flyway-database-postgresql
    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.15.0")
}

tasks.test {
    useJUnitPlatform()
}