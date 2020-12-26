package io.github.boogiemonster1o1.palpebratingpesl.objects.singleton

import io.github.boogiemonster1o1.palpebratingpesl.PalpebratingPesl
import io.github.boogiemonster1o1.palpebratingpesl.objects.MessageChannelObject
import org.spongepowered.api.Game
import p0nki.pesl.api.`object`.BuiltinMapLikeObject
import p0nki.pesl.api.`object`.FunctionObject
import p0nki.pesl.api.`object`.StringObject

object GameObject : BuiltinMapLikeObject("game") {
	private val game : Game = PalpebratingPesl.game

	init {
		this.put("getServer", FunctionObject.of(false) {
			ServerObject
		})
		this.put("getGameDirectory", FunctionObject.of(false) {
			StringObject(game.gameDirectory.toAbsolutePath().toString())
		})
		this.put("getSavesDirectory", FunctionObject.of(false) {
			StringObject(game.savesDirectory.toAbsolutePath().toString())
		})
		this.put("getPluginManager", FunctionObject.of(false) {
			PluginManagerObject
		})
		this.put("getPlatform", FunctionObject.of(false) {
			PlatformObject
		})
	}
}
