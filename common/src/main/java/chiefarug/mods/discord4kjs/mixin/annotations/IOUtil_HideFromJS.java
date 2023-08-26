package chiefarug.mods.discord4kjs.mixin.annotations;

import dev.latvian.mods.rhino.util.HideFromJS;
import net.dv8tion.jda.internal.utils.IOUtil;
import okhttp3.OkHttpClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = IOUtil.class, remap = false)
public class IOUtil_HideFromJS {

	@Shadow
	@HideFromJS
	public static OkHttpClient.Builder newHttpClientBuilder() {return null;};
}
