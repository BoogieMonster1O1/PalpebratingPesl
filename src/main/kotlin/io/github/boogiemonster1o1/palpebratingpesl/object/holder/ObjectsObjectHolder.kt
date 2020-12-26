package io.github.boogiemonster1o1.palpebratingpesl.`object`.holder

import p0nki.pesl.api.PESLEvalException
import p0nki.pesl.api.`object`.*

object ObjectsObjectHolder {
	val `object`: BuiltinMapLikeObject = BuiltinMapLikeObject.builtinBuilder()
		.put("nonNull", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			val obj = it[0]
			BooleanObject(!(obj == NullObject.INSTANCE || obj == UndefinedObject.INSTANCE))
		})
		.put("requireNonNull", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			val obj = it[0]
			if (obj == NullObject.INSTANCE || obj == UndefinedObject.INSTANCE) {
				throw PESLEvalException("${obj.stringify()} was null")
			}
			obj
		})
}
