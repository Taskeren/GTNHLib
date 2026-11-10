package com.gtnewhorizon.gtnhlib.chat.customclick;

import java.io.IOException;

import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;

import org.jetbrains.annotations.ApiStatus;

import com.gtnewhorizon.gtnhlib.network.base.IPacket;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@ApiStatus.Internal
@NoArgsConstructor
@AllArgsConstructor
public class C2SCustomClickPacket implements IPacket {

    private CustomClickData data;

    @Override
    public void encode(PacketBuffer buf) throws IOException {
        data.encode(buf);
    }

    @Override
    public void decode(PacketBuffer buf) throws IOException {
        data = CustomClickData.decode(buf);
    }

    @Override
    public IPacket executeServer(NetHandlerPlayServer handler) {
        CustomClickAction.dispatchClickEvent(data, handler);
        return null;
    }
}
