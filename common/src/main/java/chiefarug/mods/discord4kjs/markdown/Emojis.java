package chiefarug.mods.discord4kjs.markdown;

import net.minecraft.ChatFormatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static chiefarug.mods.discord4kjs.markdown.Parser.emojiCharacters;


public class Emojis {

	public static final Map<String, String> emojis = defaultEmojis();

	public static String decorate(String string) {
		if (!string.contains(emojiCharacters)) return string;

		final StringBuilder buffer = new StringBuilder(string);
		boolean hasEmojiLeft = true;
		int lastEmojiEnd = -1;

		while (hasEmojiLeft) {
			// TODO: Move this into another method for readability
			int startIndex = buffer.indexOf(emojiCharacters, lastEmojiEnd + 1) + 1;
			if (startIndex == 0) return buffer.toString();
			// add 1 here again because minimum emoji length is 1
			int endIndex = buffer.indexOf(emojiCharacters, startIndex);
			if (endIndex == -1) {
				hasEmojiLeft = false;
				endIndex = buffer.length();
			}
			lastEmojiEnd = endIndex;

			String emojiName = buffer.substring(startIndex, endIndex).toLowerCase();
			String emoji = emojis.get(emojiName);
			if (emoji != null) {
				buffer.replace(startIndex - 1, endIndex == buffer.length() ? endIndex : endIndex + 1, emoji);
				// Could be a better way to do this, but for now this works.
				// Subtract the difference in length from the lastEmojiEnd so that it's looking in the correct place
				// Then we subtract two more so that it actually works (🤷)
 				lastEmojiEnd = lastEmojiEnd - (emojiName.length() - emoji.length() + 2);
			}
		}

		return buffer.toString();
	}

	public static Map<String, String> defaultEmojis() {
		Map<String, String> defaultEmojis = new HashMap<>();
		// Emojis MC has special support for
		defaultEmojis.put("white_square_button", "⧈");
		defaultEmojis.put("skull", "☠");
		defaultEmojis.put("crossed_swords", "⚔");
		defaultEmojis.put("pick", "⛏");
		defaultEmojis.put("bow_and_arrow", "🏹");
		defaultEmojis.put("axe", "🪓");
		defaultEmojis.put("trident", "🔱");
		defaultEmojis.put("fishing_pole_and_fish", "🎣");
		defaultEmojis.put("test_tube", "🧪");
		defaultEmojis.put("alembic", "⚗");
		defaultEmojis.put("shield", "🛡");
		defaultEmojis.put("scissors", "✂");
		defaultEmojis.put("meat", "🍖");
		defaultEmojis.put("bucket", "🪣");
		defaultEmojis.put("bell", "🔔");
		// Symbols
		defaultEmojis.put("heavy_check_mark", "✔");
		defaultEmojis.put("snowflake", "❄");
		defaultEmojis.put("x", "❌");
		defaultEmojis.put("heart", "❤");
		defaultEmojis.put("hearts", "❤");
		defaultEmojis.put("star", "⭐");
		defaultEmojis.put("diamond", "♦");
		defaultEmojis.put("diamonds", "♦");
		defaultEmojis.put("spades", "♠");
		defaultEmojis.put("clubs", "♣");
		defaultEmojis.put("musical_note", "♫");
		defaultEmojis.put("zap", "⚡");
		defaultEmojis.put("anchor", "⚓");
		defaultEmojis.put("unknown", "�");
		defaultEmojis.put("triangular_flag_on_post", "⚑");
		defaultEmojis.put("black_flag", "⚑");
		defaultEmojis.put("white_flag", "⚑");
		defaultEmojis.put("hourglass", "⏳");
		defaultEmojis.put("fire", "🔥");
		defaultEmojis.put("cloud_rain", "🌧");
		defaultEmojis.put("ocean", "🌊");
		defaultEmojis.put("information_source", "ℹ");
		defaultEmojis.put("point_left", "☜");
		defaultEmojis.put("peace", "☮");
		defaultEmojis.put("warning", "⚠");
		defaultEmojis.put("yin_yang", "☯");
		defaultEmojis.put("blue_square", "☐");
		defaultEmojis.put("green_square", "☐");
		defaultEmojis.put("white_square", "☐");
		defaultEmojis.put("black_square", "☐");
		defaultEmojis.put("checked_box", "☑");
		defaultEmojis.put("ballot_box_with_check", "☑");
		defaultEmojis.put("white_check_mark", "☑");
		defaultEmojis.put("crossed_box", "☒");
		// Faces
		defaultEmojis.put("smiley", "☺");
		defaultEmojis.put("smile", "☻");
		defaultEmojis.put("slight_frown", "☹");
		defaultEmojis.put("frowning", "☹");
		defaultEmojis.put("frowning2", "☹");
		// Controls
		defaultEmojis.put("play_pause", "⏯");
		defaultEmojis.put("eject", "⏏");
		defaultEmojis.put("fast_forward", "⏩");
		defaultEmojis.put("rewind", "⏪");
		defaultEmojis.put("track_next", "⏭");
		defaultEmojis.put("track_previous", "⏮");
		defaultEmojis.put("pause_button", "⏸");
		defaultEmojis.put("stop_button", "⏹");
		defaultEmojis.put("record_button", "⏺");

		return defaultEmojis;
	}
}
