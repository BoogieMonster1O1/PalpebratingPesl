package io.github.boogiemonster1o1.palpebratingpesl.util;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

public class Fields {
	public static final Map<String, ChatType> CHAT_TYPES = ImmutableMap.<String, ChatType>builder()
			.put("CHAT", ChatTypes.CHAT)
			.put("ACTION_BAR", ChatTypes.ACTION_BAR)
			.put("SYSTEM", ChatTypes.SYSTEM)
			.build();
	public static final Map<String, TextColor> COLORS;

	static {
		ImmutableMap.Builder<String, TextColor> builder = ImmutableMap.builder();
		Arrays.stream(TextColors.class.getFields())
				.filter(field -> field.getType() == TextColor.class).forEach(field -> {
					try {
						builder.put(field.getName().toUpperCase(Locale.ROOT), (TextColor) field.get(null));
					} catch (IllegalAccessException e) {
						// All fields are public
						throw new AssertionError(e);
					}
				});
		COLORS = builder.build();
	}

	public static void init() {
		// intentionally left blank to trigger static initializer
	}
}
