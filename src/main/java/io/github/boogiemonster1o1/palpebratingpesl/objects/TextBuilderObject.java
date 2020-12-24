package io.github.boogiemonster1o1.palpebratingpesl.objects;

import javax.annotation.Nonnull;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;
import org.spongepowered.api.text.format.TextColor;
import p0nki.pesl.api.object.BuiltinMapLikeObject;
import p0nki.pesl.api.object.PESLObject;

@SuppressWarnings("NullableProblems")
public class TextBuilderObject extends BuiltinMapLikeObject implements TextRepresentable {
	private final Text.Builder builder;

	public TextBuilderObject(String text) {
		super("text_builder");
		this.builder = Text.builder(text);
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

	public TextBuilderObject color(TextColor color) {
		color.applyTo(this.builder);
		return this;
	}

	public TextBuilderObject append(Text other) {
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
