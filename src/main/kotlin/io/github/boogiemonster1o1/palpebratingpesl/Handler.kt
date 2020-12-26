package io.github.boogiemonster1o1.palpebratingpesl

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import io.github.boogiemonster1o1.palpebratingpesl.`object`.CommandSourceObject
import io.github.boogiemonster1o1.palpebratingpesl.`object`.TextObject
import io.github.boogiemonster1o1.palpebratingpesl.`object`.holder.ObjectsObjectHolder
import io.github.boogiemonster1o1.palpebratingpesl.`object`.holder.StringUtilsObjectHolder
import io.github.boogiemonster1o1.palpebratingpesl.`object`.singleton.*
import io.github.boogiemonster1o1.palpebratingpesl.util.Fields
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.GenericArguments
import org.spongepowered.api.command.spec.CommandSpec
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.GameReloadEvent
import org.spongepowered.api.event.game.state.*
import org.spongepowered.api.text.Text
import p0nki.pesl.api.PESLContext
import p0nki.pesl.api.PESLEvalException
import p0nki.pesl.api.`object`.*
import p0nki.pesl.api.builtins.PESLBuiltins
import p0nki.pesl.api.parse.PESLParseException
import p0nki.pesl.api.parse.PESLParser
import p0nki.pesl.api.parse.PESLValidateException
import p0nki.pesl.api.token.PESLTokenList
import p0nki.pesl.api.token.PESLTokenizeException
import p0nki.pesl.api.token.PESLTokenizer
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.stream.Collectors

object Handler {
	private val peslTokenizer = PESLTokenizer()
	private val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().setLenient().create()
	private val spongeObject: PESLObject = BuiltinMapLikeObject.builtinBuilder()
		.put("getGame", FunctionObject.of(false) { GameObject })
		.put("getServer", FunctionObject.of(false) { ServerObject })
		.put("getPluginManager", FunctionObject.of(false) { PluginManagerObject })
		.put("getPlatform", FunctionObject.of(false) { PlatformObject })

	@Listener
	fun onPreInitialization(event: GamePreInitializationEvent?) {
		val logger = PalpebratingPesl.logger
		val configPath: Path = PalpebratingPesl.configDir.resolve("scripts.json")
		try {
			PalpebratingPesl.examplePeslAsset.copyToDirectory(PalpebratingPesl.configDir.resolve("scripts"), true)
		} catch (e: IOException) {
			logger.error("Error copying example script", e)
		}
		try {
			if (!Files.exists(configPath)) {
				Files.createFile(configPath)
			}
			PalpebratingPesl.config = gson.fromJson(Files.newBufferedReader(configPath), Config::class.java)
		} catch (e: IOException) {
			logger.error("Error reading file", e)
		} catch (e: JsonSyntaxException) {
			logger.error(e.message, e)
		}
	}

	@Listener
	fun onAboutToStartServer(event: GameAboutToStartServerEvent?) {
		Fields.init()
		ServerObject.init()
	}

	@Listener
	fun onStartingServer(event: GameStartingServerEvent?) {
		PalpebratingPesl.config.initScripts.forEach {
			execute(PalpebratingPesl.game.server.console, it, Collections.emptyList())
		}
	}

	@Listener
	fun onStartedServer(event: GameStartedServerEvent?) {
		PalpebratingPesl.config.serverStartedScripts.forEach {
			execute(PalpebratingPesl.game.server.console, it, Collections.emptyList())
		}
	}

	@Listener
	fun onReload(event: GameReloadEvent?) {
		PalpebratingPesl.config.reloadScripts.forEach {
			execute(PalpebratingPesl.game.server.console, it, Collections.emptyList())
		}
	}

	@Listener
	fun onInitialization(event: GameInitializationEvent) {
		Sponge.getCommandManager().register(PalpebratingPesl.pluginContainer,
			CommandSpec.builder()
				.permission("palpebratingpesl.evalpesl")
				.arguments(GenericArguments.string(Text.of("name")), GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("remaining"))))
				.executor { src, args ->
					val start = System.nanoTime()
					this.execute(src, args.getOne<String>("name").orElseThrow { AssertionError() }, args.getOne<String>("remaining").orElse("").split(" "))
					val end = System.nanoTime() - start;
					src.sendMessage(Text.builder("Finished executing script " + args.getOne<String>("name").orElseThrow { AssertionError() } + " in " + (end / 1_000_000) + " ms").toText());
					CommandResult.success()
				}
				.build(),
		"evalpesl")
	}

	private fun execute(src: CommandSource, name: String, remaining: List<String>) {
		val logger = PalpebratingPesl.logger
		val filePath: Path = PalpebratingPesl.configDir.resolve("scripts").resolve("$name.pesl")
		val tokens: PESLTokenList = try {
			peslTokenizer.tokenize(String(Files.readAllBytes(filePath)))
		} catch (e: PESLTokenizeException) {
			val index = e.index
			val builder = StringBuilder()
			builder.append("   ")
			var i = 0
			while (i < index) {
				builder.append(" ")
				i++
			}
			builder.append("^")
			with(logger) {
				while (i < index) {
						builder.append(" ")
						i++
				}
				builder.append("^")
				error(builder.toString())
				error("Tokenize error ${e.message}")
			}
			throw RuntimeException(e)
		} catch (e: IOException) {
			throw UncheckedIOException(e)
		}
		val ctx = PESLContext()
		ctx.let("Sponge", spongeObject)
		ctx.let("textOf", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			TextObject(it[0].castToString())
		})
		ctx.let("Logger", LoggerObject)
		ctx.let("Source", CommandSourceObject(src))
		ctx.let("StringUtils", StringUtilsObjectHolder.`object`)
		ctx.let("Objects", ObjectsObjectHolder.`object`)
		ctx.let("println", PESLBuiltins.PRINTLN)
		ctx.let("validateArgs", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			PESLEvalException.validArgumentListLength(it[0].asArray().values, it[1].asNumber().value.toInt())
			UndefinedObject.INSTANCE
		})
		ctx.let("args", ArrayObject(remaining.stream().map {
			StringObject(it) as PESLObject
		}.collect(Collectors.toList())))
		val astCreator = PESLParser()
		try {
			var lastObject: PESLObject
			while (tokens.hasAny()) {
				val node = astCreator.parseExpression(tokens)
				node.validate()
				lastObject = node.evaluate(ctx)
				logger.debug("Evaluated PESL Object expression -> " + lastObject.castToString())
			}
		} catch (e: PESLParseException) {
			val builder = StringBuilder()
			builder.append("  ")
			run {
				var i = 0
				while (i < e.token.start) {
					builder.append(" ")
					i++
				}
			}
			var i = 0
			while (i < e.token.end - e.token.start) {
				builder.append("^")
				i++
			}
			builder.append("\n")
			builder.append(" ").append(e.token.toString()).append("\n")
			builder.append("Parse error\n").append(e.message).append("\n")
			logger.error(builder.toString())
			throw RuntimeException(e.message, e)
		} catch (e: PESLEvalException) {
			logger.error("Eval error")
			throw RuntimeException("Encountered an unknown exception: " + e.message, e)
		} catch (e: PESLValidateException) {
			logger.error("Validate error " + e.message)
			logger.error(e.node.toString())
			throw RuntimeException(e)
		}
	}
}
