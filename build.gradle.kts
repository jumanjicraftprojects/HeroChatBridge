plugins {
    java
    //id("com.github.johnrengelman.shadow") version "8.11"
}

group = "me.camdenorrb"
version = "1.0.0"


val waterfallVersion = "1.14-SNAPSHOT"
val spigotVersion = "1.14.4-R0.1-SNAPSHOT"

repositories {

    mavenCentral()

    flatDir("dirs" to "libs")
    /*
    flatDir {
        dirs("libs")
    }*/

    maven("https://jitpack.io") {
        name = "Jitpack"
    }

    maven("https://oss.sonatype.org/content/repositories/snapshots") {
        name = "BungeeCord"
    }

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "SpigotMC"
    }

    maven("https://nexus.scarsz.me/content/groups/public/") {
        name = "DiscordSRV"
    }
}

dependencies {

    //testCompile("junit", "junit", "4.12")

    compileOnly(":HeroChat")

    compileOnly("com.discordsrv:discordsrv:1.16.6")
    compileOnly("org.spigotmc:spigot-api:$spigotVersion")
    compileOnly("net.md-5:bungeecord-api:$waterfallVersion")

    implementation("com.github.camdenorrb:JCommons:1.0.2")

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}