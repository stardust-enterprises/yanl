# yanl - yet another native loader [![Maven Central][badge-mvnc]][yanl-mvnc]

yet another native library loader and extractor for the [JVM][jvm], written in [Kotlin][kotlin].

# why

other libraries simply don't fit my needs and/or are not as expandable/configurable as we would like.

# how use

you can import [yanl][yanl] from [maven central][mvnc] just by adding it to your dependencies:

## gradle

```kotlin
dependencies {
    implementation("fr.stardustenterprises:yanl:0.3.0")
}
```

## maven

```xml

<dependency>
    <groupId>fr.stardustenterprises</groupId>
    <artifactId>yanl</artifactId>
    <version>0.3.0</version>
</dependency>
```

# how work

this library can act as a replacement from your standard `System.loadLibrary`, with a bit more configuration setup.

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

this would require the library file to be somewhere on the `PATH` of the host machine.

one way of getting around this problem would be to extract the library from the jar archive to a known folder, then to
load it. this approach works but also requires extracting and loading the correct library for the correct processor
architecture (arch) and operating system (OS).

and this is where [yanl][yanl] performs: we take care of platform detection, library extraction and location with ease.

[//]: # (and even let you customize which version of the library you would
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
        nativeLoader.loadLibrary("coolLib");

        // do cooler stuff...
    }
}
```

***Note:** the above example is the **default** configuration for the [NativeLoader][blob-native-loader], so you could
replace the entire building process with the following:*

```java
package your.project;

import fr.stardustenterprises.yanl.NativeLoader;

public class Program {
    private static final NativeLoader defaultLoader = new NativeLoader.Builder().build();
}
```

# troubleshooting

if you ever encounter any problem **related to the library**, you can [open an issue][new-issue] describing what the
problem is. please, be as precise as you can, so that we can help you asap. we're most likely to close the issue if it
isn't related to our work.

# contributing

you can contribute by [forking the repository][fork], making your changes and [creating a new pull request][new-pr]
describing what you changed, why and how.

# licensing

this project is under the [ISC license][blob-license].


<!-- Links -->

[jvm]: https://adoptium.net "adoptium website"

[kotlin]: https://kotlinlang.org "kotlin website"

[yanl]: https://github.com/stardust-enterprises/yanl "yanl github repository"

[fork]: https://github.com/stardust-enterprises/yanl/fork "fork this repository"

[new-pr]: https://github.com/stardust-enterprises/yanl/pulls/new "create a new pull request"

[new-issue]: https://github.com/stardust-enterprises/yanl/issues/new "create a new issue"

[mvnc]: https://repo1.maven.org/maven2/ "maven central website"

[yanl-mvnc]: https://maven-badges.herokuapp.com/maven-central/fr.stardustenterprises/yanl "maven central repository"

[blob-license]: https://github.com/stardust-enterprises/yanl/blob/trunk/LICENSE "LICENSE source file"

[blob-native-loader]: https://github.com/stardust-enterprises/yanl/blob/trunk/src/main/kotlin/fr/stardustenterprises/yanl/NativeLoader.kt "NativeLoader.kt source file"

<!-- Badges -->

[badge-mvnc]: https://maven-badges.herokuapp.com/maven-central/fr.stardustenterprises/yanl/badge.svg "maven central badge"
