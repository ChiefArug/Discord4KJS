package chiefarug.mods.discord4kjs.events;

import chiefarug.mods.discord4kjs.DiscordWrapper;
import dev.latvian.mods.kubejs.event.EventJS;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;

import static chiefarug.mods.discord4kjs.Discord4KJS.jda;

public abstract class DiscordEventJS extends EventJS {
	//todo: add method to queue something to run on the server thread.
	//or queue all events to the server thread and execute them from there.

	// you MUST have a constructor that matches this constructor if you want to use EventListeners#postEvent
	public <T extends GenericEvent> DiscordEventJS(T wrappedEvent) {};

	public JDA getJda() {
		return jda();
	}
}
