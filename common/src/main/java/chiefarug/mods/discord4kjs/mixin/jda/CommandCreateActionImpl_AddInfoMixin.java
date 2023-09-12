package chiefarug.mods.discord4kjs.mixin.jda;

import dev.latvian.mods.kubejs.typings.Generics;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.rhino.util.RemapForJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationFunction;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationMap;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.dv8tion.jda.internal.requests.restaction.CommandCreateActionImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
//TODO: mixin to RestAction/the various Impls and add default error callbacks for logging errors that happen. Maybe js onDiscordResponse event too?
@Mixin(value = CommandCreateActionImpl.class, remap = false)
public abstract class CommandCreateActionImpl_AddInfoMixin implements CommandCreateAction {

	@Shadow
	@Info("""
   			Removes an option (parameter) from this command. 
   			
   			Note: options are case-sensitive!
   			Returns true if a matching option was removed.
			""")
	public abstract boolean removeOptionByName(String name);

	@Shadow
	@Info("""
   			Removes all options (parameters) that match the provided predicate.
   			
   			Returns true if any options were removed.
			""")
	public abstract boolean removeOptions(Predicate<? super OptionData> condition);

	@Info("""
   			Removes all subcommands from this command that match the provided predicate.
   			
   			Returns true if any subcommands were removed.
			""")
	public abstract boolean removeSubcommands(Predicate<? super SubcommandData> condition);

	@Shadow
	@Info("""
   			Removes a subcommand from this command with the provided name.
   			
   			Note: subcommand names are case-sensitive!
   			Returns true if any subcommands were removed.
			""")
	public abstract boolean removeSubcommandByName(String name);

	@Shadow
	@Info("""
   			Removes all subcommand groups that match the provided predicate.
   			
   			Returns true if any subcommand groups were removed.
			""")
	public abstract boolean removeSubcommandGroups(Predicate<? super SubcommandGroupData> condition);

	@Shadow
	@Info("""
   			Removes a subcommand group from this command with the provided name.
   			
   			Note: subcommand group names are case-sensitive!
   			Returns true if any subcommand groups were removed.
			""")
	public boolean removeSubcommandGroupByName(String name) {
		return CommandCreateAction.super.removeSubcommandGroupByName(name);
	}

	@Shadow
	@Info("Returns a list of all subcommands this command has")
	public abstract List<SubcommandData> getSubcommands();

	@Shadow
	@Info("Returns a list of all subcommand groups this command has")
	public abstract List<SubcommandGroupData> getSubcommandGroups();

	@Shadow
	@Info("Returns a list of all options (parameters) this command has")
	public abstract List<OptionData> getOptions();

	@Shadow
	@Info("Sets the command name")
	public abstract CommandCreateAction setName(String name);

	@Shadow
	@Info("Sets the command's description (shown to users in smaller text below the command name)")
	public abstract CommandCreateAction setDescription(String description);

	@Shadow
	@Info("Sets the function use to localize the command. Note: this will probably be removed in the future in favour of using MC's lang system") //TODO: Automate this using MC's lang system?
	public abstract CommandCreateAction setLocalizationFunction(LocalizationFunction localizationFunction);

	@Shadow
	@Info("Adds a localization entry for this command for the specified locale") //TODO: DiscordLocale typewrapper
	public abstract CommandCreateAction setNameLocalization(DiscordLocale locale, String name);
	@Shadow
	@HideFromJS // Rhino can't remap inside the map, and I believe wouldn't let a map with keys of not string through
	public abstract CommandCreateAction setNameLocalizations(Map<DiscordLocale, String> map);

	@Shadow
	@Info("Sets the description of the command in the locale specified")
	public abstract CommandCreateAction setDescriptionLocalization(DiscordLocale locale, String description);

	@Shadow
	@HideFromJS// Rhino can't remap inside the map, and I believe wouldn't let a map with keys of not string through
	public abstract CommandCreateAction setDescriptionLocalizations(Map<DiscordLocale, String> map);

	@Shadow
	@Info("Gets a map of all localizations for this commands name")
	public abstract LocalizationMap getNameLocalizations();

