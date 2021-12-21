# yanl - yet another native loader [![Maven Central](https://maven-badges.herokuapp.com/maven-central/fr.stardustenterprises/yanl/badge.svg)](https://maven-badges.herokuapp.com/maven-central/fr.stardustenterprises/yanl)

Yet another Native library extractor/loader for the 
[JVM](https://adoptium.net), written in [Kotlin](https://kotlinlang.org).

# why
other libraries simply don't fit my needs and/or aren't as 
expandable/configurable as I'd like.

# how use
to import from [maven central](https://repo1.maven.org/maven2/), just add
[yanl](https://github.com/stardust-enterprises/yanl) to your dependencies block:
```kotlin
// change to the latest version
val YANL_VERSION = "0.3.0" 

dependencies {
    implementation("fr.stardustenterprises:yanl:$YANL_VERSION")
}
```

# how work
this library can act as a replacement from your standard `System.loadLibrary`,
with a bit more configuration setup

consider the following example:
```java
package your.project;

public class Program {
    public static void main(String[] args) {
        // requires the native file to be in path
        System.loadLibrary("coollib");
        
        // do stuff...
    }
}
```
this would require the library file to be somewhere on the `PATH` of the
operating system.

one way of getting around this problem would be to extract the library from
the jar archive to a known folder, then to load it. this approach works
but also requires extracting and loading the correct library for your
processor architecture and operating system. 


[yanl](https://github.com/stardust-enterprises/yanl) takes care of platform
detection, library extraction and location.

[//]: # (and even lets you customize which version of the library you would
 want to load based on processor flags)

taking back that previous example, let's see an extensive configuration:
```java
package your.project;

import fr.stardustenterprises.yanl.NativeLayout;
import fr.stardustenterprises.yanl.NativeLoader;
import fr.stardustenterprises.yanl.PlatformContext;
import fr.stardustenterprises.yanl.TempExtractor;

public class Program {
    private static final NativeLoader nativeLoader = new NativeLoader.Builder()
            // this tells the loader to look for libraries
            // in the /META-INF/natives directory
            .root("/META-INF/natives")

            // this sets where the loader is going
            // to look for your libraries. you can
            // check NativeLayout for default layouts
            .layout(NativeLayout.HIERARCHICAL_LAYOUT)
            
            // you can also only specify the layout's
            // pattern, and it'll create it for you
            //.layout("{os}/{arch}/{name}")
            
            // set the extractor instance,
            // see the Extractor interface
            .extractor(new TempExtractor())

            // set the context instance,
            // see the Context interface
            .context(new PlatformContext())

            // create the loader
            .build();

    public static void main(String[] args) {
        // replaces System.loadLibrary
        nativeLoader.loadLibrary("coollib");

        // do cooler stuff...
    }
}
```

***Note:*** *the above example is the* ***default*** *configuration for the 
[NativeLoader](https://github.com/stardust-enterprises/yanl/blob/trunk/src/main/kotlin/fr/stardustenterprises/yanl/NativeLoader.kt), 
so you could replace the entire building process with the following:*

```java
package your.project;

import fr.stardustenterprises.yanl.NativeLoader;

public class Program {
    private static final NativeLoader defaultLoader = new NativeLoader.Builder().build();
}
```

# licensing
this project is under the [ISC license](https://github.com/stardust-enterprises/yanl/blob/trunk/LICENSE).
