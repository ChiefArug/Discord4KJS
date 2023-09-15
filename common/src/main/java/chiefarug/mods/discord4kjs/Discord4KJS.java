package chiefarug.mods.discord4kjs;

import chiefarug.mods.discord4kjs.markdown.Parser;
import chiefarug.mods.discord4kjs.markdown.Unparser;
import com.google.common.base.Stopwatch;
import com.mojang.logging.LogUtils;
import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.KubeJSPaths;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.internal.utils.Helpers;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static net.dv8tion.jda.api.utils.MemberCachePolicy.ALL;

public class Discord4KJS {

    static final Path TOKEN_PATH = KubeJSPaths.LOCAL.resolve("discord4kjs.token.txt");
	static final Path TOKEN_PATH_DISPLAY = Platform.getGameFolder().relativize(TOKEN_PATH);
    public static final String MODID = "discord4kjs";
    public static final Logger LGGR = LogUtils.getLogger();
    public transient static ConsoleJS CONSOLE = new ConsoleJS(ScriptType.SERVER, LGGR);
    private static Parser markdownParser = new Parser();
	private static Unparser markdownUnparser = new Unparser();
    static JDA jda = null;

	public static boolean isConnected() {
		return jda != null && jda().getStatus() == JDA.Status.CONNECTED;
	}

    @HideFromJS
    public static final JDA jda() {
		if (jda == null) throw new IllegalStateException("Discord4KJS hasn't loaded yet/failed to load but someone is still trying to access it!");
        return jda;
    }

    @HideFromJS
    public static void init() {
		// TODO: try have classloading barrier if no valid token is found, so that we don't eat up ram/performance with JDA
        new Discord4KJSWorkingThread().start();
		RestAction.setDefaultFailure(error -> CONSOLE.error("Error while sending request to Discord", error));
		if (Discord4KJSConfig.logSuccessfulRequests.get()) {
			RestAction.setDefaultSuccess(o -> CONSOLE.info("Request succsfully sent to Discord! Recieved: " + o));
		}
    }

    public static Component parseMarkdown(String stringWithMarkdown) {
        return markdownParser.setText(stringWithMarkdown).parse();
    }

	public static String unparseMarkdown(Component styledComponent) {
		return markdownUnparser.setComponent(styledComponent).unparse();
	}

    @NotNull
	private static String readToken() {
		String token = "";
		var tokenFile = TOKEN_PATH.toFile();
		if (!tokenFile.exists()) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(tokenFile))) {
				tokenFile.createNewFile();
				writer.append(System.lineSeparator()) // Blank line for token
						.append("Put your token on the line above. The token should end just about here ^ if you are using a monospace font").append(System.lineSeparator())
						.append("Text on lines after the token (like this one) are ignored.").append(System.lineSeparator())
						.append("DO NOT COMMIT, EXPORT OR OTHERWISE SHARE THIS FILE").append(System.lineSeparator())
						.append("If you do, regenerate the token at https://discord.com/developers/applications/");
				LGGR.info("Discord4KJS token file created at {}", TOKEN_PATH_DISPLAY);
			} catch (IOException e) {
				LGGR.error("Error while trying to write a blank Discord4KJS bot token file to {}. {}", TOKEN_PATH_DISPLAY, e);
			}
			return token; // Exit early. We just wrote the file, so reading it now is pointless
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(tokenFile))) {
			token = reader.readLine();
			token = token != null ? token.trim() : "";
			if (token.isEmpty()) {
				LGGR.warn("No valid token in Discord4KJS token file at {}. Discord4KJS will not load", TOKEN_PATH_DISPLAY);
			} else if (token.length() < 59) {
				LGGR.error("The Discord bot token in {} seems to be invalid. Will attempt to start anyway, but this probably won't go well.", TOKEN_PATH_DISPLAY);
			}
			return token;
		} catch (IOException e) {
			LGGR.error("Error while trying to read Discord4KJS bot token from {}. {}", TOKEN_PATH_DISPLAY, e);
		}
		return token;
	}

    static JDABuilder buildJDA() {
        String token = readToken();
		if (token.isEmpty()) return null;

		JDABuilder builder = JDABuilder.create(token, Discord4KJSConfig.intents.get());
		builder.setActivity(Activity.competing("the most jank Discord bot api setup")); // config?
		builder.setEventManager(new EventListeners());
		builder.setMemberCachePolicy(ALL); //todo make this a config option

		return builder;
	}

    static boolean connectToDiscord(JDABuilder jdab) {
		var loginTimer = Stopwatch.createStarted();
		LGGR.info("Connecting to Discord...");
		Discord4KJS.jda = jdab.build();
		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
			LGGR.error("Error while waiting for JDA to connect to Discord. Aborting!", e);
			return false;
		}
		LGGR.info("Connected to Discord in {}", loginTimer.stop());
		return true;
	}

	public static String getJumpUrl(String messageId, String channelId, String guildId) {
		return Helpers.format(Message.JUMP_URL, guildId != null ?guildId : "@me", channelId, messageId);
	}

}
