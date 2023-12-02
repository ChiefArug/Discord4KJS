package chiefarug.mods.discord4kjs;

import chiefarug.mods.discord4kjs.events.channel.ChannelUpdatedEventJS;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.script.ScriptTypeHolder;
import net.dv8tion.jda.api.entities.channel.forums.ForumTag;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.automod.AutoModExecutionEvent;
import net.dv8tion.jda.api.events.automod.AutoModRuleCreateEvent;
import net.dv8tion.jda.api.events.automod.AutoModRuleDeleteEvent;
import net.dv8tion.jda.api.events.automod.AutoModRuleUpdateEvent;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.GenericChannelEvent;
import net.dv8tion.jda.api.events.channel.forum.ForumTagRemoveEvent;
import net.dv8tion.jda.api.events.channel.forum.GenericForumTagEvent;
import net.dv8tion.jda.api.events.channel.forum.update.ForumTagUpdateEmojiEvent;
import net.dv8tion.jda.api.events.channel.forum.update.ForumTagUpdateModeratedEvent;
import net.dv8tion.jda.api.events.channel.forum.update.ForumTagUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.update.*;
import net.dv8tion.jda.api.events.emoji.EmojiAddedEvent;
import net.dv8tion.jda.api.events.emoji.EmojiRemovedEvent;
import net.dv8tion.jda.api.events.emoji.update.EmojiUpdateNameEvent;
import net.dv8tion.jda.api.events.emoji.update.EmojiUpdateRolesEvent;
import net.dv8tion.jda.api.events.guild.*;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberUpdateEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateAvatarEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateFlagsEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdatePendingEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateTimeOutEvent;
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideCreateEvent;
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideDeleteEvent;
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideUpdateEvent;
import net.dv8tion.jda.api.events.guild.scheduledevent.ScheduledEventCreateEvent;
import net.dv8tion.jda.api.events.guild.scheduledevent.ScheduledEventDeleteEvent;
import net.dv8tion.jda.api.events.guild.scheduledevent.ScheduledEventUserAddEvent;
import net.dv8tion.jda.api.events.guild.scheduledevent.ScheduledEventUserRemoveEvent;
import net.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateDescriptionEvent;
import net.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateEndTimeEvent;
import net.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateLocationEvent;
import net.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateNameEvent;
import net.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateStartTimeEvent;
import net.dv8tion.jda.api.events.guild.scheduledevent.update.ScheduledEventUpdateStatusEvent;
import net.dv8tion.jda.api.events.guild.update.*;
import net.dv8tion.jda.api.events.guild.voice.*;
import net.dv8tion.jda.api.events.interaction.command.ApplicationCommandUpdatePrivilegesEvent;
import net.dv8tion.jda.api.events.interaction.command.ApplicationUpdatePrivilegesEvent;
import net.dv8tion.jda.api.events.message.MessageBulkDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageEmbedEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateColorEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateHoistedEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateIconEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateMentionableEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePermissionsEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePositionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.events.sticker.GuildStickerAddedEvent;
import net.dv8tion.jda.api.events.sticker.GuildStickerRemovedEvent;
import net.dv8tion.jda.api.events.sticker.update.GuildStickerUpdateAvailableEvent;
import net.dv8tion.jda.api.events.sticker.update.GuildStickerUpdateDescriptionEvent;
import net.dv8tion.jda.api.events.sticker.update.GuildStickerUpdateNameEvent;
import net.dv8tion.jda.api.events.sticker.update.GuildStickerUpdateTagsEvent;
import net.dv8tion.jda.api.events.thread.ThreadHiddenEvent;
import net.dv8tion.jda.api.events.thread.ThreadRevealedEvent;
import net.dv8tion.jda.api.events.thread.member.ThreadMemberJoinEvent;
import net.dv8tion.jda.api.events.thread.member.ThreadMemberLeaveEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateGlobalNameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.IEventManager;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.ForumTagImpl;

import java.util.List;

import static chiefarug.mods.discord4kjs.Discord4KJS.CONSOLE;
import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;
import static chiefarug.mods.discord4kjs.DiscordEvents.*;

public class EventListeners extends ListenerAdapter implements IEventManager {

	private void postWrappedEvent(EventHandler handler, GenericEvent event) {
		postWrappedEventWithExtra(handler, event, null);
	}

