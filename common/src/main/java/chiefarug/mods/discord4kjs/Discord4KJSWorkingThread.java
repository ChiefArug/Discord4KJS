package chiefarug.mods.discord4kjs;

import com.google.common.base.Stopwatch;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.dv8tion.jda.api.JDABuilder;

import java.time.Duration;

import static chiefarug.mods.discord4kjs.Discord4KJS.CONSOLE;
import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;
import static chiefarug.mods.discord4kjs.Discord4KJS.jda;

public class Discord4KJSWorkingThread extends Thread {

	@HideFromJS
	public static boolean running;

	public Discord4KJSWorkingThread() {
		super("Discord4KJS working thread");
	}



	static ConsoleJS getConsole() {
		return CONSOLE;
	}


	@Override
	public void start() {
		LGGR.info("Initializing Discord connection thread");
		super.start();
	}

	public static void shutdown() throws InterruptedException {
		if (!running) return;
		running = false;

		var disconnectTimer = Stopwatch.createStarted();
		LGGR.info("Disconnecting from Discord. Will wait {} milliseconds before force quitting!", Discord4KJSConfig.shutdownDelay);
		jda().shutdown();

		try {
			if (!jda().awaitShutdown(Duration.ofMillis(Discord4KJSConfig.shutdownDelay.get()))) {
				LGGR.warn("Wasn't able to disconnect nicely from Discord within {}ms, forcing shutdown immediately (this will skip any queued requests!)", Discord4KJSConfig.shutdownDelay);
				jda().shutdownNow();
			} else {
				LGGR.info("Disconnected from Discord in {}", disconnectTimer.stop());
			}
		} catch (InterruptedException e) {
			LGGR.error("Error while waiting for JDA to shutdown", e);
		}
	}


	@Override
	public void run() {
		running = true;

		JDABuilder jdaBuilder = Discord4KJS.buildJDA();
		if (jdaBuilder == null || !Discord4KJS.connectToDiscord(jdaBuilder)) {
			running = false;
			return;
		}
		Discord4KJSConfig.load();


		while (running) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				LGGR.error("Discord4KJS working thread interrupted while sleeping", e);
			}
		}
	}
}
