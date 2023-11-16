package chiefarug.mods.discord4kjs.commands;

import chiefarug.mods.discord4kjs.Discord4KJS;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;

public class RoleArgumentType extends SnowflakeArgumentType<Role> {
	@Override
	public Role parse(StringReader reader) throws CommandSyntaxException {
		return Discord4KJS.jda().getRoleById(super.parse(reader).getId());
	}
}
