package chiefarug.mods.discord4kjs;

import com.google.common.base.Stopwatch;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.dv8tion.jda.api.JDA;

import java.time.Duration;

import static chiefarug.mods.discord4kjs.Discord4KJS.CONSOLE;
import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;

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
		running = false;
		//todo add config option, off by default, to sleep. So that we dont delay regular packs from shutting down
		// if off maybe it could also just disable the thread checking, meaning that this thread can shutdown early... unless i can find some other use for it
		Thread.sleep(500);
	}


	@Override
	public void run() {
		running = true;

		JDA jda = Discord4KJS.buildJDA();
		if (jda == null) return;

		Discord4KJS.connectToDiscord(jda);


		while (running) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				LGGR.error("Discord4KJS working thread interrupted while sleeping", e);
			}
		}

		var disconnectTimer = Stopwatch.createStarted();
		LGGR.info("Disconnecting from Discord");
		jda.shutdown();

		try {
			if (!jda.awaitShutdown(Duration.ofSeconds(10))) {
				LGGR.warn("Wasn't able to disconnect nicely from Discord within 10 seconds, forcing shutdown immediately (this will skip the any queued requests!)");
				jda.shutdownNow();
				jda.awaitShutdown();
			}
			LGGR.info("Disconnected from Discord in {}", disconnectTimer.stop());
		} catch (InterruptedException e) {
			LGGR.error("Error while waiting for JDA to shutdown", e);
		}
	}
}
