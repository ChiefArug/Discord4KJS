package chiefarug.mods.discord4kjs.events.message;

import chiefarug.mods.discord4kjs.Discord4KJS;
import chiefarug.mods.discord4kjs.events.WrappedJDAEventJS;
import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

public class ContentlessMessageEventJS extends WrappedJDAEventJS {

	protected final String messageId;
	protected final Channel channel;

	public ContentlessMessageEventJS(GenericMessageEvent wrappedEvent) {
		super(wrappedEvent);
		this.messageId = wrappedEvent.getMessageId();
		this.channel = wrappedEvent.getChannel();
	}

	@Info("Gets the message's id")
	public String getMessageId() {
		return messageId;
	}

	@Info("Gets the channel the message was in")
	public Channel getChannel() {
		return channel;
	}

	@Info("Gets the channel type (ie FORUM, THREAD, PRIVATE (dm) or TEXT")
	public ChannelType getChannelType() {
		return channel.getType();
	}

	@Info("Returns if the channel is PRIVATE, which means it is a dm.")
	public boolean isDm() {
		return channel.getType() == ChannelType.PRIVATE;
	}

	@Info("Returns if the channel is in a guild, instead of being in the bots dms")
	public boolean isGuildChannel() {
		return channel.getType().isGuild();
	}

	@Info("Returns if the channel is in a thread in a guild")
	public boolean isThread() {
		return  channel.getType().isThread();
	}

	@Info("Gets the link to jump to this message")
	public String getLink() {
		return Discord4KJS.getJumpUrl(messageId, channel.getId(), channel instanceof GuildChannel gc ? gc.getGuild().getId() : null);
	}

	//todo wrapper for messages so that things lke embeds can be passed in using a map like format
	// might need own messagejs class to represent it
	@Info("Replies to this message with a new message")
	public void reply(String message) {
		if (channel instanceof TextChannel textChannel) {
			textChannel.sendMessage(message).setMessageReference(messageId).queue();
		}
	}

	@Info("Sends a message in the same channel as this one")
	public void sendMessage(String message) {
		if (channel instanceof TextChannel textChannel) {
			textChannel.sendMessage(message).queue();
		}
	}
}
