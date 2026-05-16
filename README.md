# Minecraft Debug Renderer
Allows servers to render arbitrarily placed and sized debug shapes on the client.

![showcase](.github/showcase.png)

# Usage
Any platform which can send plugin messages is supported. There is a library for Minestom and Fabric, 
which is available on [Jitpack](https://jitpack.io/#playgamesgo/PlayMcDebugRenderer).

```groovy
repositories {
    // ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    // Minestom
    implementation 'com.github.playgamesgo.PlayMcDebugRenderer:minestom:$LATEST_COMMIT_HASH'
    
    // Fabric
    implementation 'com.github.playgamesgo.PlayMcDebugRenderer:fabric:$LATEST_COMMIT_HASH'
}
```

## Checking if enabled
The client will send a plugin message with the id `debug:hello` when joining a server if the mod is present.
The message will contain a single integer representing the current version of the mod.

## Usage
Check [Minestom Demo Server](https://github.com/playgamesgo/PlayMcDebugRenderer/blob/master/minestom/src/test/java/me/playgamesgo/debug/demo/DemoServer.java) or [Fabric Demo Server](https://github.com/playgamesgo/PlayMcDebugRenderer/blob/master/fabric/src/test/java/me/playgamesgo/debug/demo/DemoServer.java) for examples.

## Credit
Original author of a library - [mworzala](https://github.com/mworzala/mc_debug_renderer)

# License
This project is licensed under the [MIT License](./LICENSE).
