package io.github.boogiemonster1o1.palpebratingpesl.objects;

import io.github.boogiemonster1o1.palpebratingpesl.util.FunctionObjects;
import org.spongepowered.api.entity.living.player.Player;
import p0nki.pesl.api.PESLEvalException;
import p0nki.pesl.api.object.BooleanObject;
import p0nki.pesl.api.object.BuiltinMapLikeObject;
import p0nki.pesl.api.object.StringObject;
import p0nki.pesl.api.object.UndefinedObject;

public class PlayerObject extends BuiltinMapLikeObject  {
	private Player player;

	public PlayerObject(Player player) {
		super("player");
		this.player = player;
		this.put("kick", FunctionObjects.of(this, (args) -> {
			PESLEvalException.validArgumentListLength(args, 0, 1);
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			if (args.size() == 0) {
				this.player.kick();
				this.player = null;
			} else {
				this.player.kick(((TextObject) args.get(0)).toText());
			}
			return UndefinedObject.INSTANCE;
		}));
		this.put("hasPlayedBefore", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new BooleanObject(this.player.hasPlayedBefore());
		}));
		this.put("isChatColorsEnabled", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new BooleanObject(this.player.isChatColorsEnabled());
		}));
		this.put("isViewingInventory", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new BooleanObject(this.player.isViewingInventory());
		}));
		this.put("isOnGround", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new BooleanObject(this.player.isOnGround());
		}));
		this.put("getName", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new StringObject(this.player.getName());
		}));
		this.put("getPosition", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new Vector3dObject(this.player.getPosition());
		}));
		this.put("getVelocity", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new Vector3dObject(this.player.getVelocity());
		}));
		this.put("getRotation", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new Vector3dObject(this.player.getRotation());
		}));
		this.put("getScale", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new Vector3dObject(this.player.getScale());
		}));
		this.put("getHeadRotation", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new Vector3dObject(this.player.getHeadRotation());
		}));
		this.put("closeInventory", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new BooleanObject(this.player.closeInventory());
		}));
		this.put("closeInventory", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new BooleanObject(this.player.closeInventory());
		}));
		this.put("getUniqueId", FunctionObjects.of(this, (args) -> {
			if (this.player == null) {
				throw new PESLEvalException("A Player is not online!");
			}
			return new UUIDObject(this.player.getUniqueId());
		}));
	}

	public Player getPlayer() {
		return this.player;
	}
}
