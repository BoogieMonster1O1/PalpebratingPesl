package io.github.boogiemonster1o1.palpebratingpesl.`object`.singleton

import io.github.boogiemonster1o1.palpebratingpesl.PalpebratingPesl
import io.github.boogiemonster1o1.palpebratingpesl.`object`.PluginContainerObject
import org.spongepowered.api.Platform
import p0nki.pesl.api.`object`.BuiltinMapLikeObject
import p0nki.pesl.api.`object`.FunctionObject
import p0nki.pesl.api.`object`.StringObject

object PlatformObject : BuiltinMapLikeObject("platform") {
	private val platform : Platform = PalpebratingPesl.game.platform

	init {
		this.put("getApi", FunctionObject.of(false) {
			PluginContainerObject(platform.getContainer(Platform.Component.API))
		})
		this.put("getImplementation", FunctionObject.of(false) {
			PluginContainerObject(platform.getContainer(Platform.Component.IMPLEMENTATION))
		})
		this.put("getMinecraft", FunctionObject.of(false) {
			PluginContainerObject(platform.getContainer(Platform.Component.GAME))
		})
		this.put("getMinecraftVersion", FunctionObject.of(false) {
			StringObject(platform.minecraftVersion.name)
		})
	}
}
