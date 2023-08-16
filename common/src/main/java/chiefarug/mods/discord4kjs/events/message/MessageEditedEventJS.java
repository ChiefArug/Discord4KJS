package chiefarug.mods.discord4kjs.events.message;

import net.dv8tion.jda.api.events.message.MessageUpdateEvent;

public class MessageEditedEventJS extends MessageEventJS {
	public MessageEditedEventJS(MessageUpdateEvent wrappedEvent) {
		super(wrappedEvent.getMessage(), wrappedEvent);
	}



}
