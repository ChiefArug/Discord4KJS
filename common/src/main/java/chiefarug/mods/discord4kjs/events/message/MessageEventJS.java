package chiefarug.mods.discord4kjs.events.message;

import chiefarug.mods.discord4kjs.markdown.Parser;
import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import static chiefarug.mods.discord4kjs.Discord4KJS.jda;
import static chiefarug.mods.discord4kjs.DiscordTypeWrappers.tryMember;

public abstract class MessageEventJS extends ContentlessMessageEventJS {

	@Nullable
	protected final Message message;

	protected static final Parser markdownParser = new Parser();
	private Component cachedFormattedMesssage;

	
	protected MessageEventJS(Message message, GenericMessageEvent wrappedEvent) {
		super(wrappedEvent);
		this.message = message;
	}

	@Info("Returns the message as a Message object")
	public Message getMessage() {
		return message;
	}

	@Info("Returns the message content, with mentions (like users, channels, commands and emojis) in dislpay form (@user, #channel ect), rather than raw <@/#//id> form")
	public String getMessageContent() {
		return message.getContentDisplay();
	}

	@Info("Returns the message content in its raw form, including all formatting characters and with mentions in <id> form.")
	public String getMessageRaw() {
		return message.getContentRaw();
	}

	@Info("Returns the message as a series of appended Components with Discords markdown applied, for sending in chat.")
	public Component getMessageFormatted() {
		if (cachedFormattedMesssage == null)
			cachedFormattedMesssage = markdownParser.setText(getMessageContent()).parse();
		return cachedFormattedMesssage;
	}

	@Info("Returns the user that wrote this message")
	public User getAuthor() {
		return tryMember(message.getAuthor());
	}
}