	private void postWrappedEventWithExtra(EventHandler handler, GenericEvent event, Object extra) {
		if (handler.hasListeners()) {
			EventJS eventJS;
			try {
				eventJS = handler.eventType.get().getConstructor(event.getClass()).newInstance(event);
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}
			handler.post(handler.scriptTypePredicate instanceof ScriptTypeHolder sth ? sth : ScriptType.SERVER, extra, eventJS);
		}
	}

	public void onReady(ReadyEvent event) { postWrappedEvent(READY, event); } //todo: add global command registry event firing here, maybe

//	@Override
//	public void onSessionInvalidate(SessionInvalidateEvent event) {
//		super.onSessionInvalidate(event);
//	}
//
//	@Override
//	public void onSessionDisconnect(SessionDisconnectEvent event) {
//		super.onSessionDisconnect(event);
//	}
//
//	@Override
//	public void onSessionResume(SessionResumeEvent event) {
//		super.onSessionResume(event);
//	}
//
//	@Override
//	public void onSessionRecreate(SessionRecreateEvent event) {
//		super.onSessionRecreate(event);
//	}

//	@Override
//	public void onStatusChange(StatusChangeEvent event) {
//		super.onStatusChange(event);
//	}

	public void onShutdown(ShutdownEvent event) { postWrappedEvent(DISCONNECTED, event); }

//	@Override
//	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
//		super.onSlashCommandInteraction(event);
//	}

//	@Override
//	public void onUserContextInteraction(UserContextInteractionEvent event) {
//		super.onUserContextInteraction(event);
//	}
//
//	@Override
//	public void onMessageContextInteraction(MessageContextInteractionEvent event) {
//		super.onMessageContextInteraction(event);
//	}

//	@Override
//	public void onButtonInteraction(ButtonInteractionEvent event) {
//		super.onButtonInteraction(event);
//	}

//	@Override
//	public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
//		super.onCommandAutoCompleteInteraction(event);
//	}

//	@Override
//	public void onModalInteraction(ModalInteractionEvent event) {
//		super.onModalInteraction(event);
//	}

//	@Override
//	public void onStringSelectInteraction(StringSelectInteractionEvent event) {
//		super.onStringSelectInteraction(event);
//	}

//	@Override
//	public void onEntitySelectInteraction(EntitySelectInteractionEvent event) {
//		super.onEntitySelectInteraction(event);
//	}

	// Name change events
	public void onUserUpdateName(UserUpdateNameEvent event) { postWrappedEvent(USER_NAME_UPDATE, event); }
	public void onUserUpdateGlobalName(UserUpdateGlobalNameEvent event) { postWrappedEvent(USER_NAME_UPDATE, event); }
	public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) { postWrappedEvent(USER_NAME_UPDATE, event); }

	// We don't do presence events at the moment, cause ram and network usage go brrr
//	@Override
//	public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) { super.onUserUpdateOnlineStatus(event); }
//	@Override
//	public void onUserActivityStart(UserActivityStartEvent event) { super.onUserActivityStart(event); }
//	@Override
//	public void onUserActivityEnd(UserActivityEndEvent event) { super.onUserActivityEnd(event); }
//	@Override
//	public void onUserUpdateActivities(UserUpdateActivitiesEvent event) { super.onUserUpdateActivities(event); }

	// This may cause too much traffic, so lets not for now
//	@Override
//	public void onUserTyping(UserTypingEvent event) {
//		super.onUserTyping(event);
//	}

	private String emojiId(EmojiUnion emoji) {
		if (emoji.getType() == Emoji.Type.CUSTOM)
			return emoji.asCustom().getId();
		return emoji.getName();
	}

	// Messages
	public void onMessageReceived(MessageReceivedEvent event) { postWrappedEventWithExtra(MESSAGE_RECIEVED, event, event.getChannel().getIdLong()); }
	public void onMessageUpdate(MessageUpdateEvent event) { postWrappedEventWithExtra(MESSAGE_EDITED, event, event.getChannel().getIdLong()); }
	public void onMessageDelete(MessageDeleteEvent event) { postWrappedEventWithExtra(MESSAGE_DELETED, event, event.getChannel().getIdLong()); }
	public void onMessageBulkDelete(MessageBulkDeleteEvent event) { postWrappedEventWithExtra(MESSAGES_BULK_DELETED, event, event.getChannel().getIdLong()); }
	public void onMessageEmbed(MessageEmbedEvent event) { postWrappedEventWithExtra(MESSAGE_EMBED_ADDED, event, event.getChannel().getIdLong()); }
	public void onMessageReactionAdd(MessageReactionAddEvent event) { postWrappedEventWithExtra(REACTION_ADDED, event, emojiId(event.getEmoji()));}
	public void onMessageReactionRemove(MessageReactionRemoveEvent event) { postWrappedEventWithExtra(REACTION_REMOVED, event, emojiId(event.getEmoji()));}

