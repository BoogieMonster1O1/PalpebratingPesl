package io.github.boogiemonster1o1.palpebratingpesl.objects

import org.spongepowered.api.text.Text
import org.spongepowered.api.text.channel.MessageChannel
import p0nki.pesl.api.PESLEvalException
import p0nki.pesl.api.`object`.BuiltinMapLikeObject
import p0nki.pesl.api.`object`.FunctionObject
import p0nki.pesl.api.`object`.PESLObject
import p0nki.pesl.api.`object`.UndefinedObject
import java.util.function.Supplier

class MessageChannelObject(channel : Supplier<MessageChannel>) : BuiltinMapLikeObject("message_channel") {
	init {
		this.put("send", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1, 2)
			val obj : PESLObject = it[0]
			val text : Text = if (obj is TextObject) {
				obj.toText()
			} else {
				Text.of(obj.castToString())
			}
			if (it.size == 1) {
				channel.get().send(text)
			} else if (it.size == 2) {
				channel.get().send(text, getChatType(it[1].castToString()))
			}
			UndefinedObject.INSTANCE
		})
	}
}
