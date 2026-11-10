package com.gtnewhorizon.gtnhlib.chat.customclick;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import cpw.mods.fml.common.network.ByteBufUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomClickData {

    private static final Gson gson = new Gson();

    public final ResourceLocation id;
    public final @Nullable JsonElement payload;

    public String encodeToString() {
        return gson.toJson(this);
    }

    void encode(PacketBuffer buf) {
        ByteBufUtils.writeUTF8String(buf, encodeToString());
    }

    public static CustomClickData decodeFromString(String s) {
        return gson.fromJson(s, CustomClickData.class);
    }

    static CustomClickData decode(PacketBuffer buf) {
        String s = ByteBufUtils.readUTF8String(buf);
        return gson.fromJson(s, CustomClickData.class);
    }
}
