package chiefarug.mods.discord4kjs.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandManager {

	public record SlashCommand(String description, LiteralCommandNode<SlashCommandInteraction> command) {
		String name() {
			return command().getName();
		}
		List<ArgumentCommandNode<SlashCommandInteraction, ?>> arguments() {

		}
		List<ArgumentCommandNode<SlashCommandInteraction, ?>> arguments() {

		}
	}

	protected final CommandDispatcher<SlashCommandInteraction> dispatcher = new CommandDispatcher<>();
	protected final Map<String, SlashCommand> commands = new HashMap();

	protected abstract void upsertCommand(SlashCommand command);

	public SlashCommand register(String description, LiteralArgumentBuilder<SlashCommandInteraction> builder) {
		SlashCommand command = new SlashCommand(description, buildAndValidate(builder));

		dispatcher.getRoot().addChild(command.command());
		commands.put(command.name(), command);
		upsertCommand(command);
		return command;
	}

	private LiteralCommandNode<SlashCommandInteraction> buildAndValidate(LiteralArgumentBuilder<SlashCommandInteraction> builder) {
		LiteralCommandNode<SlashCommandInteraction> command = builder.build();


		return command;
	}


		static OptionType argumentToOption(ArgumentType<?> arg) {
		if (arg instanceof IntegerArgumentType)
			return OptionType.INTEGER;
		else if (arg instanceof DoubleArgumentType || arg instanceof FloatArgumentType || arg instanceof LongArgumentType)
			return OptionType.NUMBER;
		else if (arg instanceof BoolArgumentType)
			return OptionType.BOOLEAN;
		else if (arg instanceof StringArgumentType)
			return OptionType.STRING;
		else if (arg instanceof UserArgumentType)
			return OptionType.USER;
		else if (arg instanceof RoleArgumentType)
			return OptionType.ROLE;
		else if (arg instanceof ChannelArgumentType)
			return OptionType.CHANNEL;
		else if (arg instanceof MentionableArgumentType)
			return OptionType.MENTIONABLE;
//		else if (arg instanceof AttachmentArgumentType) //TODO: Attachments, maybe
//			return OptionType.ATTACHMENT;
		return OptionType.STRING; // it will have a string form from ArgumentType#parse
	}

}
