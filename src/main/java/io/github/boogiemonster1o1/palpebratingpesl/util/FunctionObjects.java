package io.github.boogiemonster1o1.palpebratingpesl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import p0nki.pesl.api.PESLContext;
import p0nki.pesl.api.PESLEvalException;
import p0nki.pesl.api.object.FunctionObject;
import p0nki.pesl.api.object.PESLObject;
import p0nki.pesl.api.parse.ASTNode;
import p0nki.pesl.api.parse.PESLIndentedLogger;
import p0nki.pesl.api.parse.TreeRequirement;

@SuppressWarnings("NullableProblems")
public class FunctionObjects {
	public static FunctionObject of(PESLObject thisObject, FunctionObject.PESLFunctionInterface value) {
		return new FunctionObject(thisObject, new ArrayList<>(), new ASTNode() {
			@Override
			public PESLObject evaluate(PESLContext context) throws PESLEvalException {
				List<PESLObject> arguments = context.getKey("arguments").asArray().getValues();
				return value.operate(arguments);
			}

			@Override
			public void validate(Set<TreeRequirement> requirements) {
			}

			@Override
			public void print(PESLIndentedLogger logger) {
				logger.println("[native function]");
			}
		});
	}
}
