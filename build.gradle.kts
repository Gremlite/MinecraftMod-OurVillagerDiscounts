plugins {
    id("fabric-loom")
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm").version(kotlinVersion)
}
base {
    val archivesBaseName: String by project
    archivesName.set(archivesBaseName)
}
val modVersion: String by project
version = modVersion
val mavenGroup: String by project
group = mavenGroup
repositories {}
dependencies {
    val minecraftVersion: String by project
    minecraft("com.mojang", "minecraft", minecraftVersion)
    val yarnMappings: String by project
    mappings("net.fabricmc", "yarn", yarnMappings, null, "v2")
    val loaderVersion: String by project
    modImplementation("net.fabricmc", "fabric-loader", loaderVersion)
    val fabricVersion: String by project
    modImplementation("net.fabricmc.fabric-api", "fabric-api", fabricVersion)
    val fabricKotlinVersion: String by project
    modImplementation("net.fabricmc", "fabric-language-kotlin", fabricKotlinVersion)
}
tasks {
    val javaVersion = JavaVersion.VERSION_21
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
        options.release.set(javaVersion.toString().toInt())
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }
    jar { from("LICENSE") { rename { "${it}_${base.archivesName}" } } }
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") { expand(mutableMapOf("version" to project.version)) }
    }
    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(javaVersion.toString())) }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
    }
}

loom {
    accessWidenerPath.set(file("src/main/resources/ourvillagerdiscounts.accesswidener"))
}

tasks.register("exportOutputs") {
    doLast {
        // Suppress deprecation warnings within this task's execution
        logger.quiet("Suppressing deprecation warning for this task to avoid gradle Task.project warning")


        val isCi = System.getenv("CI") != null // Detect CI environment (GitHub Actions or others)
        val version = project.version.toString()
        val minecraftVersion = project.findProperty("minecraftVersion")?.toString() ?: "unknown"

        if (isCi) {
            // In CI, write to $GITHUB_OUTPUT
            val githubOutput = System.getenv("GITHUB_OUTPUT") ?: return@doLast
            file(githubOutput).appendText("version=$version\n")
            file(githubOutput).appendText("minecraftVersion=$minecraftVersion\n")
        } else {
            // For local builds, just print to the console
            println("version=$version")
            println("minecraftVersion=$minecraftVersion")
        }
    }
}

tasks.named("build") {
    finalizedBy("exportOutputs")
}
