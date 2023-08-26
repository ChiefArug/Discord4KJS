package chiefarug.mods.discord4kjs.events.channel;

import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.GenericChannelEvent;

public class ChannelDeletedEventJS extends ChannelEventJS {
	public ChannelDeletedEventJS(ChannelDeleteEvent wrappedEvent) {
		super(wrappedEvent);
	}
}
