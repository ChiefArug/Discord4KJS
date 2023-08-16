package chiefarug.mods.discord4kjs.mixin;

import chiefarug.mods.discord4kjs.Discord4KJSWorkingThread;
import dev.latvian.mods.kubejs.util.KubeJSBackgroundThread;
import net.minecraft.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;

@Mixin(Util.class)
public class ExtraThreadsShutdownMixin {
	@Inject(method = "shutdownExecutors", at = @At("RETURN"))
	private static void shutdownExecutorsD4KJS(CallbackInfo ci) throws InterruptedException {
		Discord4KJSWorkingThread.shutdown();
		LGGR.info("Shutting down gracefully");
	}
}
