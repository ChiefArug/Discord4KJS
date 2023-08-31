package chiefarug.mods.discord4kjs;

import dev.latvian.mods.kubejs.event.Extra;

public class DiscordExtras {

	// For custom emojis the emojis name, like :heh: would be heh
	// For unicode emojis the emoji in unicode form, like :smile: would be 😄 and :transgender_flag: would be 🏳️‍⚧️ (note the invisible joiner characters!)
	public static final Extra EMOJI = Extra.STRING.copy();
	// A snowflake, in string format prefferably
	public static final Extra SNOWFLAKE = new Extra().transformer(DiscordExtras::toLong);
	public static final Extra CHANNEL = SNOWFLAKE.copy();
	public static final Extra USER = SNOWFLAKE.copy();
	public static final Extra GUILD = SNOWFLAKE.copy();

	private static Long toLong(Object object) {
		DiscordTypeWrappers.asSnowflake(object);
		if (object == null) {
			return null;
		}

		try {
			return Long.parseLong(object.toString());
		} catch (NumberFormatException _e) {
			return null;
		}
	}
}
