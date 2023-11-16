package chiefarug.mods.discord4kjs.mixin.jda;

import dev.latvian.mods.rhino.util.HideFromJS;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.utils.AttachmentProxy;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@Mixin(remap = false, value = Message.Attachment.class)
public abstract class Attachment_HideFileDownloadingMixin {
	@Shadow @HideFromJS public abstract CompletableFuture<File> downloadToFile();

	@Shadow @HideFromJS public abstract CompletableFuture<File> downloadToFile(String path);

	@Shadow @HideFromJS public abstract CompletableFuture<File> downloadToFile(File file);

	@Shadow @HideFromJS public abstract AttachmentProxy getProxy();

	@Shadow @HideFromJS public abstract CompletableFuture<Icon> retrieveAsIcon();

	@Shadow @HideFromJS public abstract CompletableFuture<InputStream> retrieveInputStream();

}
