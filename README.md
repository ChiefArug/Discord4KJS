# Discord4KJS
Discord4KJS uses [JDA (Java Discord API)](https://github.com/discord-jda/JDA) to allow creation of a Discord bot in [KubeJS](https://kubejs.com/).
This allows effortless creation of simple things such as a Minecraft-Discord chat relay, and janky creation of advanced
things like full moderation/logger/fun bots (this is not a reccomended usecase though).


## For Scripters
There isn't a wiki yet. Sorry. A fair amount of stuff is documented through @Info
annotations, so I highly reccomend installing [ProbeJS](https://www.curseforge.com/minecraft/mc-mods/probejs) to be able to see those.
There is also this example that I use in my dev environment:
```js
Discord.setDefaultGuild('1137329641561018500')

PlayerEvents.chat(event => {
    let unparsedMessage = Discord.unparseMarkdown(event.component)
    Discord.getChannel('1137329689136996393').sendMessage('<' + event.player.name.string + '> ' + unparsedMessage).queue()
    Discord.getGuild('1014383755923836928').getChannel('1014383756435533826').sendMessage('<' + event.player.name.string + '> ' + unparsedMessage).queue()
})

DiscordEvents.messageRecieved(event => {
    if (event.author.isSelf()) return;
    Utils.server.tell(event.message.mentions.members)
    Utils.server.tell(Discord.getSelf())
    Utils.server.tell(event.message.mentions.members.contains(Discord.getMember(Discord.getSelf())))
    if (event.messageRaw.startsWith(Discord.selfMention)) event.reply("Hello there!")
    else Utils.server.tell(Text.of('[' + event.author.name + '] ').append(event.messageFormatted))
})
DiscordEvents.messageEdited(event => {
    event.reply("I saw you edit that!")
})

DiscordEvents.reactionAdded('1134711549240545280', event => {
    if (event.messageAuthor.isSelf())
        event.reply("C'mon, it wasn't *that* bad " + event.user.asMention + "!")
})
```

## For Contributors/Developers
Thank you for wanting to contribute! PRs are welcome, especially if its for a missing feature (there are a lot of those
at the moment). Note that testing should all be done on Fabric, as Forge doesn't allow mixining into libraries like 
JDA (I get around this in production by shadowing JDA and essentially self-mixining, but shadow doesnt apply in dev). 
