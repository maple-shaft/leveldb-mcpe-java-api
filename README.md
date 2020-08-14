# leveldb-mcpe-java-api

A JNI based integration of the Mojang LevelDB database, in a friendly to use Java API.  The goal of the project is to provide a functional Java API that can be used to modify Minecraft Bedrock world files.  The potential applications of this would be to program applications that can search, analyze and modify Bedrock worlds.  The primary motivation driving the project is that I wanted to create tools to better enable complex redstone builds in Bedrock.

### Sub Projects

 - LevelDB MCPE Native: is a thin C++ application that provides a direct interface to the Mojang LevelDB variant software and provides a JNI interface for Java apps.
 - MCAPI: is a generic Minecraft API that can be expanded upon for many different uses.  It utilizes the Native project to communicate with the LevelDB database files.
  - DAssembler: A prototype assembly language that is intended to compile into machine code for 
