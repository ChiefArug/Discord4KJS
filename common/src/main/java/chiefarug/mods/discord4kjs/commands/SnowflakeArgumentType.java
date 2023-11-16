package chiefarug.mods.discord4kjs.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.internal.entities.UserSnowflakeImpl;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SnowflakeArgumentType<T extends ISnowflake> implements ArgumentType<T> {
	@Override
	public T parse(StringReader reader) throws CommandSyntaxException {
		long value = reader.readLong();
		return (T) ((ISnowflake) (() -> value));
	}
}
