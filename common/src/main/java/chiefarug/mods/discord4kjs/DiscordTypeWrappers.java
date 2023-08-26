package chiefarug.mods.discord4kjs;

import dev.latvian.mods.kubejs.util.MapJS;
import dev.latvian.mods.rhino.Context;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.regex.Matcher;

import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;
import static chiefarug.mods.discord4kjs.Discord4KJS.connected;
import static chiefarug.mods.discord4kjs.Discord4KJS.jda;
import static chiefarug.mods.discord4kjs.DiscordWrapper.defaultGuild;

public class DiscordTypeWrappers {

	private static final String PLAYING = "playing ";
	private static final String WATCHING = "watching ";
	private static final String LISTENING = "listening to ";
	private static final String STREAMING = "streaming ";
	private static final String COMPETING = "competing in ";

	public static Activity activity(Context _c, Object object) {
		if (object instanceof CharSequence cs) {
			String activityString = cs.toString().toLowerCase(Locale.ROOT);
			if (activityString.startsWith(PLAYING))
				return Activity.of(ActivityType.PLAYING, activityString.substring(PLAYING.length() - 1));
			if (activityString.startsWith(WATCHING))
				return Activity.of(Activity.ActivityType.WATCHING, activityString.substring(WATCHING.length() - 1));
			if (activityString.startsWith(LISTENING))
				return Activity.of(ActivityType.LISTENING, activityString.substring(LISTENING.length() - 1));
			if (activityString.startsWith(STREAMING)) {
				Matcher urlMatcher = Activity.STREAMING_URL.matcher(activityString);
				if (!urlMatcher.find())
					throw new IllegalArgumentException("Status that starts with 'Streaming ' needs to include a valid streaming url somewhere. A valid streaming url needs to be from either Twitch or YouTube and formatted like: https://twitch.tv/user or https://youtube.com/watch?v=videoid");
				String streamUrl = activityString.substring(urlMatcher.start(), urlMatcher.end());
				String urllessStatusStart = activityString.substring(STREAMING.length() - 1, urlMatcher.start());
				String urllessStatusEnd = activityString.substring(urlMatcher.end());
				// If there was a space on either end of the url (very likely if its in the middle of the string) then remove one of those spaces.
				if (urllessStatusStart.endsWith(" ") && urllessStatusEnd.startsWith(" ")) urllessStatusEnd = urllessStatusEnd.substring(1);
				return Activity.of(Activity.ActivityType.STREAMING, urllessStatusStart + urllessStatusEnd, streamUrl);
			}
			if (activityString.startsWith(COMPETING))
				return Activity.of(ActivityType.COMPETING, activityString.substring(COMPETING.length() - 1));
			throw new IllegalArgumentException("Status needs to start with one of 'Playing ', 'Watching', 'Listening to ', 'Streaming ' or 'Competing in '! Bots aren't allowed to set custom statuses. (actually they are, please yell at Arug to implement this once JDA supports it)");
		}

		var map = MapJS.of(object);
		if (map != null && map.containsKey("type") && map.containsKey("status")) {
			ActivityType type = ActivityType.valueOf(map.get("type").toString());
			if (type == ActivityType.STREAMING)
				return Activity.streaming(map.get("status").toString(), map.containsKey("link") ? map.get("link").toString() : null);
			return Activity.of(ActivityType.valueOf(map.get("type").toString()), map.get("status").toString());
		}
		return null;
	}

	public static Guild guild(Context _c, Object o) {
		// we can't use jda() if not connected and typewrappers
		// are likely to be used outside event blocks so we need to safeguard here
		if (!connected) return gracefullyRefuse();

		if (o instanceof Guild g) return g;
		Long snowflake = asSnowflake(o);
		if  (o != null) return jda().getGuildById(snowflake);
		if (o instanceof CharSequence cs) return jda().getGuildsByName(cs.toString(), false).get(0);


		return null;
	}

	public static User user(Context _c, Object o) {
		// we can't use jda() if not connected and typewrappers
		// are likely to be used outside event blocks so we need to safeguard here
		if (!connected) return gracefullyRefuse();

		if (o instanceof User u) return u;
		Long snowflake = asSnowflake(o);
		if  (o != null) return jda().getUserById(snowflake);
		if (o instanceof CharSequence cs) return jda().getUsersByName(cs.toString(), false).get(0);

		return null;
	}

	public static MessageChannel messageChannel(Context ctx, Object o) {
		// we can't use jda() if not connected and typewrappers
		// are likely to be used outside event blocks so we need to safeguard here
		if (!connected) return gracefullyRefuse();

		if (o instanceof MessageChannel c) return c;

		Long snowflake = asSnowflake(o);

		MessageChannel channel = jda().getChannelById(MessageChannel.class, snowflake);
		if (channel != null) return channel;
		// if its a user, use that users dms only if already open.
		// if they arent open, ask for them to be opened only if we
		// are allowed to block the thread (because that requires a request to discord)
		User user = (User) ctx.getTypeWrappers().getWrapperFactory(User.class, o).wrap(ctx, o);
		if (user != null) {
			if (user.hasPrivateChannel()) return user.openPrivateChannel().complete();
			if (Discord4KJSConfig.blockThread)
				return user.openPrivateChannel().complete();
		}

		return null;
	}

	public static ISnowflake snowflake(Context _c, Object o) {
		return () -> asSnowflake(o);
	}

	private static final double maxSafeDouble = 1L << 53;
	private static Long asSnowflake(Object o) {
		LGGR.debug(o.toString());
		if (o instanceof Number n) {
			if (!(n instanceof Long) && n.doubleValue() > maxSafeDouble)
				LGGR.error("Cannot safely use raw numbers for Discord snowflake of about " + n.longValue() + " due to precision errors. Surround it in ' to convert it to a string");
			return n.longValue();
		}
		if (o instanceof CharSequence cs) return Long.valueOf(cs.toString());
		if (o instanceof ISnowflake is) return is.getIdLong();
		return null;
	}

	@Nullable
	private static <T> T gracefullyRefuse() {
		LGGR.warn("Discord4KJS is not connected to Discord, gracefully refusing to typewrap! Consider using script headers to stop the script loading if not connected to Discord"); // todo: script headers
		return null;
	}
}
