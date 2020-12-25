package io.github.boogiemonster1o1.palpebratingpesl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import io.github.boogiemonster1o1.palpebratingpesl.objects.GetUtilsKt;
import io.github.boogiemonster1o1.palpebratingpesl.objects.singleton.GameObject;
import io.github.boogiemonster1o1.palpebratingpesl.objects.singleton.PlatformObject;
import io.github.boogiemonster1o1.palpebratingpesl.objects.singleton.PluginManagerObject;
import io.github.boogiemonster1o1.palpebratingpesl.objects.singleton.ServerObject;
import io.github.boogiemonster1o1.palpebratingpesl.util.Fields;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.asset.AssetId;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import p0nki.pesl.api.PESLContext;
import p0nki.pesl.api.PESLEvalException;
import p0nki.pesl.api.object.ArrayObject;
import p0nki.pesl.api.object.BuiltinMapLikeObject;
import p0nki.pesl.api.object.FunctionObject;
import p0nki.pesl.api.object.PESLObject;
import p0nki.pesl.api.object.StringObject;
import p0nki.pesl.api.parse.PESLParseException;
import p0nki.pesl.api.parse.PESLParser;
import p0nki.pesl.api.token.PESLTokenizeException;
import p0nki.pesl.api.token.PESLTokenizer;

@Plugin(id = "palpebratingpesl")
public class PalpebratingPesl {
	private static final PESLObject SPONGE_OBJECT = BuiltinMapLikeObject.builtinBuilder()
			.put("getGame", FunctionObject.of(false, args -> GameObject.INSTANCE))
			.put("getServer", FunctionObject.of(false, args -> ServerObject.INSTANCE))
			.put("getPluginManager", FunctionObject.of(false, args -> PluginManagerObject.INSTANCE))
			.put("getPlatform", FunctionObject.of(false, args -> PlatformObject.INSTANCE));
	public static final PESLTokenizer TOKENIZER = new PESLTokenizer();
	public static final PESLParser PARSER = new PESLParser();
	public static final PESLContext CONTEXT;

	static {
		PESLContext e = new PESLContext();
		e.let("Sponge", SPONGE_OBJECT);
		CONTEXT = e;
	}

	private static PalpebratingPesl instance;

	@Inject @ConfigDir(sharedRoot = false) private Path configDir;
	@Inject private Logger logger;
	@Inject private Game game;
	@Inject private PluginContainer pluginContainer;
	@Inject @AssetId("scripts/example.pesl") private Asset examplePeslAsset;

	@Listener
	public void onConstruction(GameConstructionEvent event) {
		instance = this;
	}

	@Listener
	public void onPreInitialization(GamePreInitializationEvent event) {
		try {
			this.examplePeslAsset.copyToDirectory(this.configDir.resolve("scripts"), true);
		} catch (IOException e) {
			this.logger.error("Error copying example script", e);
		}
	}

	@Listener
	public void onAboutToStartServer(GameAboutToStartServerEvent event) {
		Fields.init();
		ServerObject.init();
	}

	@Listener
	public void onInitialization(GameInitializationEvent event) {
		Sponge.getCommandManager().register(this.pluginContainer,
				CommandSpec.builder()
						.permission("palpebratingpesl.evalpesl")
						.arguments(GenericArguments.string(Text.of("name")), GenericArguments.remainingJoinedStrings(Text.of("remaining")))
						.executor((src, args) -> {
							String name = args.<String>getOne("name").orElseThrow(AssertionError::new);
							long start = System.nanoTime();
							Path filePath = this.configDir.resolve("scripts").resolve(name + ".pesl");

							try {
								PESLContext argContext = CONTEXT.push();
								argContext.let("Args", new ArrayObject(Arrays.stream(args.<String>getOne("remaining").orElse("").split(" ")).map(StringObject::new).collect(Collectors.toList())));
								PARSER.parseExpression(TOKENIZER.tokenize(new String(Files.readAllBytes(filePath)))).evaluate(CONTEXT);
							} catch (PESLParseException e) {
								throw new RuntimeException(e.getMessage(), e);
							} catch (PESLEvalException e) {
								throw new RuntimeException("Encountered an unknown exception: " + e.getMessage(), e);
							} catch (PESLTokenizeException e) {
								throw new RuntimeException("Invalid PESL code at index " + e.getIndex(), e);
							} catch (IOException e) {
								throw new UncheckedIOException(e);
							}

							long end = System.nanoTime() - start;
							src.sendMessage(Text.builder("Finished executing script " + name + " in " + (end / 1_000_000D) + " ms").onHover(TextActions.showText(Text.of(filePath.toAbsolutePath().toString()))).toText());
							return CommandResult.success();
						})
						.build(),
				"evalpesl");
	}

//	@Listener
//	public void onReload(GameReloadEvent event) {
//	}

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

	public static PalpebratingPesl getInstance() {
		return instance;
	}
}
