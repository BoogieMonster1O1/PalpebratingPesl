package io.github.boogiemonster1o1.palpebratingpesl.objects;

import java.util.Locale;
import java.util.Optional;

import javax.annotation.Nonnull;

import io.github.boogiemonster1o1.palpebratingpesl.util.Fields;
import io.github.boogiemonster1o1.palpebratingpesl.util.FunctionObjects;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;
import org.spongepowered.api.text.format.TextColor;
import p0nki.pesl.api.PESLEvalException;
import p0nki.pesl.api.object.BuiltinMapLikeObject;
import p0nki.pesl.api.object.PESLObject;

@SuppressWarnings("NullableProblems")
public class TextObject extends BuiltinMapLikeObject implements TextRepresentable {
	private final Text.Builder builder;

	public TextObject(String text) {
		super("text_builder");
		this.builder = Text.builder(text);
		this.put("color", FunctionObjects.of(this, (args) -> {
			String arg = args.get(1).asString().getValue().toUpperCase(Locale.ROOT);
			TextColor color = Optional.ofNullable(Fields.COLORS.get(arg)).orElseThrow(() -> new PESLEvalException("Unknown color " + arg));
			TextObject builder = (TextObject) args.get(0);
			return builder.color(color);
		}));
		this.put("append", FunctionObjects.of(this, (args) -> {
			TextObject arg = (TextObject) args.get(1);
			TextObject builder = (TextObject) args.get(0);
			return builder.append(arg.toText());
		}));
	}

	@Nonnull
	@Override
	public String stringify() {
		return this.builder.build().toPlainSingle();
	}

	@Nonnull
	@Override
	public String castToString() {
		return this.builder.build().toPlainSingle();
	}

	public TextObject color(TextColor color) {
		color.applyTo(this.builder);
		return this;
	}

	public TextObject append(Text other) {
		other.applyTo(this.builder);
		return this;
	}

	@Override
	public boolean compareEquals(@Nonnull PESLObject object) {
		return false;
	}

	@Override
	public Text toText() {
		return this.builder.toText();
	}
}
