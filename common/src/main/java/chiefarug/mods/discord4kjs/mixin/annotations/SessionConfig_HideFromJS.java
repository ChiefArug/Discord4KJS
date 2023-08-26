package chiefarug.mods.discord4kjs.mixin.annotations;

import dev.latvian.mods.rhino.util.HideFromJS;
import net.dv8tion.jda.internal.utils.config.SessionConfig;
import okhttp3.OkHttpClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = SessionConfig.class, remap = false)
public abstract class SessionConfig_HideFromJS {

	@Shadow
	@HideFromJS
	public abstract OkHttpClient getHttpClient();
}
