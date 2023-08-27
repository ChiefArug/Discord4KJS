package chiefarug.mods.discord4kjs.mixin.annotations.hidefromjs;

import dev.latvian.mods.rhino.util.HideFromJS;
import okhttp3.OkHttpClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = OkHttpClient.Builder.class, remap = false)
public abstract class OkHttpClient$Builder {

	@Shadow
	@HideFromJS // just in case you get your hands on a builder from somewhere I missed
	public abstract OkHttpClient build();
}
