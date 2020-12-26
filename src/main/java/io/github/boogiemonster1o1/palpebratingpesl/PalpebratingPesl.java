package io.github.boogiemonster1o1.palpebratingpesl;

import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import io.github.boogiemonster1o1.palpebratingpesl.objects.singleton.GameObject;
import io.github.boogiemonster1o1.palpebratingpesl.objects.singleton.PlatformObject;
import io.github.boogiemonster1o1.palpebratingpesl.objects.singleton.PluginManagerObject;
import io.github.boogiemonster1o1.palpebratingpesl.objects.singleton.ServerObject;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.asset.AssetId;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;
import p0nki.pesl.api.object.BuiltinMapLikeObject;
import p0nki.pesl.api.object.FunctionObject;
import p0nki.pesl.api.object.PESLObject;
import p0nki.pesl.api.token.PESLTokenizer;

@Plugin(id = "palpebratingpesl")
public class PalpebratingPesl {
	public static final PESLObject SPONGE_OBJECT = BuiltinMapLikeObject.builtinBuilder()
			.put("getGame", FunctionObject.of(false, args -> GameObject.INSTANCE))
			.put("getServer", FunctionObject.of(false, args -> ServerObject.INSTANCE))
			.put("getPluginManager", FunctionObject.of(false, args -> PluginManagerObject.INSTANCE))
			.put("getPlatform", FunctionObject.of(false, args -> PlatformObject.INSTANCE));
	public static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().setLenient().create();
	public static final PESLTokenizer TOKENIZER = new PESLTokenizer();
	private Config config = null;

	private static PalpebratingPesl instance;

	@Inject @ConfigDir(sharedRoot = false) private Path configDir;
	@Inject private Logger logger;
	@Inject private Game game;
	@Inject private PluginContainer pluginContainer;
	@Inject @AssetId("scripts/example.pesl") private Asset examplePeslAsset;
	@Inject private PluginManager pluginManager;
	@Inject private EventManager eventManager;

	@Listener
	public void onConstruction(GameConstructionEvent event) {
		instance = this;
		this.eventManager.registerListeners(this, Handler.INSTANCE);
	}

	public Path getConfigDir() {
		return this.configDir;
	}

	public Logger getLogger() {
		return this.logger;
	}

	public Game getGame() {
		return this.game;
	}

	public PluginContainer getPluginContainer() {
		return this.pluginContainer;
	}

	public Server getServer() {
		return this.getGame().getServer();
	}

	public PluginManager getPluginManager() {
		return this.pluginManager;
	}

	public static PalpebratingPesl getInstance() {
		return instance;
	}

	public Asset getExamplePeslAsset() {
		return this.examplePeslAsset;
	}

	public Config getConfig() {
		return this.config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
}
