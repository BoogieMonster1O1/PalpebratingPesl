package io.github.boogiemonster1o1.palpebratingpesl.objects

import org.spongepowered.api.plugin.PluginContainer
import p0nki.pesl.api.`object`.*
import java.util.stream.Collectors

class PluginContainerObject(private val pluginContainer: PluginContainer) : BuiltinMapLikeObject("plugin_container") {
	init {
		this.put("getId", FunctionObject.of(false) {
			StringObject(this.pluginContainer.id)
		})
		this.put("getName", FunctionObject.of(false) {
			StringObject(this.pluginContainer.name)
		})
		this.put("getVersion", FunctionObject.of(false) {
			StringObject(this.pluginContainer.version.orElse(""))
		})
		this.put("getDescription", FunctionObject.of(false) {
			StringObject(this.pluginContainer.description.orElse(""))
		})
		this.put("getUrl", FunctionObject.of(false) {
			StringObject(this.pluginContainer.url.orElse(""))
		})
		this.put("getAuthors", FunctionObject.of(false) {
			ArrayObject(this.pluginContainer.authors.stream().map { StringObject(it) as PESLObject }.collect(Collectors.toList()))
		})
		this.put("getSource", FunctionObject.of(false) {
			StringObject(this.pluginContainer.source.map { it.toAbsolutePath().toString() }.orElse(""))
		})
	}

	override fun equals(other: Any?): Boolean {
		if (other is PluginContainerObject) {
			return other.pluginContainer == this.pluginContainer
		}
		return false
	}

	override fun compareEquals(`object`: PESLObject): Boolean {
		return this == `object`
	}

	override fun hashCode(): Int {
		return pluginContainer.hashCode()
	}
}
