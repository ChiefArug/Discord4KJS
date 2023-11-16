package chiefarug.mods.discord4kjs.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import org.apache.commons.lang3.NotImplementedException;

public class MentionableArgumentType extends SnowflakeArgumentType<IMentionable> {
	@Override
	public UserSnowflake parse(StringReader reader) throws CommandSyntaxException {
		throw new NotImplementedException("no idea how to do this.. just try parse all three and see what works?");
	}
}
