package chiefarug.mods.discord4kjs;

import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.EventJS;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.automod.AutoModExecutionEvent;
import net.dv8tion.jda.api.events.automod.AutoModRuleCreateEvent;
import net.dv8tion.jda.api.events.automod.AutoModRuleDeleteEvent;
import net.dv8tion.jda.api.events.automod.AutoModRuleUpdateEvent;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.forum.ForumTagAddEvent;
import net.dv8tion.jda.api.events.channel.forum.ForumTagRemoveEvent;
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
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveAllEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEmojiEvent;
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
import net.dv8tion.jda.api.events.stage.StageInstanceCreateEvent;
import net.dv8tion.jda.api.events.stage.StageInstanceDeleteEvent;
import net.dv8tion.jda.api.events.stage.update.StageInstanceUpdatePrivacyLevelEvent;
import net.dv8tion.jda.api.events.stage.update.StageInstanceUpdateTopicEvent;
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
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;
import static chiefarug.mods.discord4kjs.DiscordEvents.*;

public class EventListeners extends ListenerAdapter {

	private void postWrappedEvent(EventHandler handler, GenericEvent event) {
		if (handler.hasListeners()) {
			EventJS eventJS;
			try {
				eventJS = handler.eventType.get().getConstructor(event.getClass()).newInstance(event);
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}
			handler.post(eventJS);
		}
	}

	@Override
	public void onReady(ReadyEvent event) { postWrappedEvent(READY, event); }

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

	@Override
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

	// We don't do presence events at the moment, cause ram usage go brrr
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



	// Messages
	@Override
	public void onMessageReceived(MessageReceivedEvent event) { postWrappedEvent(MESSAGE_RECIEVED, event); }

	@Override
	public void onMessageUpdate(MessageUpdateEvent event) { postWrappedEvent(MESSAGE_EDITED, event); }

	@Override
	public void onMessageDelete(MessageDeleteEvent event) { postWrappedEvent(MESSAGE_DELETED, event); }

	@Override
	public void onMessageBulkDelete(MessageBulkDeleteEvent event) {
		super.onMessageBulkDelete(event);
	}

