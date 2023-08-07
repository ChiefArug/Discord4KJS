package chiefarug.mods.discord4kjs;

import chiefarug.mods.discord4kjs.events.DisconnectEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.script.ScriptTypePredicate;

public interface DiscordEvents {
	EventGroup GROUP = EventGroup.of("DiscordEvents");
	static void register() { GROUP.register(); }

	EventHandler DISCONNECT = GROUP.add("disconnected", ScriptTypePredicate.STARTUP_OR_SERVER, DisconnectEventJS::new);
}
