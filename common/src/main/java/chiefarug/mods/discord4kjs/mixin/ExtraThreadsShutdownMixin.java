package chiefarug.mods.discord4kjs.mixin;

import chiefarug.mods.discord4kjs.Discord4KJSWorkingThread;
import com.ibm.icu.impl.duration.impl.Utils;
import dev.latvian.mods.kubejs.util.KubeJSBackgroundThread;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Utils.class)
public class ExtraThreadsShutdownMixin { // FIXME
	@Inject(method = "shutdownExecutors", at = @At("RETURN"))
	private static void shutdownExecutorsD4KJS(CallbackInfo ci) {
		Discord4KJSWorkingThread.running = false;
	}
}
