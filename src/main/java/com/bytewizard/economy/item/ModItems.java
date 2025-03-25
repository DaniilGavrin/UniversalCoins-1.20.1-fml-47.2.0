package com.bytewizard.economy.item;

import com.bytewizard.economy.Main; // Импорт EconomyMod
import net.minecraft.world.item.Item; // Импорт Item
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    // Указываем тип RegistryObject<Item> вместо конкретного BankCardItem
    public static final RegistryObject<Item> BANK_CARD = ITEMS.register("bank_card",
            () -> new BankCardItem(new Item.Properties().stacksTo(1))); // Это у вас уже есть

    public static final RegistryObject<Item> MONEY1 = ITEMS.register("MONEY 1$",
            () -> new Money1Item( new Item.Properties().stacksTo(1)));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}