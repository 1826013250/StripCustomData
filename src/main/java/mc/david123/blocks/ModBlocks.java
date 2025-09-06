package mc.david123.blocks;

import mc.david123.StripCustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(StripCustomData.MODID);

    public static DeferredBlock<Block> CUSTOM_DATA_STRIP_MACHINE = BLOCKS.register(
            "custom_data_strip_machine",
            () -> new CustomDataStripMachine(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .strength(1.0F))
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
