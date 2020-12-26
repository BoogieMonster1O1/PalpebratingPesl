package io.github.boogiemonster1o1.palpebratingpesl;

import java.nio.file.Path;

import com.google.inject.Inject;
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

@Plugin(id = "palpebratingpesl")
public class PalpebratingPesl {
	@Inject @ConfigDir(sharedRoot = false) public static Path configDir;
	@Inject public static Logger logger;
	@Inject public static Game game;
	@Inject public static PluginContainer pluginContainer;
	@Inject @AssetId("scripts/example.pesl") public static Asset examplePeslAsset;
	@Inject public static PluginManager pluginManager;
	@Inject public static EventManager eventManager;
	public static Config config = null;

	@Listener
	public void onConstruction(GameConstructionEvent event) {
		eventManager.registerListeners(this, Handler.INSTANCE);
	}
}
