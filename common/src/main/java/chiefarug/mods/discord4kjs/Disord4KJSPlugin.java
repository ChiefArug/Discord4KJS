package chiefarug.mods.discord4kjs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ClassFilter;
import net.minecraft.server.players.UserWhiteList;

import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;

public class Disord4KJSPlugin extends KubeJSPlugin {
	@Override
	public void init() {
		LGGR.info("hello world");
		DiscordEvents.GROUP.register();
	}

	@Override
	public void registerClasses(ScriptType type, ClassFilter filter) {
		filter.deny("net.dv8tio.jda"); // Is this going too far?
		filter.deny("okhttp3");
	}
	/*block list
	net.dv8tion.jda.internal
	okhttp3
	net.dv8tion.jda.api.requests
	 */

	@Override
	public void registerBindings(BindingsEvent event) {
		switch (event.getType()) {
			case STARTUP -> startupBindings(event);
			case SERVER -> serverBindings(event);
		}
	}

	private void serverBindings(BindingsEvent event) {
		if (Discord4KJS.connected) {

		}
	}

	@Override
	public void registerEvents() {
	}

	private void startupBindings(BindingsEvent event) {
		event.add("Discord", DiscordWrapper.class);
	}
}
