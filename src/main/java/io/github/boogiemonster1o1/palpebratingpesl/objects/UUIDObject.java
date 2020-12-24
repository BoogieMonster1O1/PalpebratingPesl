package io.github.boogiemonster1o1.palpebratingpesl.objects;

import java.util.UUID;

import javax.annotation.Nonnull;

import io.github.boogiemonster1o1.palpebratingpesl.util.FunctionObjects;
import p0nki.pesl.api.object.BuiltinMapLikeObject;
import p0nki.pesl.api.object.NumberObject;
import p0nki.pesl.api.object.StringObject;

public class UUIDObject extends BuiltinMapLikeObject {
	private final UUID uuid;

	public UUIDObject(UUID uuid) {
		super("uuid");
		this.uuid = uuid;
		this.put("getMostSigBits", FunctionObjects.of(this, (args) -> new NumberObject(this.uuid.getMostSignificantBits())));
		this.put("getLeastSigBits", FunctionObjects.of(this, (args) -> new NumberObject(this.uuid.getLeastSignificantBits())));
		this.put("toString", FunctionObjects.of(this, (args) -> new StringObject(this.uuid.toString())));
	}

	@Nonnull
	@Override
	public String castToString() {
		return this.uuid.toString();
	}
}
