package io.github.boogiemonster1o1.palpebratingpesl.objects;

import com.flowpowered.math.vector.Vector3d;
import io.github.boogiemonster1o1.palpebratingpesl.util.FunctionObjects;
import p0nki.pesl.api.object.BuiltinMapLikeObject;
import p0nki.pesl.api.object.NumberObject;

public class Vector3dObject extends BuiltinMapLikeObject {
	private final Vector3d vector3d;

	public Vector3dObject(Vector3d vector3d) {
		super("vector3d");
		this.vector3d = vector3d;
		this.put("getX", FunctionObjects.of(this, (args) -> new NumberObject(this.vector3d.getX())));
		this.put("getY", FunctionObjects.of(this, (args) -> new NumberObject(this.vector3d.getY())));
		this.put("getZ", FunctionObjects.of(this, (args) -> new NumberObject(this.vector3d.getZ())));
		this.put("getFloorX", FunctionObjects.of(this, (args) -> new NumberObject(this.vector3d.getX())));
		this.put("getFloorY", FunctionObjects.of(this, (args) -> new NumberObject(this.vector3d.getY())));
		this.put("getFloorZ", FunctionObjects.of(this, (args) -> new NumberObject(this.vector3d.getZ())));
	}

	public Vector3d getVector3d() {
		return this.vector3d;
	}
}
