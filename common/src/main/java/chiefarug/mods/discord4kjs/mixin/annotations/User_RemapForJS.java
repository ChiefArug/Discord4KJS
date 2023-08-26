package chiefarug.mods.discord4kjs.mixin.annotations;

import dev.latvian.mods.rhino.util.RemapForJS;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.internal.entities.UserImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = UserImpl.class, remap = false)
public abstract class User_RemapForJS {
	@Shadow
	@RemapForJS("getUsername")
	public abstract String getName();

	@Shadow
	@RemapForJS("getName")
    public abstract String getEffectiveName();
}
