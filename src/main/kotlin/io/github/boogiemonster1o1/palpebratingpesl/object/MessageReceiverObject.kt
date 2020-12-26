package io.github.boogiemonster1o1.palpebratingpesl.`object`

import org.spongepowered.api.text.Text
import org.spongepowered.api.text.channel.MessageReceiver
import p0nki.pesl.api.`object`.BuiltinMapLikeObject
import p0nki.pesl.api.`object`.FunctionObject
import p0nki.pesl.api.`object`.UndefinedObject

abstract class MessageReceiverObject(type: String, messageReceiver: MessageReceiver) : BuiltinMapLikeObject(type) {
	init {
		this.put("sendMessage", FunctionObject.of(false) {
			val obj = it[0]
			val text: Text = if (obj is TextObject) {
				obj.toText()
			} else {
				Text.of(obj.castToString())
			}
			if (it.size == 1) {
				messageReceiver.sendMessage(text)
			} else if (it.size == 2) {
				messageReceiver.sendMessage(text)
			}
			UndefinedObject.INSTANCE
		})
		this.put("sendMessages", FunctionObject.of(false) {
			for (obj in it) {
				val text: Text = if (obj is TextObject) {
					obj.toText()
				} else {
					Text.of(obj.castToString())
				}
				if (it.size == 1) {
					messageReceiver.sendMessage(text)
				} else if (it.size == 2) {
					messageReceiver.sendMessage(text)
				}
			}
			UndefinedObject.INSTANCE
		})
		this.put("getMessageChannel", FunctionObject.of(false) {
			MessageChannelObject { messageReceiver.messageChannel }
		})
	}
}
