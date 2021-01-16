# Commons

This project has been maintained for about two years now by the Starfish Studios.  It's been used in a variety of projects
thanks to the amount of utilities and frameworks, mainly focused on Bukkit, Bungee and JDA but can do the trick in anything.

## Contents

1. [Installation](#installation)
2. []

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

```xml
<dependency>
    <groupId>me.googas.commons</groupId>
    <artifactId>Commons</artifactId>
    <version>%version%</version>
</dependency>
```

## Usage

For more information on how to use the utilities and frameworks head to the [wiki](https://github.com/xChevy/Commons/wiki)