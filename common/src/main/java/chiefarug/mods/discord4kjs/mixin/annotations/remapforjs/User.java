package chiefarug.mods.discord4kjs.mixin.annotations.remapforjs;

import dev.latvian.mods.rhino.util.RemapForJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.dv8tion.jda.internal.entities.UserImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = UserImpl.class, remap = false)
public abstract class User implements net.dv8tion.jda.api.entities.User {
	@Shadow
	@RemapForJS("getUsername")
	public abstract String getName();

	@Unique // This is actually in the User interface, but I can't be stuffed making
	@RemapForJS("getName") // another mixin for that so lets just override it here
	public String discord4kjs$getName() {
		return net.dv8tion.jda.api.entities.User.super.getEffectiveName();
	}
}
