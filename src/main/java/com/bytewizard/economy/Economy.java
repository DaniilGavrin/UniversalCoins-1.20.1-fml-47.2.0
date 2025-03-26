package com.bytewizard.economy;

import com.bytewizard.economy.block.*;
import com.bytewizard.economy.command.*;
import com.bytewizard.economy.config.UCConfig;
import com.bytewizard.economy.item.*;
import com.bytewizard.economy.net.PacketHandler;
import com.bytewizard.economy.render.*;
import com.bytewizard.economy.tileentity.*;
import com.bytewizard.economy.worldgen.UCVillagePools;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;

@Mod(economy.MODID)
public class economy {
    public static final String MODID = "economy";

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Регистрация блоков
    public static final RegistryObject<Block> TRADE_STATION = BLOCKS.register("tradestation", BlockTradeStation::new);
    public static final RegistryObject<Block> SAFE = BLOCKS.register("safe", BlockSafe::new);
    public static final RegistryObject<Block> SIGNAL_BLOCK = BLOCKS.register("signalblock", BlockSignal::new);
    public static final RegistryObject<Block> VENDOR_BLOCK = BLOCKS.register("vendor_block", BlockVendor::new);
    public static final RegistryObject<Block> VENDOR_FRAME = BLOCKS.register("vendor_frame", BlockVendorFrame::new);
    public static final RegistryObject<Block> PACKAGER = BLOCKS.register("packager", BlockPackager::new);
    public static final RegistryObject<Block> POWER_TRANSMITTER = BLOCKS.register("power_transmitter", BlockPowerTransmitter::new);
    public static final RegistryObject<Block> POWER_RECEIVER = BLOCKS.register("power_receiver", BlockPowerReceiver::new);
    public static final RegistryObject<Block> ATM = BLOCKS.register("atm", BlockATM::new);

    // Регистрация предметов
    public static final RegistryObject<Item> IRON_COIN = ITEMS.register("iron_coin", () -> new ItemCoin(UCConfig.coinValues.get().get(0)));
    public static final RegistryObject<Item> GOLD_COIN = ITEMS.register("gold_coin", () -> new ItemCoin(UCConfig.coinValues.get().get(1)));
    public static final RegistryObject<Item> EMERALD_COIN = ITEMS.register("emerald_coin", () -> new ItemCoin(UCConfig.coinValues.get().get(2)));
    public static final RegistryObject<Item> DIAMOND_COIN = ITEMS.register("diamond_coin", () -> new ItemCoin(UCConfig.coinValues.get().get(3)));
    public static final RegistryObject<Item> OBSIDIAN_COIN = ITEMS.register("obsidian_coin", () -> new ItemCoin(UCConfig.coinValues.get().get(4)));
    public static final RegistryObject<Item> UC_CARD = ITEMS.register("uc_card", UCCardItem::new);
    public static final RegistryObject<Item> VENDOR_WRENCH = ITEMS.register("vendor_wrench", VendorWrenchItem::new);

    // Регистрация BlockEntity
    public static final RegistryObject<BlockEntityType<TileTradeStation>> TRADE_STATION_BE = BLOCK_ENTITIES.register("tradestation",
            () -> BlockEntityType.Builder.of(TileTradeStation::new, TRADE_STATION.get()).build(null));

    // Creative Tab
    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_TABS.register("main", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.universalcoins"))
                    .icon(() -> new ItemStack(IRON_COIN.get()))
                    .build());

    public UniversalCoins() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Регистрация всех компонентов
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);

        // Конфигурация
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UCConfig.SPEC);

        // Обработчики событий
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Инициализация сети
        PacketHandler.register();

        // Генерация мира
        event.enqueueWork(() -> {
            UCVillagePools.registerVillageStructures();
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Регистрация рендереров
        event.enqueueWork(() -> {
            ClientRegistry.bindTileEntityRenderer(TRADE_STATION_BE.get(), TradeStationRenderer::new);
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == MAIN_TAB.getKey()) {
            event.accept(IRON_COIN.get());
            event.accept(TRADE_STATION.get());
            // Добавьте остальные предметы и блоки
        }
    }

    private void onServerStarting(ServerStartingEvent event) {
        // Регистрация команд
        UCCommand.register(event.getServer().getCommands().getDispatcher());
    }

    // Базовый класс для TileEntity (пример)
    public static class TileTradeStation extends BaseContainerBlockEntity {
        public TileTradeStation(BlockPos pos, BlockState state) {
            super(TRADE_STATION_BE.get(), pos, state);
        }

        // Реализация методов контейнера...
    }
}