package com.github.lunawasflaggedagain.emojishortcodes;

import com.google.gson.Gson;
import net.fabricmc.api.ClientModInitializer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EmojiShortcodes implements ClientModInitializer {
    public static Map SHORTCODES;
    @Override
    public void onInitializeClient() {
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("shortcodes.json");
        if(inputStream == null) {
            throw new RuntimeException("could not load shortcodes json file");
        }


        final InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        EmojiShortcodes.SHORTCODES = new Gson().fromJson(reader, Map.class);

        if(EmojiShortcodes.SHORTCODES == null) {
            throw new RuntimeException("could not parse shortcodes json file");
        }
    }
}
