package chiefarug.mods.discord4kjs.events.channel;

import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.GenericChannelEvent;

public class ChannelCreatedEventJS extends ChannelEventJS {
	public ChannelCreatedEventJS(ChannelCreateEvent wrappedEvent) {
		super(wrappedEvent);
	}
}
