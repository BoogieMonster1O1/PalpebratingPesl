package io.github.boogiemonster1o1.palpebratingpesl;

import java.util.Optional;
import java.util.function.Supplier;

import io.github.boogiemonster1o1.palpebratingpesl.objects.TextBuilderObject;
import io.github.boogiemonster1o1.palpebratingpesl.util.Fields;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.chat.ChatTypes;
import p0nki.pesl.api.PESLContext;
import p0nki.pesl.api.PESLEvalException;
import p0nki.pesl.api.object.BuiltinMapLikeObject;
import p0nki.pesl.api.object.FunctionObject;
import p0nki.pesl.api.object.NumberObject;
import p0nki.pesl.api.object.PESLObject;
import p0nki.pesl.api.object.StringObject;
import p0nki.pesl.api.object.UndefinedObject;

public class MorePeslObjects {
	private static final Supplier<Server> G_S = () -> PalpebratingPesl.getInstance().getServer();

	/**
	 * {@link MessageChannel}
	 */
	public static final PESLObject BROADCAST_CHANNEL = BuiltinMapLikeObject.builtinBuilder()
			.put("send", FunctionObject.of(true, (args) -> {
				ChatType chatType;
				if (args.size() > 1) {
					StringObject next = args.get(1).asString();
					chatType = Optional.ofNullable(Fields.CHAT_TYPES.get(next.getValue())).orElseThrow(() -> new PESLEvalException("Unknown chat type: " + next.getValue()));
				} else {
					chatType = ChatTypes.CHAT;
				}
				PESLObject e = args.get(0);
				if (e instanceof TextBuilderObject) {
					G_S.get().getBroadcastChannel().send(((TextBuilderObject) e).toText(), chatType);
				}
				return UndefinedObject.INSTANCE;
			}));

	/**
	 * {@link Server}
	 */
	public static final PESLObject SERVER = BuiltinMapLikeObject.builtinBuilder()
			.put("onlinePlayerCount", FunctionObject.of(true, (args) -> new NumberObject(G_S.get().getOnlinePlayers().size())))
			.put("maxPlayers", FunctionObject.of(true, (args) -> new NumberObject(G_S.get().getMaxPlayers())))
			.put("playerIdleTimeout", FunctionObject.of(true, (args) -> new NumberObject(G_S.get().getPlayerIdleTimeout())))
			.put("broadcastChannel", BROADCAST_CHANNEL);

	/**
	 * {@link Game}
	 */
	public static final PESLObject GAME = BuiltinMapLikeObject.builtinBuilder()
			.put("gameDirectory", FunctionObject.of(true, (args) -> new StringObject(PalpebratingPesl.getInstance().getGame().getGameDirectory().toAbsolutePath().toString())))
			.put("server", SERVER);

	public static final PESLContext CONTEXT = new PESLContext();

	static {
		CONTEXT.let("game", GAME);
		CONTEXT.let("textOf", FunctionObject.of(true, (args) -> new TextBuilderObject(args.get(0).asString().getValue())));
		CONTEXT.let("server", SERVER);
	}
}
