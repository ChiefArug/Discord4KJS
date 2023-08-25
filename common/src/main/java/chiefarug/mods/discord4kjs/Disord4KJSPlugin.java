package chiefarug.mods.discord4kjs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ClassFilter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.internal.JDAImpl;
import net.minecraft.server.players.UserWhiteList;
import okhttp3.OkHttp;

import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;

public class Disord4KJSPlugin extends KubeJSPlugin {
	@Override
	public void init() {
		LGGR.info("hello world");
		DiscordEvents.GROUP.register();
	}

	@Override
	public void registerClasses(ScriptType type, ClassFilter filter) {
		// use .class.getPackage() so that shadow moves the path for us when it *asms*
		filter.deny(JDA.class.getPackage().getName());
		filter.deny(JDAImpl.class.getPackage().getName());
		filter.deny(OkHttp.class.getPackage().getName());
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
