package io.github.boogiemonster1o1.palpebratingpesl.objects

import com.flowpowered.math.vector.Vector3d
import p0nki.pesl.api.`object`.BuiltinMapLikeObject
import p0nki.pesl.api.`object`.FunctionObject
import p0nki.pesl.api.`object`.NumberObject
import p0nki.pesl.api.`object`.PESLObject
import javax.annotation.Nonnull

class Vector3dObject(private val vector3d: Vector3d) : BuiltinMapLikeObject("vector3d") {
	@Nonnull
	override fun castToString(): String {
		return vector3d.toString()
	}

	override fun equals(o: Any?): Boolean {
		if (this === o) return true
		if (o !is Vector3dObject) return false
		return vector3d == o.vector3d
	}

	override fun compareEquals(@Nonnull o: PESLObject): Boolean {
		return this == o
	}

	override fun hashCode(): Int {
		return vector3d.hashCode()
	}

	init {
		this.put("getX", FunctionObject.of(false) {
			NumberObject(vector3d.x)
		})
		this.put("getY", FunctionObject.of(false) {
			NumberObject(vector3d.y)
		})
		this.put("getZ", FunctionObject.of(false) {
			NumberObject(vector3d.z)
		})
		this.put("getFloorX", FunctionObject.of(false) {
			NumberObject(vector3d.x)
		})
		this.put("getFloorY", FunctionObject.of(false) {
			NumberObject(vector3d.y)
		})
		this.put("getFloorZ", FunctionObject.of(false) {
			NumberObject(vector3d.z)
		})
	}
}
