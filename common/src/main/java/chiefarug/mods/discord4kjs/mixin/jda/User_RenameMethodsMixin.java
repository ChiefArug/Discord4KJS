package chiefarug.mods.discord4kjs.mixin.jda;

import dev.latvian.mods.rhino.util.RemapForJS;
import net.dv8tion.jda.internal.entities.UserImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = UserImpl.class, remap = false)
public abstract class User_RenameMethodsMixin implements net.dv8tion.jda.api.entities.User {
	@Shadow
	@RemapForJS("getUsername")
	public abstract String getName();

	@Shadow
	@RemapForJS("getName")
	public abstract String getEffectiveName();
}