//	@Override
//	public void onMessageReactionRemoveAll(MessageReactionRemoveAllEvent event) {super.onMessageReactionRemoveAll(event);}
//	@Override // maybe later
//	public void onMessageReactionRemoveEmoji(MessageReactionRemoveEmojiEvent event) {super.onMessageReactionRemoveEmoji(event);}

	public void onPermissionOverrideDelete(PermissionOverrideDeleteEvent event) {
		super.onPermissionOverrideDelete(event);
	}

	public void onPermissionOverrideUpdate(PermissionOverrideUpdateEvent event) {
		super.onPermissionOverrideUpdate(event);
	}

	public void onPermissionOverrideCreate(PermissionOverrideCreateEvent event) {
		super.onPermissionOverrideCreate(event);
	}

//	@Override
//	public void onStageInstanceDelete(StageInstanceDeleteEvent event) {super.onStageInstanceDelete(event);}
//	@Override // maybe later
//	public void onStageInstanceUpdateTopic(StageInstanceUpdateTopicEvent event) {super.onStageInstanceUpdateTopic(event);}
//	@Override
//	public void onStageInstanceUpdatePrivacyLevel(StageInstanceUpdatePrivacyLevelEvent event) {super.onStageInstanceUpdatePrivacyLevel(event);}
//	@Override
//	public void onStageInstanceCreate(StageInstanceCreateEvent event) {super.onStageInstanceCreate(event);}
	
	// Channel Updates
	public void onChannelCreate(ChannelCreateEvent event) { postWrappedEventWithExtra(CHANNEL_CREATED, event, event.getGuild()); }
	public void onChannelDelete(ChannelDeleteEvent event) { postWrappedEventWithExtra(CHANNEL_DELETED, event, event.getGuild()); }
	public void onGenericChannelUpdate(GenericChannelUpdateEvent<?> event) {
		CHANNEL_UPDATED.post(ScriptType.SERVER, event.getPropertyIdentifier(), new ChannelUpdatedEventJS<>(event));
	}
	// Forum Tag Updates
	private ForumTagImpl copyTag(ForumTag original) {
		return new ForumTagImpl(original.getIdLong())
				.setName(original.getName())
				.setEmoji(original.getEmoji().toData())
				.setModerated(original.isModerated())
				.setPosition(original.getPosition());
	}
	private void postForumTagWrappedEvent(GenericForumTagEvent event, ForumTag oldTag, ForumTag newTag) {
		if (CHANNEL_UPDATED.hasListeners())
			CHANNEL_UPDATED.post(ScriptType.SERVER, "available_tags", new ChannelUpdatedEventJS<>("available_tags", oldTag, newTag, new GenericChannelEvent(event.getJDA(), event.getResponseNumber(), event.getChannel())));
	}
	private void postForumTagWrappedEvent(GenericForumTagEvent event, ForumTag oldTag) { postForumTagWrappedEvent(event, oldTag, event.getTag()); }
	public void onForumTagAdd(GenericForumTagEvent event) { postForumTagWrappedEvent(event, null); }
	public void onForumTagRemove(ForumTagRemoveEvent event) { postForumTagWrappedEvent(event, event.getTag(), null); }
	public void onForumTagUpdateName(ForumTagUpdateNameEvent event) { postForumTagWrappedEvent(event, copyTag(event.getTag()).setName(event.getOldName())); }
	public void onForumTagUpdateEmoji(ForumTagUpdateEmojiEvent event) { postForumTagWrappedEvent(event, copyTag(event.getTag()).setEmoji(event.getOldEmoji().toData())); }
	public void onForumTagUpdateModerated(ForumTagUpdateModeratedEvent event) { postForumTagWrappedEvent(event, copyTag(event.getTag()).setModerated(event.getOldValue())); }

	// Threads
