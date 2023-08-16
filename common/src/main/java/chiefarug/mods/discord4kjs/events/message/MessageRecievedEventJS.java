package chiefarug.mods.discord4kjs.events.message;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageRecievedEventJS extends MessageEventJS {

	public MessageRecievedEventJS(MessageReceivedEvent wrappedEvent) {
		super(wrappedEvent.getMessage(), wrappedEvent);
	}

}
