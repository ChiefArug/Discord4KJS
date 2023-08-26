package chiefarug.mods.discord4kjs.events.message;

import chiefarug.mods.discord4kjs.events.DiscordEventJS;
import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageBulkDeleteEvent;

import java.util.List;

public class MessageBulkDeletedEventJS extends DiscordEventJS {

	private final List<String> messageIds;

	private final Channel channel;

	public MessageBulkDeletedEventJS(MessageBulkDeleteEvent wrappedEvent) {
		super(wrappedEvent);
		this.channel = wrappedEvent.getChannel();
		this.messageIds = wrappedEvent.getMessageIds();
	}

	@Info("Gets a list of message ids that were deleted")
	public List<String> getMessageIds() {
		return messageIds;
	}

	@Info("Gets the channel the messages were in")
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