//	public void onThreadRevealed(ThreadRevealedEvent event) { postWrappedEvent(THREAD_REVEALED, event); }
//	public void onThreadHidden(ThreadHiddenEvent event) { postWrappedEvent(THREAD_HIDDEN, event); }

	public void onThreadMemberJoin(ThreadMemberJoinEvent event) {
		super.onThreadMemberJoin(event);
	}

	public void onThreadMemberLeave(ThreadMemberLeaveEvent event) {
		super.onThreadMemberLeave(event);
	}

	public void onGuildReady(GuildReadyEvent event) {
		if (Discord4KJSConfig.autofillDefaultGuild.get())
			if (DiscordWrapper.defaultGuild != null)
				DiscordWrapper.defaultGuild = event.getGuild();
			else if (event.getJDA().getGuilds().size() > 1)
				LGGR.warn("Discord4KJS is connected to more than one Discord Server! You should disable autofillDefaultGuild in {} to prevent the default guild changing between restarts", Discord4KJSConfig.CONFIG);
			// TODO: add command registry event firing here for guild commands
	}

	public void onGuildJoin(GuildJoinEvent event) {
		super.onGuildJoin(event);
	}

	public void onGuildLeave(GuildLeaveEvent event) {
		super.onGuildLeave(event);
	}
 //todo do we need these?
	public void onGuildAvailable(GuildAvailableEvent event) {
		super.onGuildAvailable(event);
	}

	public void onGuildUnavailable(GuildUnavailableEvent event) {
		super.onGuildUnavailable(event);
	}

	public void onGuildBan(GuildBanEvent event) {
		super.onGuildBan(event);
	}

	public void onGuildUnban(GuildUnbanEvent event) {
		super.onGuildUnban(event);
	}

	public void onGuildAuditLogEntryCreate(GuildAuditLogEntryCreateEvent event) {
		super.onGuildAuditLogEntryCreate(event);
	}
 // merge kicks, bans and leaving?
	public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
		super.onGuildMemberRemove(event);
	}
 //merge with channel update event?
	public void onGuildUpdateAfkChannel(GuildUpdateAfkChannelEvent event) {
		super.onGuildUpdateAfkChannel(event);
	}
 // more channel updates. or maybe guild settings update
	public void onGuildUpdateSystemChannel(GuildUpdateSystemChannelEvent event) {
		super.onGuildUpdateSystemChannel(event);
	}

	public void onGuildUpdateRulesChannel(GuildUpdateRulesChannelEvent event) {
		super.onGuildUpdateRulesChannel(event);
	}

	public void onGuildUpdateCommunityUpdatesChannel(GuildUpdateCommunityUpdatesChannelEvent event) {
		super.onGuildUpdateCommunityUpdatesChannel(event);
	}

	public void onGuildUpdateAfkTimeout(GuildUpdateAfkTimeoutEvent event) {
		super.onGuildUpdateAfkTimeout(event);
	}

	public void onGuildUpdateExplicitContentLevel(GuildUpdateExplicitContentLevelEvent event) {
		super.onGuildUpdateExplicitContentLevel(event);
	}

	public void onGuildUpdateIcon(GuildUpdateIconEvent event) {
		super.onGuildUpdateIcon(event);
	}

	public void onGuildUpdateMFALevel(GuildUpdateMFALevelEvent event) {
		super.onGuildUpdateMFALevel(event);
	}

	public void onGuildUpdateName(GuildUpdateNameEvent event) {
		super.onGuildUpdateName(event);
	}

	public void onGuildUpdateNotificationLevel(GuildUpdateNotificationLevelEvent event) {
		super.onGuildUpdateNotificationLevel(event);
	}

	public void onGuildUpdateOwner(GuildUpdateOwnerEvent event) {
		super.onGuildUpdateOwner(event);
	}

	public void onGuildUpdateSplash(GuildUpdateSplashEvent event) {
		super.onGuildUpdateSplash(event);
	}

	public void onGuildUpdateVerificationLevel(GuildUpdateVerificationLevelEvent event) {
		super.onGuildUpdateVerificationLevel(event);
	}

	public void onGuildUpdateLocale(GuildUpdateLocaleEvent event) {
		super.onGuildUpdateLocale(event);
	}

	public void onGuildUpdateFeatures(GuildUpdateFeaturesEvent event) {
		super.onGuildUpdateFeatures(event);
	}

	public void onGuildUpdateVanityCode(GuildUpdateVanityCodeEvent event) {
		super.onGuildUpdateVanityCode(event);
	}

	public void onGuildUpdateBanner(GuildUpdateBannerEvent event) {
		super.onGuildUpdateBanner(event);
	}

	public void onGuildUpdateDescription(GuildUpdateDescriptionEvent event) {
		super.onGuildUpdateDescription(event);
	}

	public void onGuildUpdateBoostTier(GuildUpdateBoostTierEvent event) {
		super.onGuildUpdateBoostTier(event);
	}

	public void onGuildUpdateBoostCount(GuildUpdateBoostCountEvent event) {
		super.onGuildUpdateBoostCount(event);
	}

	public void onGuildUpdateMaxMembers(GuildUpdateMaxMembersEvent event) {
		super.onGuildUpdateMaxMembers(event);
	}

	public void onGuildUpdateMaxPresences(GuildUpdateMaxPresencesEvent event) {
		super.onGuildUpdateMaxPresences(event);
	}

	public void onGuildUpdateNSFWLevel(GuildUpdateNSFWLevelEvent event) {
		super.onGuildUpdateNSFWLevel(event);
	}
 // event edited event, like guild and channel
	public void onScheduledEventUpdateDescription(ScheduledEventUpdateDescriptionEvent event) {
		super.onScheduledEventUpdateDescription(event);
	}

	public void onScheduledEventUpdateEndTime(ScheduledEventUpdateEndTimeEvent event) {
		super.onScheduledEventUpdateEndTime(event);
	}

	public void onScheduledEventUpdateLocation(ScheduledEventUpdateLocationEvent event) {
		super.onScheduledEventUpdateLocation(event);
	}

	public void onScheduledEventUpdateName(ScheduledEventUpdateNameEvent event) {
		super.onScheduledEventUpdateName(event);
	}

	public void onScheduledEventUpdateStartTime(ScheduledEventUpdateStartTimeEvent event) {
		super.onScheduledEventUpdateStartTime(event);
	}

	public void onScheduledEventUpdateStatus(ScheduledEventUpdateStatusEvent event) {
		super.onScheduledEventUpdateStatus(event);
	}

	public void onScheduledEventCreate(ScheduledEventCreateEvent event) {
		super.onScheduledEventCreate(event);
	}

	public void onScheduledEventDelete(ScheduledEventDeleteEvent event) {
		super.onScheduledEventDelete(event);
	}

	public void onScheduledEventUserAdd(ScheduledEventUserAddEvent event) {
		super.onScheduledEventUserAdd(event);
	}

	public void onScheduledEventUserRemove(ScheduledEventUserRemoveEvent event) {
		super.onScheduledEventUserRemove(event);
	}
 // invite event?
	public void onGuildInviteCreate(GuildInviteCreateEvent event) {
		super.onGuildInviteCreate(event);
	}

	public void onGuildInviteDelete(GuildInviteDeleteEvent event) {
		super.onGuildInviteDelete(event);
	}

	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		super.onGuildMemberJoin(event);
	}
 // generic member modified event?
	public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
		super.onGuildMemberRoleAdd(event);
	}

	public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
		super.onGuildMemberRoleRemove(event);
	}

	public void onGuildMemberUpdate(GuildMemberUpdateEvent event) {
		super.onGuildMemberUpdate(event);
	}


	public void onGuildMemberUpdateAvatar(GuildMemberUpdateAvatarEvent event) {
		super.onGuildMemberUpdateAvatar(event);
	}

	public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent event) {
		super.onGuildMemberUpdateBoostTime(event);
	}

	public void onGuildMemberUpdatePending(GuildMemberUpdatePendingEvent event) {
		super.onGuildMemberUpdatePending(event);
	}

	public void onGuildMemberUpdateFlags(GuildMemberUpdateFlagsEvent event) {
		super.onGuildMemberUpdateFlags(event);
	}

	public void onGuildMemberUpdateTimeOut(GuildMemberUpdateTimeOutEvent event) {
		super.onGuildMemberUpdateTimeOut(event);
	}

	public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
		super.onGuildVoiceUpdate(event);
	}

	public void onGuildVoiceGuildMute(GuildVoiceGuildMuteEvent event) {
		super.onGuildVoiceGuildMute(event);
	}
 // combine with GuildVoiceGuildMuteEvent. the pre combined event isnt nice
	public void onGuildVoiceSelfMute(GuildVoiceSelfMuteEvent event) {
		super.onGuildVoiceSelfMute(event);
	}
 // combine with GuildVoiceSelfDeafenEvent. the pre combined event isnt nice
	public void onGuildVoiceGuildDeafen(GuildVoiceGuildDeafenEvent event) {
		super.onGuildVoiceGuildDeafen(event);
	}

	public void onGuildVoiceSelfDeafen(GuildVoiceSelfDeafenEvent event) {
		super.onGuildVoiceSelfDeafen(event);
	}

	public void onGuildVoiceSuppress(GuildVoiceSuppressEvent event) {
		super.onGuildVoiceSuppress(event);
	}

	public void onGuildVoiceStream(GuildVoiceStreamEvent event) {
		super.onGuildVoiceStream(event);
	}

	public void onGuildVoiceVideo(GuildVoiceVideoEvent event) {
		super.onGuildVoiceVideo(event);
	}

	public void onGuildVoiceRequestToSpeak(GuildVoiceRequestToSpeakEvent event) {
		super.onGuildVoiceRequestToSpeak(event);
	}

	public void onAutoModExecution(AutoModExecutionEvent event) {
		super.onAutoModExecution(event);
	}
 // automod rule event
	public void onAutoModRuleCreate(AutoModRuleCreateEvent event) {
		super.onAutoModRuleCreate(event);
	}

	public void onAutoModRuleUpdate(AutoModRuleUpdateEvent event) {
		super.onAutoModRuleUpdate(event);
	}

	public void onAutoModRuleDelete(AutoModRuleDeleteEvent event) {
		super.onAutoModRuleDelete(event);
	}
 // role update event
	public void onRoleCreate(RoleCreateEvent event) {
		super.onRoleCreate(event);
	}

	public void onRoleDelete(RoleDeleteEvent event) {
		super.onRoleDelete(event);
	}

	public void onRoleUpdateColor(RoleUpdateColorEvent event) {
		super.onRoleUpdateColor(event);
	}

	public void onRoleUpdateHoisted(RoleUpdateHoistedEvent event) {
		super.onRoleUpdateHoisted(event);
	}

	public void onRoleUpdateIcon(RoleUpdateIconEvent event) {
		super.onRoleUpdateIcon(event);
	}

	public void onRoleUpdateMentionable(RoleUpdateMentionableEvent event) {
		super.onRoleUpdateMentionable(event);
	}

	public void onRoleUpdateName(RoleUpdateNameEvent event) {
		super.onRoleUpdateName(event);
	}

	public void onRoleUpdatePermissions(RoleUpdatePermissionsEvent event) {
		super.onRoleUpdatePermissions(event);
	}

	public void onRoleUpdatePosition(RoleUpdatePositionEvent event) {
		super.onRoleUpdatePosition(event);
	}
 // emoji update event
	public void onEmojiAdded(EmojiAddedEvent event) {
		super.onEmojiAdded(event);
	}

	public void onEmojiRemoved(EmojiRemovedEvent event) {
		super.onEmojiRemoved(event);
	}

	public void onEmojiUpdateName(EmojiUpdateNameEvent event) {
		super.onEmojiUpdateName(event);
	}

	public void onEmojiUpdateRoles(EmojiUpdateRolesEvent event) {
		super.onEmojiUpdateRoles(event);
	}
 // wuts this
	public void onApplicationCommandUpdatePrivileges(ApplicationCommandUpdatePrivilegesEvent event) {
		super.onApplicationCommandUpdatePrivileges(event);
	}

	public void onApplicationUpdatePrivileges(ApplicationUpdatePrivilegesEvent event) {
		super.onApplicationUpdatePrivileges(event);
	}
 // stticker update event
	public void onGuildStickerAdded(GuildStickerAddedEvent event) {
		super.onGuildStickerAdded(event);
	}

	public void onGuildStickerRemoved(GuildStickerRemovedEvent event) {
		super.onGuildStickerRemoved(event);
	}

	public void onGuildStickerUpdateName(GuildStickerUpdateNameEvent event) {
		super.onGuildStickerUpdateName(event);
	}

	public void onGuildStickerUpdateTags(GuildStickerUpdateTagsEvent event) {
		super.onGuildStickerUpdateTags(event);
	}

	public void onGuildStickerUpdateDescription(GuildStickerUpdateDescriptionEvent event) {
		super.onGuildStickerUpdateDescription(event);
	}

	public void onGuildStickerUpdateAvailable(GuildStickerUpdateAvailableEvent event) {
		super.onGuildStickerUpdateAvailable(event);
	}

	@Override
	public void handle(GenericEvent event) {
		try {
			onEvent(event);
		} catch (Exception e) {
			CONSOLE.error("Error while handling event from Discord: " + event, e);
		}
	}

	@Override
	public void register(Object listener) { throw new UnsupportedOperationException("Discord4KJS handles events, do not try overwrite this!"); }

	@Override
	public void unregister(Object listener) { throw new UnsupportedOperationException("Discord4KJS handles events, do not try overwrite this!"); }

	@Override
	public List<Object> getRegisteredListeners() { throw new UnsupportedOperationException("Discord4KJS handles events, do not try overwrite this!"); }
}
