package io.github.boogiemonster1o1.palpebratingpesl

import com.google.gson.annotations.Expose

class Config {
	@Expose
	var initScripts: List<String> = ArrayList()

	@Expose
	var serverStartedScripts: List<String> = ArrayList()

	@Expose
	var reloadScripts: List<String> = ArrayList()
}
