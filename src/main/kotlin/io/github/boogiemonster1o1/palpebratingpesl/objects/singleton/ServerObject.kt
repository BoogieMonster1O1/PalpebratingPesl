package io.github.boogiemonster1o1.palpebratingpesl.objects.singleton

import io.github.boogiemonster1o1.palpebratingpesl.PalpebratingPesl
import io.github.boogiemonster1o1.palpebratingpesl.objects.MessageChannelObject
import io.github.boogiemonster1o1.palpebratingpesl.objects.TextObject
import io.github.boogiemonster1o1.palpebratingpesl.objects.asText
import org.spongepowered.api.Server
import p0nki.pesl.api.PESLEvalException
import p0nki.pesl.api.`object`.*

object ServerObject : BuiltinMapLikeObject("server") {
	private val server : Server = PalpebratingPesl.getInstance().server
	private val broadcastChannel : MessageChannelObject = MessageChannelObject { server.broadcastChannel }

	init {
		this.put("getMaxPlayers", FunctionObject.of(false) {
			NumberObject(server.maxPlayers.toDouble())
		})
		this.put("getDefaultWorldName", FunctionObject.of(false) {
			StringObject(server.defaultWorldName)
		})
		this.put("getRunningTimeTicks", FunctionObject.of(false) {
			NumberObject(server.runningTimeTicks.toDouble())
		})
		this.put("getTicksPerSecond", FunctionObject.of(false) {
			NumberObject(server.ticksPerSecond)
		})
		this.put("hasWhitelist", FunctionObject.of(false) {
			BooleanObject(server.hasWhitelist())
		})
		this.put("setHasWhitelist", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			server.setHasWhitelist(it[0].asBoolean().value)
			UndefinedObject.INSTANCE
		})
		this.put("getOnlineMode", FunctionObject.of(false) {
			BooleanObject(server.onlineMode)
		})
		this.put("getMotd", FunctionObject.of(false) {
			TextObject(server.motd)
		})
		this.put("shutdown", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 0, 1)
			if (it.size == 0) {
				server.shutdown()
			} else {
				server.shutdown(asText(it[0]).toText())
			}
			UndefinedObject.INSTANCE
		})
		this.put("getPlayerIdleTimeout", FunctionObject.of(false) {
			NumberObject(server.playerIdleTimeout.toDouble())
		})
		this.put("setPlayerIdleTimeout", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			server.playerIdleTimeout = it[0].asNumber().value.toInt()
			UndefinedObject.INSTANCE
		})
		this.put("getBroadcastChannel", FunctionObject.of(false) {
			broadcastChannel
		})
	}

	@JvmStatic
	fun init() {
	}
}
