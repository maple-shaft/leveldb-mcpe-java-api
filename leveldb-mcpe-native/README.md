# Native Build for Mojang port of LevelDB for Minecraft Bedrock Edition

The following project uses the Gradle `cpp-library` plugin to build the C++ native interface and JNI interface for the Java MCAPI library.  It is a dependent project of the Java MCAPI library and will output a library that must be on the system path or 'java.library.path` runtime parameter of the JVM.

### Build Environment

You will require Gradle 6+ as well as an appropriate C++ toolchain installed and configured to your build environment.  The build should support any GNU compatible toolchain on a POSIX operating system.  It should also be able to support Windows OS with Visual Studio and to a lesser extent Cygwin.

### Dependencies

You will need:

 - Gradle 6+
 - JDK8+
 - GNU C++ compiler and linker (supporting C++11)
 - LevelDB (Mojang Variant) built and installed with an environment variable `LEVLEDB_HOME` set to the directory where LevelDB builds exist in `./out-shared/` and appropriate C header files can be found (`./include/`)
 
You will be able to find the source for the Mojant variant LevelDB here: [Mojang LevelDB](https://github.com/Mojang/leveldb-mcpe)

### Considerations for Cygwin

The build can be successfully executed in Windows via Cygwin, however a few considerations may need to be considered.  We will work to employ macros to better resolve some of the issues.

 1. The `win32/jni_md.h` of the JDK installation may need to be tweaked to resolve the absence of `__int64` as a type when compiling in Cygwin.  For more information [see here](https://graphics-muse.org/wp/?page_id=147) .
 2. Some casts in `jnimcpe.cpp` may not compile correctly in Cygwin.  Again, this can change with some use of macros which can be added later.

