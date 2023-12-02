package chiefarug.mods.discord4kjs;

import chiefarug.mods.discord4kjs.events.BotReadyEventJS;
import chiefarug.mods.discord4kjs.events.DisconnectEventJS;
import chiefarug.mods.discord4kjs.events.DiscordEventJS;
import chiefarug.mods.discord4kjs.events.channel.ChanelMovedEventJS;
import chiefarug.mods.discord4kjs.events.channel.ChannelCreatedEventJS;
import chiefarug.mods.discord4kjs.events.channel.ChannelDeletedEventJS;
import chiefarug.mods.discord4kjs.events.channel.ChannelUpdatedEventJS;
import chiefarug.mods.discord4kjs.events.message.MessageBulkDeletedEventJS;
import chiefarug.mods.discord4kjs.events.message.MessageDeletedEventJS;
import chiefarug.mods.discord4kjs.events.message.MessageEditedEventJS;
import chiefarug.mods.discord4kjs.events.message.MessageEmbedAddedEventJS;
import chiefarug.mods.discord4kjs.events.message.MessageRecievedEventJS;
import chiefarug.mods.discord4kjs.events.UserNameUpdateEventJS;
import chiefarug.mods.discord4kjs.events.message.ReactionAddedEventJS;
import chiefarug.mods.discord4kjs.events.message.ReactionRemovedEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.Extra;

import static chiefarug.mods.discord4kjs.DiscordExtras.*;

// IF YOU ADD AN EVENT TO THIS PLEASE ALSO DOCUMENT THE INTENTS IT REQUIRES IN Discord4KJSConfig#intents
public interface DiscordEvents {
	EventGroup GROUP = EventGroup.of("DiscordEvents");
	static void register() { GROUP.register(); }

	static EventHandler event(String name, Class<? extends DiscordEventJS> clss) {
		return GROUP.server(name, () -> clss);
	}

	// Lifecycle events
	// These probably aren't terribly handy, but they exist
	EventHandler READY = GROUP.startup("botReady", () -> BotReadyEventJS.class);
	EventHandler DISCONNECTED = GROUP.startup("disconnected", () -> DisconnectEventJS.class);


	// User update events
	EventHandler USER_NAME_UPDATE = event("nameChanged", UserNameUpdateEventJS.class).extra(USER);

	// Message Events
	EventHandler MESSAGE_RECIEVED = event("messageRecieved", MessageRecievedEventJS.class).extra(CHANNEL);
	EventHandler MESSAGE_EDITED = event("messageEdited", MessageEditedEventJS.class).extra(CHANNEL);
	EventHandler MESSAGE_DELETED = event("messageDeleted", MessageDeletedEventJS.class).extra(CHANNEL);
	EventHandler MESSAGES_BULK_DELETED = event("bulkMessagesDeleted", MessageBulkDeletedEventJS.class).extra(CHANNEL);
	EventHandler MESSAGE_EMBED_ADDED = event("messageEmbedAdded", MessageEmbedAddedEventJS.class).extra(CHANNEL);
	// Reaction Events
	EventHandler REACTION_ADDED = event("reactionAdded", ReactionAddedEventJS.class).extra(EMOJI);
	EventHandler REACTION_REMOVED = event("reactionRemoved", ReactionRemovedEventJS.class).extra(EMOJI);

	// Channel events
	EventHandler CHANNEL_CREATED = event("channelCreated", ChannelCreatedEventJS.class).extra(GUILD);
	EventHandler CHANNEL_DELETED = event("channelDeleted", ChannelDeletedEventJS.class).extra(GUILD);
	EventHandler CHANNEL_UPDATED = event("channelUpdated", ChannelUpdatedEventJS.class).extra(GUILD);
	// Thread events
//	EventHandler THREAD_REVEALED = event("threadRevealed", ThreadRevealedEventJS.class);
//	EventHandler THREAD_HIDDEN = event("threadHidden", ThreadHiddenEventJS.class);

}
