package chiefarug.mods.discord4kjs.mixin.jda;

import dev.latvian.mods.rhino.util.RemapForJS;
import net.dv8tion.jda.api.entities.User;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = User.class, remap = false)
public interface User_RenameMethodMixin extends User {
	@Shadow
	@RemapForJS("getName")
	public abstract String getEffectiveName();
}
