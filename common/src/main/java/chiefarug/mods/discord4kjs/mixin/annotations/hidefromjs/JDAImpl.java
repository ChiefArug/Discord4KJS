package chiefarug.mods.discord4kjs.mixin.annotations.hidefromjs;

import com.neovisionaries.ws.client.WebSocketFactory;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.dv8tion.jda.api.hooks.IEventManager;
import net.dv8tion.jda.internal.requests.Requester;
import net.dv8tion.jda.internal.requests.WebSocketClient;
import okhttp3.OkHttpClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(value = net.dv8tion.jda.internal.JDAImpl.class, remap = false)
public abstract class JDAImpl {

	@Shadow
	@HideFromJS
	public abstract OkHttpClient getHttpClient();

	@Shadow
	@HideFromJS // you shouldnt really be messing with event stuff either. if there is an event missing, raise an issue or pr it!
	public abstract IEventManager getEventManager();
	@Shadow
	@HideFromJS
	public abstract void setEventManager(IEventManager eventManager);
	@Shadow
	@HideFromJS
	public abstract void addEventListener(Object... listeners);
	@Shadow
	@HideFromJS
	public abstract void removeEventListener(Object... listeners);
	@Shadow
	@HideFromJS
	public abstract List<Object> getRegisteredListeners();


	@Shadow
	@HideFromJS // this isn't particularly harmful afaict, but it still touches too close to networking for my liking
	public abstract Requester getRequester();

	@Shadow
	@HideFromJS // no web sockets for you!
	public abstract WebSocketFactory getWebSocketFactory();
	@Shadow
	@HideFromJS
	public abstract WebSocketClient getClient();

}
