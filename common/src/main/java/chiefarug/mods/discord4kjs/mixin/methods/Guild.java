package chiefarug.mods.discord4kjs.mixin.methods;

import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@RemapPrefixForJS("discord4kjs$")
@Mixin(value = net.dv8tion.jda.api.entities.Guild.class, remap = false)
public interface Guild {


}
