package chiefarug.mods.discord4kjs.events.message;

import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageEmbedEvent;

import java.util.List;

public class MessageEmbedAddedEventJS extends ContentlessMessageEventJS {

	protected final List<MessageEmbed> embeds;

	protected MessageEmbedAddedEventJS(MessageEmbedEvent wrappedEvent) {
		super(wrappedEvent);
		this.embeds = wrappedEvent.getMessageEmbeds();
	}

	@Info("Gets the embeds now attached to this message")
	public List<MessageEmbed> getEmbeds() {
		return embeds;
	}
}
