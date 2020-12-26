package io.github.boogiemonster1o1.palpebratingpesl.`object`.holder

import p0nki.pesl.api.PESLEvalException
import p0nki.pesl.api.`object`.*
import java.lang.RuntimeException
import java.util.*
import java.util.stream.Collectors
import kotlin.jvm.Throws

object StringUtilsObjectHolder {
	val `object` : BuiltinMapLikeObject = BuiltinMapLikeObject.builtinBuilder()
		.put("lengthOf", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			NumberObject(it[0].castToString().length.toDouble())
		})
		.put("isEmpty", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			BooleanObject(it[0].castToString().isEmpty())
		})
		.put("charAt", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			StringObject(it[0].castToString()[it[1].asNumber().value.toInt()].toString())
		})
		.put("intCharAt", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			NumberObject(it[0].castToString()[it[1].asNumber().value.toInt()].toInt().toDouble())
		})
		.put("equals", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			BooleanObject(it[0].castToString() == it[1].castToString())
		})
		.put("equalsIgnoreCase", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			BooleanObject(it[0].castToString().capitalize() == it[1].castToString().capitalize())
		})
		.put("compare", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			NumberObject(it[0].castToString().compareTo(it[1].castToString()).toDouble())
		})
		.put("compareIgnoreCase", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			NumberObject(it[0].castToString().compareTo(it[1].castToString(), false).toDouble())
		})
		.put("startsWith", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			BooleanObject(it[0].castToString().startsWith(it[1].castToString(), false))
		})
		.put("endsWith", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			BooleanObject(it[0].castToString().endsWith(it[1].castToString(), false))
		})
		.put("indexOf", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			NumberObject(it[0].castToString().indexOf(it[1].castToString()).toDouble())
		})
		.put("lastIndexOf", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			NumberObject(it[0].castToString().lastIndexOf(it[1].castToString()).toDouble())
		})
		.put("substring", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2, 3)
			val newStr : String = if (it.size == 3) {
				it[0].castToString().substring(it[1].asNumber().value.toInt(), it[2].asNumber().value.toInt())
			} else {
				it[0].castToString().substring(it[1].asNumber().value.toInt())
			}
			StringObject(newStr)
		})
		.put("concat", FunctionObject.of(false) {
			PESLEvalException.checkIndexOutOfBounds(0, it.size)
			val builder : StringBuilder = StringBuilder()
			for(str in it) {
				builder.append(str)
			}
			StringObject(builder.toString())
		})
		.put("replace", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 3)
			StringObject(it[0].castToString().replace(it[1].castToString(), it[2].castToString()))
		})
		.put("matches", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			BooleanObject(it[0].castToString().matches(createRegex(it[1].castToString())))
		})
		.put("contains", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			BooleanObject(it[0].castToString().contains(it[1].castToString()))
		})
		.put("split", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 2)
			ArrayObject(it[0].castToString().split(createRegex(it[1].castToString())).toList().stream().map { str -> StringObject(str) as PESLObject }.collect(Collectors.toList()))
		})
		.put("join", FunctionObject.of(false) {
			val builder = StringBuilder()
			for (obj in it) {
				builder.append(obj.castToString())
			}
			StringObject(builder.toString())
		})
		.put("toLowerCase", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			StringObject(it[0].castToString().toLowerCase())
		})
		.put("toUpperCase", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			StringObject(it[0].castToString().toUpperCase())
		})
		.put("trim", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			StringObject(it[0].castToString().trim())
		})
		.put("toCharArray", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1)
			ArrayObject(Arrays.stream(it[0].castToString().toCharArray().toTypedArray()).map { c -> StringObject(c.toString()) as PESLObject }.collect(Collectors.toList()))
		})
		.put("format", FunctionObject.of(false) {
			PESLEvalException.validArgumentListLength(it, 1, 2)
			StringObject(java.lang.String.format(it[0].castToString(), it[1].asArray().values.stream().map {obj -> obj.castToString()}.toArray()))
		})

	@Throws(PESLEvalException::class)
	private fun createRegex(str: String) : Regex {
		try {
			return Regex(str)
		} catch (e : RuntimeException) {
			throw PESLEvalException(e.message).initCause(e) as PESLEvalException
		}
	}
}
