package io.github.boogiemonster1o1.palpebratingpesl.`object`

import org.spongepowered.api.text.Text
import org.spongepowered.api.text.TextRepresentable
import org.spongepowered.api.text.format.TextColor
import p0nki.pesl.api.PESLEvalException
import p0nki.pesl.api.`object`.BuiltinMapLikeObject
import p0nki.pesl.api.`object`.FunctionObject
import p0nki.pesl.api.`object`.PESLObject
import java.util.*

class TextObject : BuiltinMapLikeObject, TextRepresentable {
	private val text : Text.Builder

	constructor() : super("text") {
		this.text = Text.builder()
	}

	constructor(initial : String) : super("text") {
		this.text = Text.builder(initial)
	}

	constructor(initial : Text) : super("text") {
		this.text = initial.toBuilder()
	}

	init {
		this.put("color", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			val color = it[0].asString().value
			this.color(getColor(color))
			this
		})
		this.put("append", FunctionObject.of(false) { args ->
			args.forEach {
				this.append(asText(it))
			}
			this
		})
	}

	private fun color(color: TextColor) {
		text.color(color)
	}

	private fun append(append: TextRepresentable) {
		text.append(append.toText())
		text.format
	}

	override fun toText(): Text = text.toText()

	override fun castToString(): String = toText().toPlain()

	override fun stringify(): String = castToString()

	override fun compareEquals(`object`: PESLObject): Boolean {
		return this == `object`
	}

	override fun equals(other: Any?): Boolean {
		if (other is TextObject) {
			return other.text == text
		}
		return false
	}

	override fun hashCode(): Int {
		return Objects.hash(text)
	}
}
