package chiefarug.mods.discord4kjs.events.message;

import chiefarug.mods.discord4kjs.events.WrappedJDAEventJS;
import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;

public class MessageDeletedEventJS extends WrappedJDAEventJS {

	private final String messageId;

	private final Channel channel;

	public MessageDeletedEventJS(MessageDeleteEvent wrappedEvent) {
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

	@Info("Returns if the channel is PRIVATE, which means it is a dm (specifically the bots own dm with the message sender.")
	public boolean isDm() {
		return channel.getType() == ChannelType.PRIVATE;
	}

	@Info("Returns if the channel is in a guild, instead of being in the bots dms")
	public boolean isFromGuild() {
		return channel.getType().isGuild();
	}

	@Info("Returns if the channel is in a thread in a guild")
	public boolean isThread() {
		return  channel.getType().isThread();
	}
}
