package io.github.boogiemonster1o1.palpebratingpesl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Config {
	public static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().setLenient().create();
}
