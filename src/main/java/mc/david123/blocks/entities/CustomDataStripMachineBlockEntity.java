package mc.david123.blocks.entities;

import mc.david123.screens.CustomDataStripMachineMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class CustomDataStripMachineBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            super.onContentsChanged(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch (slot) {
                case 0 -> true;
                case 1 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == INPUT_SLOT) return ItemStack.EMPTY;
            return super.extractItem(slot, amount, simulate);
        }
    };
    private final int INPUT_SLOT = 0;
    private final int OUTPUT_SLOT = 1;

    public void tick(Level level, BlockPos pos, BlockState state) {
        ItemStack sourceItem = inventory.getStackInSlot(INPUT_SLOT);
        ItemStack destItem = inventory.getStackInSlot(OUTPUT_SLOT);
        if (!sourceItem.isEmpty() && (destItem.getCount() < destItem.getMaxStackSize())) {
            ItemStack modifiedItem = sourceItem.copy();
            modifiedItem.set(DataComponents.CUSTOM_DATA, null);
            if (destItem.isEmpty()) {
                inventory.setStackInSlot(OUTPUT_SLOT, modifiedItem);
                inventory.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
            } else if (modifiedItem.getItem().equals(destItem.getItem())) {
                int maxStackSize = destItem.getMaxStackSize();
                sourceItem.shrink(maxStackSize - destItem.getCount());
                destItem.shrink(-(maxStackSize - destItem.getCount()));
            }
            setChanged(level, pos, state);
        }
    }

    public CustomDataStripMachineBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CUSTOM_DATA_STRIP_MACHINE_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Custom Data Strip Machine");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CustomDataStripMachineMenu(i, inventory, this);
    }
}
