package com.github.lunawasflaggedagain.emojishortcodes.mixin;

import com.github.lunawasflaggedagain.emojishortcodes.EmojiShortcodes;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    private static final Pattern SHORTCODE_PATTERN = Pattern.compile("(?:^|[^\\\\])(:[^:\\s]+:)");


    @ModifyArg(method = "sendMessage(Ljava/lang/String;Z)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendChatMessage(Ljava/lang/String;)V"), index = 0)
    private String sendMessage(String message) {
        final Matcher m = SHORTCODE_PATTERN.matcher(message);

        while(m.find()) {
            final String shortcode = m.group(1);
            final String emoji = (String) EmojiShortcodes.SHORTCODES.get(shortcode);
            if(emoji == null) {
                continue;
            }

            message = message.replace(shortcode, emoji);
        }

        return message.replaceAll("\\\\:", ":");
    }
}
