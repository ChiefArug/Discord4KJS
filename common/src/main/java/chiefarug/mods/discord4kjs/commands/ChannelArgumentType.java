package chiefarug.mods.discord4kjs.commands;

import chiefarug.mods.discord4kjs.Discord4KJS;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.Channel;

public class ChannelArgumentType extends SnowflakeArgumentType<Channel> {
	@Override
	public Channel parse(StringReader reader) throws CommandSyntaxException {
		return Discord4KJS.jda().getChannelById(Channel.class, super.parse(reader).getId());
	}
}
