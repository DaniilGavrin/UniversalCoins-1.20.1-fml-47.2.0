package com.bytewizard.economy.block;

import com.bytewizard.economy.Main;
import com.bytewizard.economy.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);

    public static final RegistryObject<Block> TERMINAL = BLOCKS.register("terminal",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F)));

    public static final RegistryObject<Item> TERMINAL_ITEM = ModItems.ITEMS.register("terminal",
            () -> new BlockItem(ModBlocks.TERMINAL.get(), new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}