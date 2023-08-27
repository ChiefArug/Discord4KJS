package chiefarug.mods.discord4kjs.mixin.annotations.hidefromjs;

import dev.latvian.mods.rhino.util.HideFromJS;
import okhttp3.OkHttpClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = net.dv8tion.jda.internal.requests.Requester.class, remap = false)
public abstract class Requester {

	@Shadow
	@HideFromJS
	public abstract OkHttpClient getHttpClient();
}
