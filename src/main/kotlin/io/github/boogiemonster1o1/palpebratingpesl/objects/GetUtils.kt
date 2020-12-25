package io.github.boogiemonster1o1.palpebratingpesl.objects

import io.github.boogiemonster1o1.palpebratingpesl.util.Fields
import org.spongepowered.api.text.chat.ChatType
import org.spongepowered.api.text.format.TextColor
import p0nki.pesl.api.PESLEvalException
import p0nki.pesl.api.`object`.PESLObject
import java.util.*
import kotlin.jvm.Throws

@Throws(PESLEvalException::class)
fun getChatType(name : String) : ChatType = Optional.ofNullable(Fields.CHAT_TYPES[name.capitalize()]).orElseThrow { PESLEvalException("Unknown chat type $name") }

@Throws(PESLEvalException::class)
fun asText(peslObject: PESLObject) : TextObject {
	if (peslObject is TextObject) {
		return peslObject
	}
	throw PESLEvalException("Can not cast ${peslObject.type} to text")
}

@Throws(PESLEvalException::class)
fun getColor(name: String) : TextColor = Optional.ofNullable(Fields.COLORS[name.capitalize()]).orElseThrow { PESLEvalException("Unknown color $name") }
