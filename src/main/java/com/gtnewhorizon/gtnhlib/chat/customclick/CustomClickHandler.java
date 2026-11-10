package com.gtnewhorizon.gtnhlib.chat.customclick;

import net.minecraft.entity.player.EntityPlayerMP;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonElement;

@FunctionalInterface
public interface CustomClickHandler {

    void handle(@Nullable JsonElement payload, EntityPlayerMP player);

}
