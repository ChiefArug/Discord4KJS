package chiefarug.mods.discord4kjs.events.channel;

import chiefarug.mods.discord4kjs.events.DiscordEventJS;
import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.channel.GenericChannelEvent;

public abstract class ChannelEventJS extends DiscordEventJS {

	protected final Channel channel;

	public ChannelEventJS(GenericChannelEvent wrappedEvent) {
		super(wrappedEvent);
		this.channel = wrappedEvent.getChannel();
	}

	@Info("Gets the channel this event happened to")
	public Channel getChannel() {
		return channel;
	}
}
