package chiefarug.mods.discord4kjs.events.message;

import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import static chiefarug.mods.discord4kjs.Discord4KJS.jda;
import static chiefarug.mods.discord4kjs.DiscordTypeWrappers.tryMember;
import static chiefarug.mods.discord4kjs.DiscordWrapper.getSelf;

public class ReactionAddedEventJS extends ReactionEventJS {

	private final long messageAuthorId;
	private User messageAuthor;

	public ReactionAddedEventJS(MessageReactionAddEvent wrappedEvent) {
		super(wrappedEvent);
		this.messageAuthorId = wrappedEvent.getMessageAuthorIdLong();
	}

	@Info("Returns the snowflake id of the person who authored the message that was reacted to")
	public String getMessageAuthorId() {
		return String.valueOf(messageAuthorId);
	}

	@Info("Gets the person who authored the message that was reacted to")
	public User getMessageAuthor() {
		if (messageAuthor == null) messageAuthor = tryMember(jda().getUserById(messageAuthorId));
		return messageAuthor;
	}
}
