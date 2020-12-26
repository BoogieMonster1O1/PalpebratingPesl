package io.github.boogiemonster1o1.palpebratingpesl.objects.singleton

import io.github.boogiemonster1o1.palpebratingpesl.PalpebratingPesl
import p0nki.pesl.api.PESLEvalException
import p0nki.pesl.api.`object`.*

object LoggerObject : BuiltinMapLikeObject("logger") {
	private val logger = PalpebratingPesl.logger

	init {
		this.put("getName", FunctionObject.of(false) {
			StringObject(logger.name)
		})
		this.put("isTraceEnabled", FunctionObject.of(false) {
			BooleanObject(logger.isTraceEnabled)
		})
		this.put("trace", FunctionObject.of(false) {
			PESLEvalException.checkIndexOutOfBounds(0, it.size)
			val message = it[0].castToString()
			val args = it.subList(1, it.size)
			val arr : Array<Any> = Array(args.size) { i -> args[i] }
			logger.trace(message, arr)
			UndefinedObject.INSTANCE
		})
		this.put("isDebugEnabled", FunctionObject.of(false) {
			BooleanObject(logger.isTraceEnabled)
		})
		this.put("debug", FunctionObject.of(false) {
			PESLEvalException.checkIndexOutOfBounds(0, it.size)
			val message = it[0].castToString()
			val args = it.subList(1, it.size)
			val arr : Array<Any> = Array(args.size) { i -> args[i] }
			logger.debug(message, arr)
			UndefinedObject.INSTANCE
		})
		this.put("isInfoEnabled", FunctionObject.of(false) {
			BooleanObject(logger.isTraceEnabled)
		})
		this.put("info", FunctionObject.of(false) {
			PESLEvalException.checkIndexOutOfBounds(0, it.size)
			val message = it[0].castToString()
			val args = it.subList(1, it.size)
			val arr : Array<Any> = Array(args.size) { i -> args[i] }
			logger.info(message, arr)
			UndefinedObject.INSTANCE
		})
		this.put("isWarnEnabled", FunctionObject.of(false) {
			BooleanObject(logger.isTraceEnabled)
		})
		this.put("warn", FunctionObject.of(false) {
			PESLEvalException.checkIndexOutOfBounds(0, it.size)
			val message = it[0].castToString()
			val args = it.subList(1, it.size)
			val arr : Array<Any> = Array(args.size) { i -> args[i] }
			logger.warn(message, arr)
			UndefinedObject.INSTANCE
		})
		this.put("isErrorEnabled", FunctionObject.of(false) {
			BooleanObject(logger.isTraceEnabled)
		})
		this.put("error", FunctionObject.of(false) {
			PESLEvalException.checkIndexOutOfBounds(0, it.size)
			val message = it[0].castToString()
			val args = it.subList(1, it.size)
			val arr : Array<Any> = Array(args.size) { i -> args[i] }
			logger.error(message, arr)
			UndefinedObject.INSTANCE
		})
	}
}
