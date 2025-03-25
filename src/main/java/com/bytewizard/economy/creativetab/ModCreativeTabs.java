package com.bytewizard.economy.creativetab;

import com.bytewizard.economy.Main;
import com.bytewizard.economy.block.ModBlocks;
import com.bytewizard.economy.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);

    public static final RegistryObject<CreativeModeTab> ECONOMY_TAB = TABS.register("economy_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.BANK_CARD.get()))
                    .title(Component.translatable("itemGroup.economymod"))
                    .displayItems((params, output) -> {
                        output.accept(new ItemStack(ModItems.BANK_CARD.get(), 1)); // Размер стека 1
                        output.accept(new ItemStack(ModBlocks.TERMINAL_ITEM.get(), 1)); // И здесь
                        output.accept(new ItemStack(ModItems.MONEY1.get(), 1));
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}