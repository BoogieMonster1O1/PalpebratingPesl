package io.github.boogiemonster1o1.palpebratingpesl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import io.github.boogiemonster1o1.palpebratingpesl.objects.TextObject;
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
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.text.Text;
import p0nki.pesl.api.PESLContext;
import p0nki.pesl.api.PESLEvalException;
import p0nki.pesl.api.builtins.PESLBuiltins;
import p0nki.pesl.api.object.ArrayObject;
import p0nki.pesl.api.object.BuiltinMapLikeObject;
import p0nki.pesl.api.object.FunctionObject;
import p0nki.pesl.api.object.PESLObject;
import p0nki.pesl.api.object.StringObject;
import p0nki.pesl.api.parse.ASTNode;
import p0nki.pesl.api.parse.PESLParseException;
import p0nki.pesl.api.parse.PESLParser;
import p0nki.pesl.api.parse.PESLValidateException;
import p0nki.pesl.api.token.PESLTokenList;
import p0nki.pesl.api.token.PESLTokenizeException;
import p0nki.pesl.api.token.PESLTokenizer;

@Plugin(id = "palpebratingpesl")
public class PalpebratingPesl {
	private static final PESLObject SPONGE_OBJECT = BuiltinMapLikeObject.builtinBuilder()
			.put("getGame", FunctionObject.of(false, args -> GameObject.INSTANCE))
			.put("getServer", FunctionObject.of(false, args -> ServerObject.INSTANCE))
			.put("getPluginManager", FunctionObject.of(false, args -> PluginManagerObject.INSTANCE))
			.put("getPlatform", FunctionObject.of(false, args -> PlatformObject.INSTANCE));
	public static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().setLenient().create();
	public static final PESLTokenizer TOKENIZER = new PESLTokenizer();
	public static final PESLParser PARSER = new PESLParser();
	private Config config = null;

	private static PalpebratingPesl instance;

	@Inject @ConfigDir(sharedRoot = false) private Path configDir;
	@Inject private Logger logger;
	@Inject private Game game;
	@Inject private PluginContainer pluginContainer;
	@Inject @AssetId("scripts/example.pesl") private Asset examplePeslAsset;
	@Inject private PluginManager pluginManager;
	@Inject @DefaultConfig(sharedRoot = false) private Path configPath;

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

		try {
			if (!Files.exists(this.configPath)) {
				Files.createFile(this.configPath);
			}

			this.config = GSON.fromJson(Files.newBufferedReader(this.configPath), Config.class);
		} catch (IOException e) {
			this.logger.error("Error reading file", e);
		} catch (JsonSyntaxException e) {
			this.logger.error(e.getMessage(), e);
		}
	}

	@Listener
	public void onAboutToStartServer(GameAboutToStartServerEvent event) {
		Fields.init();
		ServerObject.init();
	}

	@Listener
	public void onStartingServer(GameStartingServerEvent event) {
		for (String name : this.config.getInitScripts()) {
			this.execute(name);
		}
	}

	@Listener
	public void onInitialization(GameInitializationEvent event) {
		Sponge.getCommandManager().register(this.pluginContainer,
				CommandSpec.builder()
						.permission("palpebratingpesl.evalpesl")
						.arguments(GenericArguments.string(Text.of("name")), GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("remaining"))))
						.executor((src, args) -> {
							long start = System.nanoTime();
							this.execute(args.<String>getOne("name").orElseThrow(AssertionError::new), args.<String>getOne("remaining").orElse("").split(" "));
							long end = System.nanoTime() - start;
							src.sendMessage(Text.builder("Finished executing script " + args.<String>getOne("name").orElseThrow(AssertionError::new) + " in " + (end / 1_000_000D) + " ms").toText());
							return CommandResult.success();
						})
						.build(),
				"evalpesl");
	}

	private void execute(String name, String... remaining) throws RuntimeException {
		Path filePath = this.configDir.resolve("scripts").resolve(name + ".pesl");
		PESLTokenList tokens;
		try {
			tokens = TOKENIZER.tokenize(new String(Files.readAllBytes(filePath)));
		} catch (PESLTokenizeException e) {
			int index = e.getIndex();
			StringBuilder builder = new StringBuilder();
			builder.append("   ");
			for (int i = 0; i < index; i++) {
				builder.append(" ");
			}
			builder.append("^");
			this.logger.error(builder.toString());
			this.logger.error("Tokenize error\n" + e.getMessage());
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

		PESLContext ctx = new PESLContext();
		ctx.let("Sponge", SPONGE_OBJECT);
		ctx.let("textOf", FunctionObject.of(false, args -> {
			PESLEvalException.validArgumentListLength(args, 1);
			return new TextObject(args.get(0).castToString());
		}));
		ctx.let("println", PESLBuiltins.PRINTLN);
		ctx.let("args", new ArrayObject(Arrays.stream(remaining).map(StringObject::new).collect(Collectors.toList())));

		PESLParser astCreator = new PESLParser();

		try {
			PESLObject lastObject;
			while (tokens.hasAny()) {
				ASTNode node = astCreator.parseExpression(tokens);
				node.validate();
				lastObject = node.evaluate(ctx);
				this.logger.debug("Evaluated PESL Object expression -> " + lastObject.castToString());
			}
		} catch (PESLParseException e) {
			StringBuilder builder = new StringBuilder();
			builder.append("  ");
			for (int i = 0; i < e.getToken().getStart(); i++) {
				builder.append(" ");
			}
			for (int i = 0; i < e.getToken().getEnd() - e.getToken().getStart(); i++) {
				builder.append("^");
			}
			builder.append("\n");
			builder.append(" ").append(e.getToken().toString()).append("\n");
			builder.append("Parse error\n").append(e.getMessage()).append("\n");
			this.logger.error(builder.toString());
			throw new RuntimeException(e.getMessage(), e);
		} catch (PESLEvalException e) {
			this.logger.error("Eval error");
			throw new RuntimeException("Encountered an unknown exception: " + e.getMessage(), e);
		} catch (PESLValidateException e) {
			this.logger.error("Validate error " + e.getMessage());
			this.logger.error(String.valueOf(e.getNode()));
			throw new RuntimeException(e);
		}
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
}
