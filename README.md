# Welcome to my simple RPG game

## Goal
The goal was to write a command line based role playing game.

This project is as a recruitment puzzle with following requirements and constraints 

Here are required stories:
* As a player I want to create a character
* As a player I want to explore
* As a player I want to gain experience through fighting
* As a player I want to save and resume a game

Technical constraints on this project were:
* Use Java
* Libraries and Frameworks are only allowed for testing and build pipelines
* Use best in industry agile engineering practices
* Please build for the command line


It works in a command line, but thanks to ports-and-adapters architecture it can extended to work with other presentation layers also

## Additional features implemented
* leveling up
* additional fighting mechanism - defence (reducing damage taken for current fight)
* coloring output
* both random and named enemies
* ASCII art for named enemies
* additional character type - an ally
* multiple realms to choose from
* new realms can be generated using `priv.rdo.rpg.common.generator.SerializationRealmConfigurationGenerator` class
* the project uses ports and adapters architecture, so all configurations and user interactions can be changed by simply implementing provided interfaces 
  * for example: configuration can be switched to something more user-readable than Java serialization (like a JSON file), by implementing `priv.rdo.rpg.ports.outgoing
.RealmConfigurationProvider`. With that it can be also externalized to a user-editable file, so user will be able to add his own realms
* exploration is tracked on a numbered map (meaning map has headers)
* there are some helpful allies on the map
* most of things that could be generified - were

## General comments
* I didn't really have much to do this, so there are still many things to be improved, hence tons of TODOs in the code (for example loading/saving multiple saves)
* once the project gets evaluated I'll refactor this to be more developer-friendly (for example using Spring (DI, AOP), or JSON configuration files)
* test coverage is not nearly sufficient, but this is how it looks if you have only a couple of hours for a project :) (ps spock is awesome!)

## Running
### IDE
just run `main` method in `priv.rdo.rpg.Main` class

JAR
------------------
```
java -jar cli-rpg-1.0.0.jar
```

## Building

Project is based on Maven and can be built using the following command
```
mvn clean package
```

## PS
this game is not easy, but it can be defeated, if you try hard enough!

this is a proof!

![this is a proof!](victory.png)