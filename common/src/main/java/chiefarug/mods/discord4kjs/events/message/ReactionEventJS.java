package chiefarug.mods.discord4kjs.events.message;

import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;

import static chiefarug.mods.discord4kjs.DiscordTypeWrappers.tryMember;

public abstract class ReactionEventJS extends ContentlessMessageEventJS {

	protected final MessageReaction reaction;
	protected final EmojiUnion emoji;
	protected final User user;

	public ReactionEventJS(GenericMessageReactionEvent wrappedEvent) {
		super(wrappedEvent);
		this.reaction = wrappedEvent.getReaction();
		this.emoji = wrappedEvent.getEmoji();
		this.user = tryMember(wrappedEvent.getUser());
	}

	@Info("Gets information about the reaction. Note that most of the methods on the returned object are useless as Discord provides very little information to this event.")
	public MessageReaction getReaction() {
		return reaction;
	}

	@Info("Returns the emoji that was reacted with")
	public Emoji getEmoji() {
		return emoji;
	}

	@Info("Returns the emoji that was reacted with in string form, for use in messages. For unicode emojis it is just the emojis, for custom emojis it is in the format <:name:id>")
	public String getEmojiString() {
		return emoji.getFormatted();
	}

	@Info("Returns the user that added the reaction")
	public User getUser() {
		return user;
	}
}
