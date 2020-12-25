package io.github.boogiemonster1o1.palpebratingpesl.objects

import p0nki.pesl.api.`object`.*
import java.util.*

class UuidObject(val uuid: UUID) : BuiltinMapLikeObject("uuid") {
	init {
		this.put("asString", FunctionObject.of(false) {
			StringObject(this.uuid.toString())
		})
		this.put("getMostSigBits", FunctionObject.of(false) {
			NumberObject(this.uuid.mostSignificantBits.toDouble())
		})
		this.put("getLeastSigBits", FunctionObject.of(false) {
			NumberObject(this.uuid.leastSignificantBits.toDouble())
		})
	}

	override fun equals(other: Any?): Boolean {
		if (other is UuidObject) {
			return other.uuid == this.uuid
		}
		return false
	}

	override fun compareEquals(`object`: PESLObject): Boolean {
		return this == `object`
	}

	override fun hashCode(): Int {
		return uuid.hashCode()
	}
}
