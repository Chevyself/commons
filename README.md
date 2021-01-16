# Commons

This project has been maintained for about two years now by the Starfish Studios.  It's been used in a variety of projects
thanks to the amount of utilities and frameworks, mainly focused on Bukkit, Bungee and JDA but can do the trick in anything.

## Contents

1. [Installation](#installation)
2. [Usage](#usage)
    * [Builders](#builders)
    * [Cache](#cache)
    * [Events](#events)
    * [Fallback](#fallback)
    * [Gson](#gson)
    * [Math](#math)
## Installation

At the moment, the Starfish Studios does not have a maven repository to provide easy access to its projects. You can download
the jar file from [the releases page](https://github.com/xChevy/Commons/releases) or clone this repository and install it into
your /.m2/ folder:

1. Clone the repository

```
git clone https://github.com/xChevy/Commons.git
```

2. Get inside the working directory

```
cd Commons
```

3. Install using maven

```
mvn clean install
```

4. Add it to your project. You have to change `%version` to the latest version.
``
```xml
<dependency>
    <groupId>me.googas.commons</groupId>
    <artifactId>Commons</artifactId>
    <version>%version%</version>
</dependency>
```

## Usage

### Builders

Create your own builders implementing `Builder`.

* `LogBuilder` to create a message to send to a `java.util.logging.Logger`
* `ToStringBuilder` to create on your objects `#toString()`. The template for IntelliJ is:

```
public java.lang.String toString() {
   return new me.googas.commons.builder.ToStringBuilder(this)
      #foreach($member in $members)
         .append("$member.name", $member.accessor)
      #end
   .build();
}   
```

#### Example:

```java
public class BuilderSample {
    public void test() {
        // Logger creating using our own LoggerFactory
        Logger logger = LoggerFactory.start("Test-Logger");
        LogBuilder builder = new LogBuilder("Starting log...");
        builder.append("\n").append(new Foo("Hello world!", 25));
        builder.send(logger);
    }
    public static class Foo {
        
        private final String string;
        private final int integer;
        
        public Foo(String string, int integer) {
            this.string = string;
            this.integer = integer;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("string", this.string)
                    .append("integer", this.integer)
                    .build();
        }
    }
}
```

### Cache

Cache is a task that contains Catchables until a time has passed and are considered safe to unload. Cache
implements `Runnable` and must registered in a task to check if those objects can be unloaded.

Create your own implementation with the interface `Cache` and make sure that the objects that you want to store
in cache must implement `Catchable`. 

You, of course, can use the implementation `MemoryCache`:

```java
public class MemoryCacheSample {
    public void cache() {
        MemoryCache cache = new MemoryCache();
        // Cache must be registered inside a task (It implements runnable) in which you can make it check
        // if the catchable can be removed in the time you want .
        // For this example, we will use the Scheduler framework.
        TimerScheduler scheduler = new TimerScheduler(new Timer());
        Time time = new Time(1, Unit.SECONDS);
        scheduler.repeat(time, time, cache);
    }
}
```

Implementing `Catchable`:

```java
public class ACatchable implements Catchable {
   @Override
   public void onRemove() {
      System.out.println("This has been unloaded");
   }

   @Override
   public @NonNull
   Time getToRemove() {
      // This object will be removed from the cache it is on in 3 minutes of querying it
      return new Time(3, Unit.MINUTES);
   }
}    
```

### Events

This is a small and straight forward framework that allows you to create events and call listeners using basic
Java Reflection. 

An `Event` must be called by the `ListenerManager` which must have all the listeners you need registered. Check the
method `ListenerManager#registerLisners(Object)`.

A method that may be used as a listener has to be annotated with `Listener` the lower the priority the fastest 
the listener will be called. Also, the parameter of the listener must be only one, and the class of the event
that listens, this means, that the class of the parameter must implement `Event`

[Take me to the wiki](https://github.com/xChevy/Commons/wiki/Events)

### Fallback

Keep track of the errors in your application! This provides you a class to store and handle errors. Crete your own implementation
with the interface `Fallback`. For this class the implementation is way too simple as it does not use a logger just prints 
the stacktrace with `Throwable#printStackTrace()`

#### Example

```java
public class FallbackSamples {
   public static void main(String[] args) {
      SimpleFallback fallback = new SimpleFallback();
      try {
         Integer.parseInt("Obvious error");
      } catch (NumberFormatException e) {
         fallback.process(e);
      }
      System.out.println(fallback.getErrors());
   }
}
```

### Gson

This package contains deserializers for some classes in the framework such as `Time`, `Cuboid` or `Circle`

### Math

Provides `MathUtils` with a few methods for math, and the package `Geometry` that contains a variety of geometric objects
which mainly aims for minecraft usages but maybe you can find another use.

### Scheduler

Its like a `Timer` with steroids. Create tasks like `Countdown`, `Repetitive` or `RunLater`. You can also create your own scheduler
implementing `Scheduler` and tasks implementing `Task`

#### Example

```java
public class SchedulerSamples {
  public static void main(String[] args) {
    Scheduler scheduler = new TimerScheduler(new Timer());
    Time aSecond = new Time(1, Unit.SECONDS);
    scheduler.repeat(aSecond, aSecond, () -> System.out.println("I will print every second!"));
    scheduler.later(aSecond, () -> System.out.println("I will run after a second has passed!"));
    scheduler.countdown(
        new Time(30, Unit.SECONDS),
        (left) -> System.out.printf("I will run for the next %s \n", left.toEffectiveString()),
        () -> System.out.println("I will run when the countdown is finished"));
  }
}
```