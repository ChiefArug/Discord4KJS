package chiefarug.mods.discord4kjs.mixin.annotations;

import com.neovisionaries.ws.client.WebSocketFactory;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.rhino.util.RemapForJS;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.hooks.IEventManager;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.requests.Requester;
import net.dv8tion.jda.internal.requests.WebSocketClient;
import okhttp3.OkHttpClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

import java.util.List;

import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;

@Mixin(JDAImpl.class)
public abstract class JDA_HideFromJS {

	@Shadow(remap = false)
	//@HideFromJS //http client bad bad bad
	@RemapForJS("beExtraNaughty")
	public abstract OkHttpClient getHttpClient();


	@Shadow(remap = false)
	@HideFromJS // you shouldnt really be messing with event stuff either. if there is an event missing, raise an issue or pr it!
	public abstract IEventManager getEventManager();
	@Shadow(remap = false)
	@HideFromJS
	public abstract void setEventManager(IEventManager eventManager);
	@Shadow(remap = false)
	@HideFromJS
	public abstract void addEventListener(Object... listeners);
	@Shadow(remap = false)
	@HideFromJS
	public abstract void removeEventListener(Object... listeners);
	@Shadow(remap = false)
	@HideFromJS
	public abstract List<Object> getRegisteredListeners();


	@Shadow(remap = false)
	@HideFromJS // this isn't particularly harmful afaict, but it still touches too close to networking for my liking
	public abstract Requester getRequester();

	@Shadow(remap = false)
	@HideFromJS // no web sockets for you!
	public abstract WebSocketFactory getWebSocketFactory();
	@Shadow(remap = false)
	@HideFromJS
	public abstract WebSocketClient getClient();

	@Inject(at = @At("HEAD"), method = "setSelfUser", remap = false)
	public void randomMixin(CallbackInfo ci, SelfUser selfUser) {
		LGGR.debug("Print from test mixin! Self user is now: " + selfUser);
	}

}