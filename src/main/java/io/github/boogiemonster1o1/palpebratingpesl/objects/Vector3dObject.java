package io.github.boogiemonster1o1.palpebratingpesl.objects;

import javax.annotation.Nonnull;

import com.flowpowered.math.vector.Vector3d;
import io.github.boogiemonster1o1.palpebratingpesl.util.FunctionObjects;
import p0nki.pesl.api.object.BuiltinMapLikeObject;
import p0nki.pesl.api.object.NumberObject;
import p0nki.pesl.api.object.PESLObject;

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

	@Nonnull
	@Override
	public String castToString() {
		return this.vector3d.toString();
	}

	@Override
	public boolean compareEquals(@Nonnull PESLObject o) {
		if (this == o) return true;
		if (!(o instanceof Vector3dObject)) return false;
		Vector3dObject that = (Vector3dObject) o;
		return this.vector3d == that.vector3d;
	}
}
