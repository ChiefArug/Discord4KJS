package chiefarug.mods.discord4kjs.mixin.annotations;

import dev.latvian.mods.rhino.util.RemapForJS;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.internal.entities.MemberImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = MemberImpl.class, remap = false)
public abstract class Member_RemapForJS {
	@Shadow
	@RemapForJS("getName")
    public abstract String getEffectiveName();
}
