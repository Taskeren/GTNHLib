package com.gtnewhorizon.gtnhlib.chat.customclick;

import java.util.Map;

import net.minecraft.event.ClickEvent;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;

import com.google.gson.JsonElement;
import com.gtnewhorizon.gtnhlib.network.NetworkHandler;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public final class CustomClickAction {

    // created by mixin.
    @ApiStatus.Internal
    public static final ClickEvent.Action CUSTOM = ClickEvent.Action.valueOf("CUSTOM");

    private static final Logger LOG = LogManager.getLogger(CustomClickAction.class);
    private static final Map<ResourceLocation, CustomClickHandler> HANDLERS = new Object2ObjectOpenHashMap<>();

    public static ClickEvent newCustomClickAction(ResourceLocation handler, JsonElement data) {
        return new ClickEvent(CUSTOM, new CustomClickData(handler, data).encodeToString());
    }

    public static CustomClickHandler registerCustomClickActionHandler(ResourceLocation location,
            CustomClickHandler handler) {
        return HANDLERS.putIfAbsent(location, handler);
    }

    /// Send a packet with the value of the custom click action text.
    /// client only.
    @ApiStatus.Internal
    public static void sendClickPacket(String s) {
        NetworkHandler.instance.sendToServer(new C2SCustomClickPacket(CustomClickData.decodeFromString(s)));
    }

    /// Handle a packet from the client, parse and dispatch to the corresponding handler.
    /// server-only.
    @ApiStatus.Internal
    static void dispatchClickEvent(CustomClickData data, NetHandlerPlayServer netHandler) {
        CustomClickHandler handler = HANDLERS.get(data.id);
        if (handler == null) {
            LOG.warn("Unexpected custom click packet from client with missing handler: {}", data.id);
            return;
        }
        handler.handle(data.payload, netHandler.playerEntity);
    }

}