	@Shadow
	@Info("Gets a map of all localizations for this commands description")
	public abstract LocalizationMap getDescriptionLocalizations();

	@Shadow
	@Info("Gets the description of the command")
	public abstract String getDescription();

	@Shadow
	@HideFromJS // Rhino hates varargs
	public abstract CommandCreateAction addOptions(OptionData... options);

	@Shadow
	@HideFromJS // Rhino won't typewrap the contents, and I can't be bothered injecting to do that manually, so yeetus
	public abstract CommandCreateAction addOptions(Collection<? extends OptionData> options);

	@Shadow
	@Info("""
   			Adds an option (parameter) to this command.
   			
   			Note: you MUST add required options before optional options.
			""")//TODO: automate the autocomplete part, so you just specify a callback for that here. See CommandAutoCompleteInteractionEvent
	public abstract CommandCreateAction addOption(OptionType type, String name, String description, boolean required, boolean autoComplete);

	@Shadow
	@Info("""
			Adds an option (parameter) to this command.
   			
   			Note: you MUST add required options before optional options.
			""")
	public abstract CommandCreateAction addOption(OptionType type, String name, String description, boolean required);

	@Shadow
	@Info("""
   			Adds an optional option (parameter) to this comamnd.
   			
   			Note: you MUST add required options before optional options.
			""")
	public abstract CommandCreateAction addOption(OptionType type, String name, String description);

	@Shadow
	@HideFromJS // Rhino doesn't do varargs well //TODO: Add singular form of this because turns out, IT DOESNT EXIST
	public abstract CommandCreateAction addSubcommands(SubcommandData... subcommands);

	@Shadow
	@HideFromJS // Rhino can't typewrap the contents of the collection
	public abstract CommandCreateAction addSubcommands(Collection<? extends SubcommandData> subcommands);

	@Shadow
	@HideFromJS // Rhino doesn't do varargs well //TODO: Add singular form of this because turns out, IT DOESNT EXIST
	public abstract CommandCreateAction addSubcommandGroups(SubcommandGroupData... groups);

	@Shadow
	@HideFromJS // Rhino can't typewrap the contents of the collection
	public abstract CommandCreateAction addSubcommandGroups(Collection<? extends SubcommandGroupData> groups);

	@Shadow
	@Info("Sets the default permissions for this command") //TODO: Typewrapper for DefaultMemberPermissions and Permissions in general (See TODO in Discord4KJSPlugin)
	public abstract CommandCreateAction setDefaultPermissions(DefaultMemberPermissions permission);

	@Shadow
	@Info("""
			Sets if this command is only available in guilds. Useful for commands that need guild context, ie ban commands.
			Defaults to false.
			""")
	public abstract CommandCreateAction setGuildOnly(boolean guildOnly);

	@Shadow
	@RemapForJS("setNsfw") // So it beens nicely
	@Info("""
   			Sets if this command is only allowed to be used in channels set to NSFW (age-restricted).
   			
   			This also stops the command being used in Dms unless the user specifically enables NSFW commands in Dms.
			""")
	public abstract CommandCreateAction setNSFW(boolean nsfw);

	@Shadow
	@Info("Gets the command name")
	public abstract String getName();

	@Shadow
	@Info("""
   			Gets the command's type.
   			
   			Can be one of SLASH, USER or MESSAGE. USER and MESSAGE commands are useable through right click then the Apps popout.
			""")//TODO figure out how you *set* this.
	public abstract Command.Type getType();

	@Shadow
	@Info("Gets the default permissions for this command")
	public abstract DefaultMemberPermissions getDefaultPermissions();

	@Shadow
	@Info("Returns if this command can only be used inside guilds (not in Dms)")
	public abstract boolean isGuildOnly();

	@Shadow
	@RemapForJS("isNsfw") // So it beens nicely
	@Info("Returns if this command can only be used in NSFW (age-restriced) channels")
	public abstract boolean isNSFW();

	@Shadow
	@Info("""
   			Sends this command off to Discord to be registered/updated.
			Note that this will not pause the thread and wait for it to finish, so any code executing after this will execute immediately.
			""")
	public void queue() {
		CommandCreateAction.super.queue();
	}
}
