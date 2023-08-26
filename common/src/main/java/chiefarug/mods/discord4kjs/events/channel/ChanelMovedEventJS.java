package chiefarug.mods.discord4kjs.events.channel;

import net.dv8tion.jda.api.events.channel.GenericChannelEvent;
import net.dv8tion.jda.api.events.channel.update.ChannelUpdatePositionEvent;

public class ChanelMovedEventJS extends ChannelEventJS {
	public ChanelMovedEventJS(ChannelUpdatePositionEvent wrappedEvent) {
		super(wrappedEvent);
	}
}
