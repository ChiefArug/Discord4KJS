package chiefarug.mods.discord4kjs.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;

public class UserArgumentType extends SnowflakeArgumentType<UserSnowflake> {
	@Override
	public UserSnowflake parse(StringReader reader) throws CommandSyntaxException {
		return User.fromId(super.parse(reader).getId());
	}
}
