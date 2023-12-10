package chiefarug.mods.discord4kjs;

import chiefarug.mods.discord4kjs.config.Configger;
import chiefarug.mods.discord4kjs.config.Configger.ConfigValue;
import dev.latvian.mods.kubejs.KubeJSPaths;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.nio.file.Path;
import java.util.EnumSet;
import java.util.LinkedHashMap;

import static chiefarug.mods.discord4kjs.Discord4KJS.MODID;

public class Discord4KJSConfig {

	protected static final Path CONFIG = KubeJSPaths.CONFIG.resolve("discord4kjs.properties");
	private static final Configger configger = new Configger(MODID, CONFIG, new LinkedHashMap<>(Discord4KJSConfig.class.getDeclaredFields().length), 0);

	public static void load() {
		configger.load();
	}

	public static final ConfigValue<Boolean> blockThread = configger.booleanValue("blockThread", false,
			"If the main thread is allowed to be blocked to wait for requests to discord to complete.",
			"Disabling this will cause some features to be disabled, but will also result in a more stable experience",
			"Note that by blocked I mean *everything else will stop*. Not recommended for general use."
	);

	public static final ConfigValue<Boolean> autofillDefaultGuild = configger.booleanValue("autofillDefaultGuild", true,
			"If the default guild (used for things such as getting channels without having to provide a guild) should be",
			"automatically set to the first guild that we connect to. Useful for bots that are only in one server.",
			"Note that there is no guarantee that guilds will be connected to in the same order each start so this should be",
			"false for bots that are in more than one server, or I will yell at you."
	);

	public static final ConfigValue<Integer> shutdownDelay = configger.integerValue("shutdownDelay", 500,
			"The amount of time that we should wait to disconnect from Discord when Minecraft closes.",
			"Note this will stop Minecraft from closing until we disconnect or we have waited this long, so should be used with caution.",
			"If 0 or less we will not wait at all, meaning that any currently queued requests to Discord may not send."
	);

	public static final ConfigValue<Boolean> logSuccessfulRequests = configger.booleanValue("logSuccessfulRequests", false,
			"If we should log all successful request that are made to Discord and come back to us.",
			"The logged message includes the object that was returned.",
			"Warning: will probably be spammy."
	);

	public static final ConfigValue<EnumSet<GatewayIntent>> intents = configger.enumSetValue("intents", EnumSet.of(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS), GatewayIntent.class,
			"A list of intents used to tell Discord what events and other information we want to receive",
			"Note that GUILD_PRESENCES, GUILD_MEMBERS and MESSAGE_CONTENT need to also be enabled on the Discord Developer bot page as they are privileged intents",
			"Possible values:", // TODO: say which JS events are affected here.
			"GUILD_MEMBERS // Required to keep a cache of members. Events: DiscordEvents.nameChanged",
			"GUILD_MODERATION // Not used in Discord4KJS yet",
			"GUILD_EMOJIS_AND_STICKERS // Not used in Discord4KJS yet",
			"GUILD_WEBHOOKS // Not user in Discord4KJS yet",
			"GUILD_INVITES // Not used in Discord4KJS yet",
			"GUILD_VOICE_STATES // Required to check who is connected to a voice channel. Otherwise not used in Discord4KJS yet",
			"GUILD_PRESENCES // Presence updates. This is used to lazy load members and update user properties such as name/avatar. This is a very heavy intent! Presence updates are 99% of traffic the bot will receive. To get user update events you should consider using GUILD_MEMBERS instead. This intent is primarily used to track Member.getOnlineStatus() and Member.getActivities().",
			"GUILD_MESSAGES // Message events from text channels in guilds.",
			"GUILD_MESSAGE_REACTIONS // Message reaction events in guilds.",
			"GUILD_MESSAGE_TYPING // Typing start events in guilds.",
			"DIRECT_MESSAGES // Message events in private channels.",
			"DIRECT_MESSAGE_REACTIONS // Message reaction events in private channels.",
			"DIRECT_MESSAGE_TYPING // Typing events in private channels.",
			"MESSAGE_CONTENT // Access to message content. This specifically affects messages received through the message history of a channel, or through Message Events. The content restriction does not apply if the message mentions the bot directly (using @username), is sent by the bot itself, or if the message is a direct message from a PrivateChannel.",
			"SCHEDULED_EVENTS // Scheduled Events events.",
			"AUTO_MODERATION_CONFIGURATION // Events related to AutoModRule changes.",
			"AUTO_MODERATION_EXECUTION // Events related to AutoModResponse triggers."
	);
	// TODO: CacheFlags?
}
