package chiefarug.mods.discord4kjs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface DiscordEvents {
	EventGroup GROUP = EventGroup.of("DiscordEvents");
	static void register() { GROUP.register(); }
}
