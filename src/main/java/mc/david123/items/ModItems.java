package mc.david123.items;

import mc.david123.StripCustomData;
import mc.david123.blocks.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(StripCustomData.MODID);

    public static DeferredItem<Item> CUSTOM_DATA_STRIP_MACHINE = ITEMS.register(
            "custom_data_strip_machine",
            () -> new BlockItem(ModBlocks.CUSTOM_DATA_STRIP_MACHINE.get(), new Item.Properties())
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
