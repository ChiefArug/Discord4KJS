package chiefarug.mods.discord4kjs.events.message;

import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

public class ReactionRemovedEventJS extends ReactionEventJS {
	public ReactionRemovedEventJS(MessageReactionRemoveEvent wrappedEvent) {
		super(wrappedEvent);
	}
}