	@Override
	public void onMessageEmbed(MessageEmbedEvent event) {
		super.onMessageEmbed(event);
	}

	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		super.onMessageReactionAdd(event);
	}

	@Override
	public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
		super.onMessageReactionRemove(event);
	}

	@Override
	public void onMessageReactionRemoveAll(MessageReactionRemoveAllEvent event) {
		super.onMessageReactionRemoveAll(event);
	}

	@Override
	public void onMessageReactionRemoveEmoji(MessageReactionRemoveEmojiEvent event) {
		super.onMessageReactionRemoveEmoji(event);
	}

	@Override
	public void onPermissionOverrideDelete(PermissionOverrideDeleteEvent event) {
		super.onPermissionOverrideDelete(event);
	}

	@Override
	public void onPermissionOverrideUpdate(PermissionOverrideUpdateEvent event) {
		super.onPermissionOverrideUpdate(event);
	}

	@Override
	public void onPermissionOverrideCreate(PermissionOverrideCreateEvent event) {
		super.onPermissionOverrideCreate(event);
	}

	@Override
	public void onStageInstanceDelete(StageInstanceDeleteEvent event) {
		super.onStageInstanceDelete(event);
	}

	@Override
	public void onStageInstanceUpdateTopic(StageInstanceUpdateTopicEvent event) {
		super.onStageInstanceUpdateTopic(event);
	}

	@Override
	public void onStageInstanceUpdatePrivacyLevel(StageInstanceUpdatePrivacyLevelEvent event) {
		super.onStageInstanceUpdatePrivacyLevel(event);
	}

	@Override
	public void onStageInstanceCreate(StageInstanceCreateEvent event) {
		super.onStageInstanceCreate(event);
	}

	@Override
	public void onChannelCreate(ChannelCreateEvent event) {
		super.onChannelCreate(event);
	}

	@Override
	public void onChannelDelete(ChannelDeleteEvent event) {
		super.onChannelDelete(event);
	}

	// one generic channel update event?
	@Override
	public void onChannelUpdateName(ChannelUpdateNameEvent event) {
		super.onChannelUpdateName(event);
	}

	@Override
	public void onChannelUpdateFlags(ChannelUpdateFlagsEvent event) {
		super.onChannelUpdateFlags(event);
	}

	@Override
	public void onChannelUpdateNSFW(ChannelUpdateNSFWEvent event) {
		super.onChannelUpdateNSFW(event);
	}

	@Override
	public void onChannelUpdateParent(ChannelUpdateParentEvent event) {
		super.onChannelUpdateParent(event);
	}

	@Override
	public void onChannelUpdatePosition(ChannelUpdatePositionEvent event) {
		super.onChannelUpdatePosition(event);
	}

	@Override
	public void onChannelUpdateSlowmode(ChannelUpdateSlowmodeEvent event) {
		super.onChannelUpdateSlowmode(event);
	}

	@Override
	public void onChannelUpdateDefaultThreadSlowmode(ChannelUpdateDefaultThreadSlowmodeEvent event) {
		super.onChannelUpdateDefaultThreadSlowmode(event);
	}

	@Override
	public void onChannelUpdateDefaultReaction(ChannelUpdateDefaultReactionEvent event) {
		super.onChannelUpdateDefaultReaction(event);
	}

	@Override
	public void onChannelUpdateDefaultLayout(ChannelUpdateDefaultLayoutEvent event) {
		super.onChannelUpdateDefaultLayout(event);
	}

	@Override
	public void onChannelUpdateTopic(ChannelUpdateTopicEvent event) {
		super.onChannelUpdateTopic(event);
	}

	@Override
	public void onChannelUpdateType(ChannelUpdateTypeEvent event) {
		super.onChannelUpdateType(event);
	}

	@Override
	public void onChannelUpdateUserLimit(ChannelUpdateUserLimitEvent event) {
		super.onChannelUpdateUserLimit(event);
	}

	@Override
	public void onChannelUpdateArchived(ChannelUpdateArchivedEvent event) {
		super.onChannelUpdateArchived(event);
	}

	@Override
	public void onChannelUpdateArchiveTimestamp(ChannelUpdateArchiveTimestampEvent event) {
		super.onChannelUpdateArchiveTimestamp(event);
	}

	@Override
	public void onChannelUpdateAutoArchiveDuration(ChannelUpdateAutoArchiveDurationEvent event) {
		super.onChannelUpdateAutoArchiveDuration(event);
	}

	@Override
	public void onChannelUpdateLocked(ChannelUpdateLockedEvent event) {
		super.onChannelUpdateLocked(event);
	}

	@Override
	public void onChannelUpdateInvitable(ChannelUpdateInvitableEvent event) {
		super.onChannelUpdateInvitable(event);
	}

	@Override
	public void onChannelUpdateAppliedTags(ChannelUpdateAppliedTagsEvent event) {
		super.onChannelUpdateAppliedTags(event);
	}

	@Override
	public void onForumTagAdd(ForumTagAddEvent event) {
		super.onForumTagAdd(event);
	}

	@Override
	public void onForumTagRemove(ForumTagRemoveEvent event) {
		super.onForumTagRemove(event);
	}

	@Override
	public void onForumTagUpdateName(ForumTagUpdateNameEvent event) {
		super.onForumTagUpdateName(event);
	}

	@Override
	public void onForumTagUpdateEmoji(ForumTagUpdateEmojiEvent event) {
		super.onForumTagUpdateEmoji(event);
	}

	@Override
	public void onForumTagUpdateModerated(ForumTagUpdateModeratedEvent event) {
		super.onForumTagUpdateModerated(event);
	}

	@Override
	public void onThreadRevealed(ThreadRevealedEvent event) {
		super.onThreadRevealed(event);
	}

	@Override
	public void onThreadHidden(ThreadHiddenEvent event) {
		super.onThreadHidden(event);
	}

	@Override
	public void onThreadMemberJoin(ThreadMemberJoinEvent event) {
		super.onThreadMemberJoin(event);
	}

	@Override
	public void onThreadMemberLeave(ThreadMemberLeaveEvent event) {
		super.onThreadMemberLeave(event);
	}

	@Override
	public void onGuildReady(GuildReadyEvent event) {
		if (Discord4KJSConfig.autofillDefaultGuild) {
			if (DiscordWrapper.defaultGuild != null)
				DiscordWrapper.defaultGuild = event.getGuild();
			else if (event.getJDA().getGuilds().size() > 1)
				LGGR.warn("Discord4KJS is connected to more than one Discord Server! You should disable autofillDefaultGuild in {} to prevent the default guild changing between restarts", (Object) null/*TODO config file location*/);
		}
		super.onGuildReady(event);
	}

	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		super.onGuildJoin(event);
	}

	@Override
	public void onGuildLeave(GuildLeaveEvent event) {
		super.onGuildLeave(event);
	}

	@Override //todo do we need these?
	public void onGuildAvailable(GuildAvailableEvent event) {
		super.onGuildAvailable(event);
	}

	@Override
	public void onGuildUnavailable(GuildUnavailableEvent event) {
		super.onGuildUnavailable(event);
	}

	@Override
	public void onGuildBan(GuildBanEvent event) {
		super.onGuildBan(event);
	}

	@Override
	public void onGuildUnban(GuildUnbanEvent event) {
		super.onGuildUnban(event);
	}

	@Override
	public void onGuildAuditLogEntryCreate(GuildAuditLogEntryCreateEvent event) {
		super.onGuildAuditLogEntryCreate(event);
	}

	@Override // merge kicks, bans and leaving?
	public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
		super.onGuildMemberRemove(event);
	}

	@Override //merge with channel update event?
	public void onGuildUpdateAfkChannel(GuildUpdateAfkChannelEvent event) {
		super.onGuildUpdateAfkChannel(event);
	}

	@Override // more channel updates. or maybe guild settings update
	public void onGuildUpdateSystemChannel(GuildUpdateSystemChannelEvent event) {
		super.onGuildUpdateSystemChannel(event);
	}

	@Override
	public void onGuildUpdateRulesChannel(GuildUpdateRulesChannelEvent event) {
		super.onGuildUpdateRulesChannel(event);
	}

	@Override
	public void onGuildUpdateCommunityUpdatesChannel(GuildUpdateCommunityUpdatesChannelEvent event) {
		super.onGuildUpdateCommunityUpdatesChannel(event);
	}

	@Override
	public void onGuildUpdateAfkTimeout(GuildUpdateAfkTimeoutEvent event) {
		super.onGuildUpdateAfkTimeout(event);
	}

	@Override
	public void onGuildUpdateExplicitContentLevel(GuildUpdateExplicitContentLevelEvent event) {
		super.onGuildUpdateExplicitContentLevel(event);
	}

	@Override
	public void onGuildUpdateIcon(GuildUpdateIconEvent event) {
		super.onGuildUpdateIcon(event);
	}

	@Override
	public void onGuildUpdateMFALevel(GuildUpdateMFALevelEvent event) {
		super.onGuildUpdateMFALevel(event);
	}

	@Override
	public void onGuildUpdateName(GuildUpdateNameEvent event) {
		super.onGuildUpdateName(event);
	}

	@Override
	public void onGuildUpdateNotificationLevel(GuildUpdateNotificationLevelEvent event) {
		super.onGuildUpdateNotificationLevel(event);
	}

	@Override
	public void onGuildUpdateOwner(GuildUpdateOwnerEvent event) {
		super.onGuildUpdateOwner(event);
	}

	@Override
	public void onGuildUpdateSplash(GuildUpdateSplashEvent event) {
		super.onGuildUpdateSplash(event);
	}

	@Override
	public void onGuildUpdateVerificationLevel(GuildUpdateVerificationLevelEvent event) {
		super.onGuildUpdateVerificationLevel(event);
	}

	@Override
	public void onGuildUpdateLocale(GuildUpdateLocaleEvent event) {
		super.onGuildUpdateLocale(event);
	}

	@Override
	public void onGuildUpdateFeatures(GuildUpdateFeaturesEvent event) {
		super.onGuildUpdateFeatures(event);
	}

	@Override
	public void onGuildUpdateVanityCode(GuildUpdateVanityCodeEvent event) {
		super.onGuildUpdateVanityCode(event);
	}

	@Override
	public void onGuildUpdateBanner(GuildUpdateBannerEvent event) {
		super.onGuildUpdateBanner(event);
	}

	@Override
	public void onGuildUpdateDescription(GuildUpdateDescriptionEvent event) {
		super.onGuildUpdateDescription(event);
	}

	@Override
	public void onGuildUpdateBoostTier(GuildUpdateBoostTierEvent event) {
		super.onGuildUpdateBoostTier(event);
	}

	@Override
	public void onGuildUpdateBoostCount(GuildUpdateBoostCountEvent event) {
		super.onGuildUpdateBoostCount(event);
	}

	@Override
	public void onGuildUpdateMaxMembers(GuildUpdateMaxMembersEvent event) {
		super.onGuildUpdateMaxMembers(event);
	}

	@Override
	public void onGuildUpdateMaxPresences(GuildUpdateMaxPresencesEvent event) {
		super.onGuildUpdateMaxPresences(event);
	}

	@Override
	public void onGuildUpdateNSFWLevel(GuildUpdateNSFWLevelEvent event) {
		super.onGuildUpdateNSFWLevel(event);
	}

	@Override // event edited event, like guild and channel
	public void onScheduledEventUpdateDescription(ScheduledEventUpdateDescriptionEvent event) {
		super.onScheduledEventUpdateDescription(event);
	}

	@Override
	public void onScheduledEventUpdateEndTime(ScheduledEventUpdateEndTimeEvent event) {
		super.onScheduledEventUpdateEndTime(event);
	}

	@Override
	public void onScheduledEventUpdateLocation(ScheduledEventUpdateLocationEvent event) {
		super.onScheduledEventUpdateLocation(event);
	}

	@Override
	public void onScheduledEventUpdateName(ScheduledEventUpdateNameEvent event) {
		super.onScheduledEventUpdateName(event);
	}

	@Override
	public void onScheduledEventUpdateStartTime(ScheduledEventUpdateStartTimeEvent event) {
		super.onScheduledEventUpdateStartTime(event);
	}

	@Override
	public void onScheduledEventUpdateStatus(ScheduledEventUpdateStatusEvent event) {
		super.onScheduledEventUpdateStatus(event);
	}

	@Override
	public void onScheduledEventCreate(ScheduledEventCreateEvent event) {
		super.onScheduledEventCreate(event);
	}

	@Override
	public void onScheduledEventDelete(ScheduledEventDeleteEvent event) {
		super.onScheduledEventDelete(event);
	}

	@Override
	public void onScheduledEventUserAdd(ScheduledEventUserAddEvent event) {
		super.onScheduledEventUserAdd(event);
	}

	@Override
	public void onScheduledEventUserRemove(ScheduledEventUserRemoveEvent event) {
		super.onScheduledEventUserRemove(event);
	}

	@Override // invite event?
	public void onGuildInviteCreate(GuildInviteCreateEvent event) {
		super.onGuildInviteCreate(event);
	}

	@Override
	public void onGuildInviteDelete(GuildInviteDeleteEvent event) {
		super.onGuildInviteDelete(event);
	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		super.onGuildMemberJoin(event);
	}

	@Override // generic member modified event?
	public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
		super.onGuildMemberRoleAdd(event);
	}

	@Override
	public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
		super.onGuildMemberRoleRemove(event);
	}

	@Override
	public void onGuildMemberUpdate(GuildMemberUpdateEvent event) {
		super.onGuildMemberUpdate(event);
	}


	@Override
	public void onGuildMemberUpdateAvatar(GuildMemberUpdateAvatarEvent event) {
		super.onGuildMemberUpdateAvatar(event);
	}

	@Override
	public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent event) {
		super.onGuildMemberUpdateBoostTime(event);
	}

	@Override
	public void onGuildMemberUpdatePending(GuildMemberUpdatePendingEvent event) {
		super.onGuildMemberUpdatePending(event);
	}

	@Override
	public void onGuildMemberUpdateFlags(GuildMemberUpdateFlagsEvent event) {
		super.onGuildMemberUpdateFlags(event);
	}

	@Override
	public void onGuildMemberUpdateTimeOut(GuildMemberUpdateTimeOutEvent event) {
		super.onGuildMemberUpdateTimeOut(event);
	}

	@Override
	public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
		super.onGuildVoiceUpdate(event);
	}

	@Override
	public void onGuildVoiceGuildMute(GuildVoiceGuildMuteEvent event) {
		super.onGuildVoiceGuildMute(event);
	}

	@Override // combine with GuildVoiceGuildMuteEvent. the pre combined event isnt nice
	public void onGuildVoiceSelfMute(GuildVoiceSelfMuteEvent event) {
		super.onGuildVoiceSelfMute(event);
	}

	@Override // combine with GuildVoiceSelfDeafenEvent. the pre combined event isnt nice
	public void onGuildVoiceGuildDeafen(GuildVoiceGuildDeafenEvent event) {
		super.onGuildVoiceGuildDeafen(event);
	}

	@Override
	public void onGuildVoiceSelfDeafen(GuildVoiceSelfDeafenEvent event) {
		super.onGuildVoiceSelfDeafen(event);
	}

	@Override
	public void onGuildVoiceSuppress(GuildVoiceSuppressEvent event) {
		super.onGuildVoiceSuppress(event);
	}

	@Override
	public void onGuildVoiceStream(GuildVoiceStreamEvent event) {
		super.onGuildVoiceStream(event);
	}

	@Override
	public void onGuildVoiceVideo(GuildVoiceVideoEvent event) {
		super.onGuildVoiceVideo(event);
	}

	@Override
	public void onGuildVoiceRequestToSpeak(GuildVoiceRequestToSpeakEvent event) {
		super.onGuildVoiceRequestToSpeak(event);
	}

	@Override
	public void onAutoModExecution(AutoModExecutionEvent event) {
		super.onAutoModExecution(event);
	}

	@Override // automod rule event
	public void onAutoModRuleCreate(AutoModRuleCreateEvent event) {
		super.onAutoModRuleCreate(event);
	}

	@Override
	public void onAutoModRuleUpdate(AutoModRuleUpdateEvent event) {
		super.onAutoModRuleUpdate(event);
	}

	@Override
	public void onAutoModRuleDelete(AutoModRuleDeleteEvent event) {
		super.onAutoModRuleDelete(event);
	}

	@Override // role update event
	public void onRoleCreate(RoleCreateEvent event) {
		super.onRoleCreate(event);
	}

	@Override
	public void onRoleDelete(RoleDeleteEvent event) {
		super.onRoleDelete(event);
	}

	@Override
	public void onRoleUpdateColor(RoleUpdateColorEvent event) {
		super.onRoleUpdateColor(event);
	}

	@Override
	public void onRoleUpdateHoisted(RoleUpdateHoistedEvent event) {
		super.onRoleUpdateHoisted(event);
	}

	@Override
	public void onRoleUpdateIcon(RoleUpdateIconEvent event) {
		super.onRoleUpdateIcon(event);
	}

	@Override
	public void onRoleUpdateMentionable(RoleUpdateMentionableEvent event) {
		super.onRoleUpdateMentionable(event);
	}

	@Override
	public void onRoleUpdateName(RoleUpdateNameEvent event) {
		super.onRoleUpdateName(event);
	}

	@Override
	public void onRoleUpdatePermissions(RoleUpdatePermissionsEvent event) {
		super.onRoleUpdatePermissions(event);
	}

	@Override
	public void onRoleUpdatePosition(RoleUpdatePositionEvent event) {
		super.onRoleUpdatePosition(event);
	}

	@Override // emoji update event
	public void onEmojiAdded(EmojiAddedEvent event) {
		super.onEmojiAdded(event);
	}

	@Override
	public void onEmojiRemoved(EmojiRemovedEvent event) {
		super.onEmojiRemoved(event);
	}

	@Override
	public void onEmojiUpdateName(EmojiUpdateNameEvent event) {
		super.onEmojiUpdateName(event);
	}

	@Override
	public void onEmojiUpdateRoles(EmojiUpdateRolesEvent event) {
		super.onEmojiUpdateRoles(event);
	}

	@Override // wuts this
	public void onApplicationCommandUpdatePrivileges(ApplicationCommandUpdatePrivilegesEvent event) {
		super.onApplicationCommandUpdatePrivileges(event);
	}

	@Override
	public void onApplicationUpdatePrivileges(ApplicationUpdatePrivilegesEvent event) {
		super.onApplicationUpdatePrivileges(event);
	}

	@Override // stticker update event
	public void onGuildStickerAdded(GuildStickerAddedEvent event) {
		super.onGuildStickerAdded(event);
	}

	@Override
	public void onGuildStickerRemoved(GuildStickerRemovedEvent event) {
		super.onGuildStickerRemoved(event);
	}

	@Override
	public void onGuildStickerUpdateName(GuildStickerUpdateNameEvent event) {
		super.onGuildStickerUpdateName(event);
	}

	@Override
	public void onGuildStickerUpdateTags(GuildStickerUpdateTagsEvent event) {
		super.onGuildStickerUpdateTags(event);
	}

	@Override
	public void onGuildStickerUpdateDescription(GuildStickerUpdateDescriptionEvent event) {
		super.onGuildStickerUpdateDescription(event);
	}

	@Override
	public void onGuildStickerUpdateAvailable(GuildStickerUpdateAvailableEvent event) {
		super.onGuildStickerUpdateAvailable(event);
	}
}
