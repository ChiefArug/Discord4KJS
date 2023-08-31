package chiefarug.mods.discord4kjs.mixin;

import dev.latvian.mods.kubejs.script.ScriptFileInfo;
import dev.latvian.mods.kubejs.script.ScriptPackInfo;
import dev.latvian.mods.kubejs.script.ScriptSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;
import static chiefarug.mods.discord4kjs.Discord4KJS.isConnected;

@Mixin(value = ScriptFileInfo.class, remap = false)
public abstract class SkipScriptLoadingMixin {

	@Shadow
	public abstract String getProperty(String s, String def);

	@Shadow @Final public ScriptPackInfo pack;
	@Unique
	private boolean discord4kjs$requiresDiscordConnection;

	@Inject(method = "preload", at = @At(value = "RETURN", ordinal = 0))
	public void discord4kjs$onPreload(ScriptSource source, CallbackInfoReturnable<Throwable> ci) {
		discord4kjs$requiresDiscordConnection = getProperty("discord", "false").equals("true");
		// This will mean that loading discord scripts from a datapack fails, but that is fine as no one uses those.
		if (discord4kjs$requiresDiscordConnection && !pack.namespace.equals("server_scripts")) {
			throw new IllegalCallerException("Tried to use the // discord: true header in a non server_scripts script. This is not a good idea due to other script types being loaded before we have a chance to finish connecting!");
		}
	}

	@Inject(method = "skipLoading", at = @At("HEAD"))
	public void discord4kjs$skipLoading(CallbackInfoReturnable<Boolean> ci) {
		if (discord4kjs$requiresDiscordConnection && !isConnected()) ci.setReturnValue(true);
	}
}
