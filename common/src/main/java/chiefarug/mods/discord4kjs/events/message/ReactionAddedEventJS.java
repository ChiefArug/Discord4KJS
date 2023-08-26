package chiefarug.mods.discord4kjs.events.message;

import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class ReactionAddedEventJS extends ReactionEventJS {
	public ReactionAddedEventJS(MessageReactionAddEvent wrappedEvent) {
		super(wrappedEvent);
	}
}
