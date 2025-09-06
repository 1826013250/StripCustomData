package mc.david123.blocks;

import com.mojang.serialization.MapCodec;
import mc.david123.blocks.entities.CustomDataStripMachineBlockEntity;
import mc.david123.blocks.entities.ModBlockEntities;
import mc.david123.screens.CustomDataStripMachineMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class CustomDataStripMachine extends BaseEntityBlock {

    protected CustomDataStripMachine(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof CustomDataStripMachineBlockEntity blockEntity) {
            if (!level.isClientSide) {
                player.openMenu(new SimpleMenuProvider(blockEntity, Component.translatable("block.stripcustomdata.custom_data_strip_machine")), pos);
            }
        }
        return ItemInteractionResult.sidedSuccess(true);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (level.getBlockEntity(pos) instanceof CustomDataStripMachineBlockEntity blockEntity) {
            SimpleContainer tempContainer = new SimpleContainer(2);
            for (int i = 0; i < blockEntity.inventory.getSlots(); i++) {
                tempContainer.addItem(blockEntity.inventory.getStackInSlot(i));
            }
            Containers.dropContents(level, pos, tempContainer);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.CUSTOM_DATA_STRIP_MACHINE_BLOCK_ENTITY.get(),
                ((level1, blockPos, blockState, blockEntity) -> blockEntity.tick(level1, blockPos, blockState)));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(CustomDataStripMachine::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CustomDataStripMachineBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
