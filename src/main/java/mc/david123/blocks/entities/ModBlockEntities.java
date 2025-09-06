package mc.david123.blocks.entities;

import mc.david123.StripCustomData;
import mc.david123.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
            Registries.BLOCK_ENTITY_TYPE, StripCustomData.MODID
    );

    public static Supplier<BlockEntityType<CustomDataStripMachineBlockEntity>> CUSTOM_DATA_STRIP_MACHINE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("custom_data_strip_machine", () -> BlockEntityType.Builder.of(
                    CustomDataStripMachineBlockEntity::new,
                    ModBlocks.CUSTOM_DATA_STRIP_MACHINE.get()
            ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
