package io.github.boogiemonster1o1.palpebratingpesl.`object`

import p0nki.pesl.api.`object`.BuiltinMapLikeObject
import p0nki.pesl.api.`object`.FunctionObject
import p0nki.pesl.api.`object`.StringObject

class StringBuilderObject : BuiltinMapLikeObject("string_builder") {
	private val stringBuilder = StringBuilder()

	init {
		this.put("append", FunctionObject.of(false) {
			for (arg in it) {
				stringBuilder.append(arg.castToString())
			}
			this
		})
		this.put("toString", FunctionObject.of(false) {
			StringObject(stringBuilder.toString())
		})
	}
}
