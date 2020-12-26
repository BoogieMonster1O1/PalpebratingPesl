package io.github.boogiemonster1o1.palpebratingpesl.objects.singleton

import io.github.boogiemonster1o1.palpebratingpesl.PalpebratingPesl
import io.github.boogiemonster1o1.palpebratingpesl.objects.PluginContainerObject
import org.spongepowered.api.plugin.PluginManager
import p0nki.pesl.api.PESLEvalException
import p0nki.pesl.api.`object`.*
import java.util.stream.Collectors

object PluginManagerObject : BuiltinMapLikeObject("plugin_manager") {
	private val pluginManager: PluginManager = PalpebratingPesl.pluginManager

	init {
		this.put("getPlugin", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			pluginManager.getPlugin(it[0].castToString()).map { pc -> PluginContainerObject(pc) as PESLObject }.orElse(NullObject.INSTANCE)
		})
		this.put("getPlugins", FunctionObject.of(false) {
			ArrayObject(pluginManager.plugins.stream().map { pc -> PluginContainerObject(pc) as PESLObject}.collect(Collectors.toList()))
		})
		this.put("isLoaded", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			BooleanObject(pluginManager.isLoaded(it[0].castToString()))
		})
	}
}
