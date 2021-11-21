# yanl - yet another native loader

Yet another Native library extractor/loader for the 
[JVM](https://adoptium.net), written in [Kotlin](https://kotlinlang.org).

# why
other libraries simply don't fit my needs and/or aren't as 
expandable/configurable as I'd like.

# how use
this is going to go over how to setup 
[yanl](https://github.com/stardust-enterprises/yanl) on a 
[gradle](https://gradle.org) project using a Kotlin DSL buildscript.

first you need to import the library, you can either import it from
the [maven central](https://repo1.maven.org/maven2/) repository or 
from [jitpack](https://jitpack.io) automatic builds.

for [maven central](https://repo1.maven.org/maven2/), just add
[yanl](https://github.com/stardust-enterprises/yanl) to your dependencies block:
```kotlin
// change to the latest version
val YANL_VERSION = "1.0.0" 

dependencies {
    implementation("fr.stardustenterprises:yanl:$YANL_VERSION")
}
```

for [jitpack](https://jitpack.io), add the repository as well as the dependency,
this time using the github domain:

```kotlin
// change to the latest github release, commit, or branch name
val YANL_RELEASE = "1.0.0" 

repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.stardust-enterprises:yanl:$YANL_RELEASE")
}
```

# how work
this library can act as a simple drop-in replacement to your standard
`System.load` or `System.loadLibrary`. 

consider the following example:
```java
public class Program {
    public static void main(String[] args) {
        // requires the native file to be in path
        System.loadLibrary("coollib");
        
        // do stuff
        // ...
    }
}
```
this would require the library file to be somewhere on the `PATH` of the
operating system.

one way of getting around this problem would be to extract the library from
the jar archive to a known folder, then to load it. this approach works
but also requires to extract and load the correct library for your
processor architecture and operating system. 

this is when [yanl](https://github.com/stardust-enterprises/yanl) 
comes into play.

[yanl](https://github.com/stardust-enterprises/yanl) takes care of platform
detection, library extraction and location.

[//]: # (and even lets you customize which version of the library you would
 want to load based on processor flags)

taking back that previous example:
```java
import fr.stardustenterprises.yanl.loader.NativeLoader;

public class Program {
    public static void main(String[] args) {
        // load the library using yanl's default loader
        NativeLoader.getDefault().loadLibrary("coollib");
        
        // do more stuff 
        // ...
    }
}
```
this time [yanl](https://github.com/stardust-enterprises/yanl) will handle
everything according to the [NativeLoader](https://github.com/stardust-enterprises/yanl/blob/trunk/src/main/kotlin/fr/stardustenterprises/yanl/loader/NativeLoader.kt)
[default settings](#).

you can of course change those setting by creating yourself a loader instance:
```java
// library not done, example snippet not available, check back later!
```

# licensing
this project is under the [MIT license](https://github.com/stardust-enterprises/yanl/blob/trunk/LICENSE).