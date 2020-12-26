package io.github.boogiemonster1o1.palpebratingpesl.`object`

import org.spongepowered.api.command.CommandSource
import p0nki.pesl.api.`object`.FunctionObject
import p0nki.pesl.api.`object`.StringObject

class CommandSourceObject(source : CommandSource) : MessageReceiverObject("command_source", source) {
	init {
		this.put("getName", FunctionObject.of(false) {
			StringObject(source.name)
		})
	}
}
