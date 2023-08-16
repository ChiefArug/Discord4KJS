package chiefarug.mods.discord4kjs;

import chiefarug.mods.discord4kjs.events.BotReadyEventJS;
import chiefarug.mods.discord4kjs.events.DisconnectEventJS;
import chiefarug.mods.discord4kjs.events.DiscordEventJS;
import chiefarug.mods.discord4kjs.events.message.MessageDeletedEventJS;
import chiefarug.mods.discord4kjs.events.message.MessageEditedEventJS;
import chiefarug.mods.discord4kjs.events.message.MessageRecievedEventJS;
import chiefarug.mods.discord4kjs.events.UserNameUpdateEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface DiscordEvents {
	EventGroup GROUP = EventGroup.of("DiscordEvents");
	static void register() { GROUP.register(); }

	static EventHandler event(String name, Class<? extends DiscordEventJS> clss) {
		return GROUP.server(name, () -> clss);
	}

	// Lifecycle events
	EventHandler READY = GROUP.startup("botReady", () -> BotReadyEventJS.class);
	EventHandler DISCONNECT = event("disconnected", DisconnectEventJS.class);


	// User update events
	EventHandler USER_NAME_UPDATE = event("nameChanged", UserNameUpdateEventJS.class);

	// Message Events
	EventHandler MESSAGE_RECIEVED = event("messageRecieved", MessageRecievedEventJS.class);
	EventHandler MESSAGE_EDITED = event("messageEdited", MessageEditedEventJS.class);
	EventHandler MESSAGE_DELETED = event("messageDeleted", MessageDeletedEventJS.class);
}
