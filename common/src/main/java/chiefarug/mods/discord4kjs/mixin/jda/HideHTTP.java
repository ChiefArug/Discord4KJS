package chiefarug.mods.discord4kjs.mixin.jda;

import com.neovisionaries.ws.client.WebSocketFactory;
import dev.latvian.mods.rhino.annotations.JSConstructor;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.dv8tion.jda.api.hooks.IEventManager;
import net.dv8tion.jda.internal.requests.WebSocketClient;
import okhttp3.OkHttpClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

public class HideHTTP {
	@HideFromJS
	@Mixin(value = net.dv8tion.jda.internal.utils.IOUtil.class, remap = false)
	public abstract static class IOUtil {}

	@Mixin(value = net.dv8tion.jda.internal.utils.config.sharding.ShardingSessionConfig.class, remap = false)
	public abstract static class ShardingSessionConfig {
		@Shadow @HideFromJS public abstract okhttp3.OkHttpClient.Builder getHttpBuilder();
	}

	@Mixin(value = net.dv8tion.jda.internal.utils.config.SessionConfig.class, remap = false)
	public abstract static class SessionConfig {
		@Shadow @HideFromJS public abstract OkHttpClient getHttpClient();
	}

	@Mixin(value = net.dv8tion.jda.internal.requests.Requester.class, remap = false)
	public abstract static class Requester {
		@Shadow @HideFromJS public abstract OkHttpClient getHttpClient();
	}

	@Mixin(value = net.dv8tion.jda.internal.JDAImpl.class, remap = false)
	public abstract static class JDAImpl {

		@Shadow @HideFromJS public abstract OkHttpClient getHttpClient();
		@Shadow @HideFromJS public abstract List<Object> getRegisteredListeners();
		@Shadow @HideFromJS public abstract WebSocketFactory getWebSocketFactory();
		@Shadow @HideFromJS public abstract WebSocketClient getClient();
		// this isn't particularly harmful afaict, but it still touches too close to networking for my liking
		@Shadow @HideFromJS public abstract net.dv8tion.jda.internal.requests.Requester getRequester();
		// this also shouldnt cause harm, but it would seriously mess everything up
		@Shadow @HideFromJS public abstract void setEventManager(IEventManager eventManager);
	}

	@HideFromJS
	@Mixin(value = okhttp3.OkHttpClient.Builder.class, remap = false)
	public abstract static class OkHttpClient$Builder {
	}

	@HideFromJS
	@Mixin(value = okhttp3.OkHttpClient.class, remap = false)
	public abstract static class OkHttpClient {}
}
