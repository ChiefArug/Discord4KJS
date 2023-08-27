package chiefarug.mods.discord4kjs.mixin;

import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.CommonProperties;
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
import static chiefarug.mods.discord4kjs.Discord4KJS.connected;

@Mixin(value = ScriptFileInfo.class, remap = false)
public abstract class SkipScriptLoadingMixin {

	@Shadow
	public abstract String getProperty(String s, String def);

	@Shadow @Final public ScriptPackInfo pack;
	@Unique
	private boolean requiresDiscordConnection;

	@Inject(method = "preload", at = @At(value = "RETURN", ordinal = 0))
	public void discord4kjs$onPreload(ScriptSource source, CallbackInfo ci) {
		requiresDiscordConnection = getProperty("discord", "false").equals("true");
		// This will mean that loading discord scripts from a datapack fails, but that is fine as no one uses those.
		if (requiresDiscordConnection && !pack.namespace.equals("server_scripts")) {
			LGGR.error("Tried to use discord header from a non server_scripts script. This is not a good idea due to other script types usually loading before we have a chance to finish connecting!");
			throw new IllegalStateException("Do not use the discord header in non server script!");
		}
	}

	@Inject(method = "skipLoading", at = @At("HEAD"))
	public void discord4kjs$skipLoading(CallbackInfoReturnable<Boolean> ci) {
		if (requiresDiscordConnection && !connected) ci.setReturnValue(true);
	}
}
