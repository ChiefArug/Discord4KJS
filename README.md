# Discord4KJS
Discord4KJS uses [JDA (Java Discord API)](https://github.com/discord-jda/JDA) to allow creation of a Discord bot in [KubeJS](https://kubejs.com/).
This allows effortless creation of simple things such as a Minecraft-Discord chat relay, and janky creation of advanced
things like full moderation/logger/fun bots (this is not a reccomended usecase though).


## For Scripters
There isn't a wiki yet. Sorry. There also aren't examples yet. A fair amount of stuff is documented through @Info
annotations, so I highly reccomend installing [ProbeJS](https://www.curseforge.com/minecraft/mc-mods/probejs) to be able to see those.

## For Contributors/Developers
Thank you for wanting to contribute! PRs are welcome, especially if its for a missing feature (there are a lot of those
at the moment). Note that testing should all be done on Fabric, as Forge doesn't allow mixining into libraries like 
JDA (I get around this in production by shadowing JDA and essentially self-mixining, but shadow doesnt apply in dev). 