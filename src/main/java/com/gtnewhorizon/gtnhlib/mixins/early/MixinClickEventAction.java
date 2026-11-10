package com.gtnewhorizon.gtnhlib.mixins.early;

import java.util.ArrayList;

import net.minecraft.event.ClickEvent;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.google.common.collect.Lists;

@Mixin(ClickEvent.Action.class)
public class MixinClickEventAction {

    @Shadow
    @Final
    @Mutable
    private static ClickEvent.Action[] $VALUES;

    @Invoker("<init>")
    private static ClickEvent.Action gtnhlib$create(String name, int ordinal, String canonicalName,
            boolean allowedInChat) {
        throw new AssertionError("Implemented via Mixin");
    }

    @Unique
    private static final ClickEvent.Action GTNHLIB$CUSTOM = gtnhlib$create("CUSTOM", "custom", true);

    @Unique
    private static ClickEvent.Action gtnhlib$create(String enumName, String canonicalName, boolean allowedInChat) {
        assert $VALUES != null;
        ArrayList<ClickEvent.Action> values = Lists.newArrayList($VALUES);
        ClickEvent.Action newValue = gtnhlib$create(enumName, values.size(), canonicalName, allowedInChat);
        values.add(newValue);
        $VALUES = values.toArray(new ClickEvent.Action[0]);
        return newValue;
    }

}
