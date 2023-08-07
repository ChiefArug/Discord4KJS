package chiefarug.mods.discord4kjs;

import com.google.common.base.Stopwatch;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.internal.JDAImpl;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;

public class Discord4KJSWorkingThread extends Thread {

	public static boolean running;
	private static ConsoleJS console = new ConsoleJS(ScriptType.STARTUP, LGGR);

	public Discord4KJSWorkingThread() {
		super("Discord4KJS working thread");
	}

	@NotNull
	private static String readToken() {
		String token = "";
		try {
			var tokenFile = Discord4KJS.TOKEN_PATH.toFile();
			if (!tokenFile.exists()) {
				tokenFile.createNewFile();
				LGGR.info("Discord4KJS token file created at {}", Discord4KJS.TOKEN_PATH_DISPLAY);
			}
			token = new BufferedReader(new FileReader(tokenFile)).readLine();
			token = token != null ? token.trim() : "";
			if (token.isEmpty()) {
				LGGR.warn("No valid token in Discord4KJS token file at {}. Discord4KJS will not load", Discord4KJS.TOKEN_PATH_DISPLAY);
			} else if (token.length() < 59) {
			   LGGR.error("The Discord bot token in {} seems to be invalid. Will attempt to start anyway, but this probably wont go well.",Discord4KJS.TOKEN_PATH_DISPLAY);
			}
			return token;
		} catch (IOException e) {
			LGGR.error("Error while trying to read Discord4KJS bot token from {}. {}", Discord4KJS.TOKEN_PATH_DISPLAY, e);
		}
		return token;
	}

	static ConsoleJS getConsole() {
		return console;
	}


	@Override
	public void start() {
		LGGR.info("Initializing Discord connection thread");
		super.start();
	}


	@Override
	public void run() {
		running = true;

		String token = readToken();
		if (token.isEmpty()) return;

		var loginTimer = Stopwatch.createStarted();
		JDABuilder builder = JDABuilder.createDefault(token);
		builder.setActivity(Activity.competing("the most jank Discord bot api setup"));
		builder.addEventListeners(new EventListeners());
		JDA jda = builder.build();

		LGGR.info("Connecting to Discord...");
		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
			LGGR.error("Error while waiting for JDA to connect to Discord. Aborting!", e);
			return;
		}
		LGGR.info("Connected to Discord in {}", loginTimer.stop());


		while (running) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LGGR.error("Discord4KJS working thread interrupted while sleeping", e);
			}
		}

		var disconnectTimer = Stopwatch.createStarted();
		LGGR.info("Disconnecting from Discord");
		jda.shutdown();

		try {
			if (!jda.awaitShutdown(Duration.ofSeconds(10))) {
				LGGR.warn("Wasn't able to disconnect nicely from Discord within 10 seconds, forcing shutdown immediately (this will skip the rate limit queue)");
				jda.shutdownNow();
			}
			LGGR.info("Disconnected from Discord in {}", disconnectTimer.stop());
		} catch (InterruptedException e) {
			LGGR.error("Error while waiting for JDA to shutdown", e);
		}
	}
}
