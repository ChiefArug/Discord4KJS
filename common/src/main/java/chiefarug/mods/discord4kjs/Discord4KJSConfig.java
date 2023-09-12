package chiefarug.mods.discord4kjs;

import dev.latvian.mods.kubejs.KubeJSPaths;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Properties;

import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;

//TODO: actually implement this
public class Discord4KJSConfig {

	public static final Path CONFIG = KubeJSPaths.CONFIG.resolve("discord4kjs.properties");
	private static final Properties properties = new Properties();

	public static void load() {
		boolean write = false;
		if (Files.exists(CONFIG)) {
			try {
				var reader = Files.newBufferedReader(CONFIG);
				properties.load(reader);
			} catch (IOException e) {
				LGGR.error("Failed loading Discord4KJS config file from " + CONFIG, e);
			}
		} else {
			write = true;
		}

//		blockThread = get("blockThread", false); // This is probably a bad idea. Let them enter callback hell anyway
		autofillDefaultGuild = get("autofillDefaultGuild", true);
		shutdownDelay = get("shutdownDelay", 500);
		intents = get("intents", EnumSet.of(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS));

		if (write) {
			try {
				var writer = Files.newBufferedWriter(CONFIG);
				properties.store(writer, "");
			} catch (IOException e) {
				LGGR.error("Failed saving Discord4KJS config file to " + CONFIG, e);
			}
		}
	}

	private static boolean get(String key, boolean defaultValue) {
		var prop = properties.getProperty(key);
		if (prop == null) return defaultValue;
		return prop.equals("true");
	}

	private static int get(String key, int defaultValue) {
		var prop = properties.getProperty(key);
		if (prop == null) return defaultValue;
		return Integer.parseInt(prop);
	}

	private static EnumSet<GatewayIntent> get(String key, EnumSet<GatewayIntent> defaultValue) {
		var prop = properties.getProperty("key");
		if (prop == null) return defaultValue;
		EnumSet<GatewayIntent> intents = EnumSet.noneOf(GatewayIntent.class);
		for (String s : prop.split(",")) {
			try {
				intents.add(GatewayIntent.valueOf(s.trim()));
			} catch (IllegalArgumentException _ignored) {
				LGGR.error("Invalid Discord intent '" + s + "'. Ignoring!");
			}
		}
		if (intents.isEmpty()) {
			LGGR.warn("Not valid intents found in Discord4KJS config, using the defaults instead!");
			return defaultValue;
		}
		return intents;
	}


	// If the thread is allowed to be blocked to wait for requests to discord to complete.
	// Disabling this will cause some features to be disabled, but will also result in a more stable experience
	// If you wish to enable this you MUST do this through scripts.
	public static boolean blockThread = false;
	// If the default guild (used for things such as getting channels without having to provide a guild) should be
	// automatically set to the first guild that we connect to. Useful for bots that are only in one server.
	// Note that there is no guarantee that guilds will be connected to in the same order each start so this should be
	// false for bots that are in more than one server.
	public static boolean autofillDefaultGuild = true;
	// The amount of time that we should wait to disconnect from Discord when Minecraft closes.
	// Note this will stop Minecraft from closing until we disconnect or we have waited this long, so should be used with caution
	// If 0 or less we will not wait at all, meaning that
	public static int shutdownDelay = 500;
	// A list of intents used to tell Discord what events and other information we want to recieve
	// Note that GUILD_PRESENCES, GUILD_MEMBERS and MESSAGE_CONTENT need to also	a be enabled on the Discord Developer bot page as they are privleged intents
	// Possible values:
	// GUILD_MEMBERS // Events which inform us about member update/leave/join of a guild. This is required to cache all members of a guild.
	// GUILD_MODERATION // Moderation events, such as ban/unban/audit-log.
    // GUILD_EMOJIS_AND_STICKERS // Custom emoji and sticker add/update/delete events.
	// GUILD_WEBHOOKS // Webhook events.
	// GUILD_INVITES // Invite events.
	// GUILD_VOICE_STATES // Voice state events. This is used to determine which members are connected to a voice channel.
	// GUILD_PRESENCES // Presence updates. This is used to lazy load members and update user properties such as name/avatar. This is a very heavy intent! Presence updates are 99% of traffic the bot will receive. To get user update events you should consider using GUILD_MEMBERS instead. This intent is primarily used to track Member.getOnlineStatus() and Member.getActivities().
    // GUILD_MESSAGES // Message events from text channels in guilds.
    // GUILD_MESSAGE_REACTIONS // Message reaction events in guilds.
    // GUILD_MESSAGE_TYPING // Typing start events in guilds.
    // DIRECT_MESSAGES // Message events in private channels.
    // DIRECT_MESSAGE_REACTIONS // Message reaction events in private channels.
    // DIRECT_MESSAGE_TYPING // Typing events in private channels.
	// MESSAGE_CONTENT // Access to message content. This specifically affects messages received through the message history of a channel, or through Message Events. The content restriction does not apply if the message mentions the bot directly (using @username), sent by the bot itself, or if the message is a direct message from a PrivateChannel .
	// SCHEDULED_EVENTS // Scheduled Events events.
	// AUTO_MODERATION_CONFIGURATION // Events related to AutoModRule changes.
	// AUTO_MODERATION_EXECUTION // Events related to AutoModResponse triggers.
	// TODO: CacheFlags?
	public static EnumSet<GatewayIntent> intents = EnumSet.of(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.DIRECT_MESSAGE_REACTIONS);
}
