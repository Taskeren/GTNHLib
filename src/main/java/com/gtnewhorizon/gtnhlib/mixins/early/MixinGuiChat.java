package com.gtnewhorizon.gtnhlib.mixins.early;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.event.ClickEvent;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.gtnewhorizon.gtnhlib.chat.customclick.CustomClickAction;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;

@Mixin(GuiChat.class)
public class MixinGuiChat {

    @WrapOperation(
            method = "mouseClicked",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;)V",
                    ordinal = 1))
    private void gtnhlib$handleCustomAction(Logger instance, String s, Operation<Void> original,
            @Local(name = "clickevent") ClickEvent clickEvent) {
        if (clickEvent.getAction() == CustomClickAction.CUSTOM) {
            // send back the value to the server.
            CustomClickAction.sendClickPacket(clickEvent.getValue());
            return;
        }
        original.call(instance, s);
    }

}
