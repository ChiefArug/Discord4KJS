package chiefarug.mods.discord4kjs;

import dev.latvian.mods.kubejs.util.MapJS;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.internal.utils.tuple.Pair;

import java.util.Locale;
import java.util.SplittableRandom;
import java.util.regex.Matcher;

public class DiscordTypeWrappers {

	private static final String PLAYING = "playing ";
	private static final String WATCHING = "watching ";
	private static final String LISTENING = "listening to ";
	private static final String STREAMING = "streaming ";
	private static final String COMPETING = "competing in ";

	public static Activity activity(Object object) {
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

}
