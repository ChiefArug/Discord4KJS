package chiefarug.mods.discord4kjs.events;

import chiefarug.mods.discord4kjs.markdown.Parser;
import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.internal.entities.ReceivedMessage;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class MessageEventJS extends DiscordEventJS {

	@Nullable
	protected final Message message;
	protected final String messageId;
	protected final Channel channel;

	protected final Parser markdownParser = new Parser();
	private Component cachedFormattedMesssage;

	protected MessageEventJS(Message message, String messageId, Channel channel) {
		this.message = message;
		this.messageId = messageId;
		this.channel = channel;
	}
	
	protected MessageEventJS(Message message, GenericMessageEvent wrappedEvent) {
		this(message, wrappedEvent.getMessageId(), wrappedEvent.getChannel());
	}

	public String getMessageId() {
		return messageId;
	}

	@Info("Returns the message")
	public Message getMessage() {
		return message;
	}

	@Info("Returns the message content, with mentions (like users, channels, commands and emojis) in dislpay form (@user, #channel ect), rather than raw <id> form")
	public String getMessageContent() {
		return message.getContentDisplay();
	}

	@Info("Returns the message content in its raw form, including all formatting characters and with mentions in <id> form")
	public String getMessageRaw() {
		return message.getContentRaw();
	}

	@Info("Returns the message as a series of appended Components with Discords markdown applied.")
	public Component getMessageFormatted() {
		if (cachedFormattedMesssage == null)
			cachedFormattedMesssage = markdownParser.setText(getMessageContent()).parse();
		return cachedFormattedMesssage;
	}

	public Channel getChannel() {
		return channel;
	}
}
