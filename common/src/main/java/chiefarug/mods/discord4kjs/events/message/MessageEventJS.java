package chiefarug.mods.discord4kjs.events.message;

import chiefarug.mods.discord4kjs.events.DiscordEventJS;
import chiefarug.mods.discord4kjs.markdown.Parser;
import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import net.dv8tion.jda.internal.entities.ReceivedMessage;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static chiefarug.mods.discord4kjs.Discord4KJS.jda;

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

	@Info("Gets the message's id")
	public String getMessageId() {
		return messageId;
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
	public boolean isGuildChannel() {
		return channel.getType().isGuild();
	}

	@Info("Returns if the channel is in a thread in a guild")
	public boolean isThread() {
		return  channel.getType().isThread();
	}

	@Info("Gets the link to jump to this message")
	public String getLink() {
		return message.getJumpUrl();
	}

	public User getAuthor() {
		return message.getAuthor();
	}

	public boolean isOwnMessage() {
		return getAuthor().equals(jda().getSelfUser());
	}

	public void reply(String message) {
		if (channel instanceof TextChannel textChannel) {
			textChannel.sendMessage(new MessageCreateBuilder().addContent(message).mentionRepliedUser(true).build());
		}
	}

}
