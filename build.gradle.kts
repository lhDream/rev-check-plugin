import com.github.gradle.node.pnpm.task.PnpmTask

plugins {
    kotlin("jvm") version "1.9.24"
    id("com.github.node-gradle.node") version "7.0.2"
    id("run.halo.plugin.devtools") version "0.2.0"
}

group = "run.halo.starter"

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/releases")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.spring.io/milestone")
}

dependencies {
    implementation(platform("run.halo.tools.platform:plugin:2.20.0-SNAPSHOT"))
    compileOnly("run.halo.app:api")

    testImplementation(kotlin("test"))
    testImplementation("run.halo.app:api")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

}

tasks.test {
    useJUnitPlatform()
}
tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

node{
    nodeProjectDir.set(file("${project.projectDir}/ui"))
}

tasks.register<PnpmTask>("buildFrontend"){
    args = listOf("build")
    dependsOn("installDepsForUI")
}

tasks.register<PnpmTask>("installDepsForUI"){
    args = listOf("install")
}

tasks.named("compileJava") {
    dependsOn("buildFrontend")
}

kotlin {
    jvmToolchain(17)
}

halo{
    version = "2.20"
}