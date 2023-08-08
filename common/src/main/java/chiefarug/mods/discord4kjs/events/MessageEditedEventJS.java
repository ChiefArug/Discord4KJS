package chiefarug.mods.discord4kjs.events;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import org.jetbrains.annotations.Nullable;

public class MessageEditedEventJS extends MessageEventJS {
	public MessageEditedEventJS(MessageUpdateEvent wrappedEvent) {
		super(wrappedEvent.getMessage(), wrappedEvent);
	}



}
