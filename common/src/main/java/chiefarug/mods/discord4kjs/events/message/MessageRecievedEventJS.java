package chiefarug.mods.discord4kjs.events.message;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static chiefarug.mods.discord4kjs.DiscordTypeWrappers.tryMember;

public class MessageRecievedEventJS extends MessageEventJS {

	protected final User user;

	public MessageRecievedEventJS(MessageReceivedEvent wrappedEvent) {
		super(wrappedEvent.getMessage(), wrappedEvent);
		this.user = tryMember(wrappedEvent.getAuthor(), isGuildChannel() ? wrappedEvent.getGuild() : null);
	}

}
