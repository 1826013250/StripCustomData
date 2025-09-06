package mc.david123.screens;

import mc.david123.blocks.ModBlocks;
import mc.david123.blocks.entities.CustomDataStripMachineBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class CustomDataStripMachineMenu extends AbstractContainerMenu {
    private final CustomDataStripMachineBlockEntity blockEntity;
    private final Level level;

    public CustomDataStripMachineMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, playerInventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public CustomDataStripMachineMenu(int containerID, Inventory playerInventory, BlockEntity blockEntity) {
        super(ModMenus.CUSTOM_DATA_STRIP_MACHINE_MENU.get(), containerID);
        this.level = playerInventory.player.level();
        this.blockEntity = (CustomDataStripMachineBlockEntity) blockEntity;
        addHotBarSlots(playerInventory);
        addInventorySlots(playerInventory);
        addSlot(new SlotItemHandler(this.blockEntity.inventory, 0,54, 34));
        addSlot(new SlotItemHandler(this.blockEntity.inventory, 1, 104, 34));
    }

    private void addInventorySlots(Inventory inv) {
        for (int i = 1; i <= 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inv, i * 9 + j, 8 + j * 18, 84 + (i - 1) * 18));
            }
        }
    }

    private void addHotBarSlots(Inventory inv) {
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inv, i, 8 + 18 * i, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex) {
        ItemStack originalItemStack = ItemStack.EMPTY;
        Slot quickMovedSlot = slots.get(quickMovedSlotIndex);

        // If the target slot index is valid(no need check due to @Nonnull annotation) and has item
        if (quickMovedSlot.hasItem()) {
            ItemStack movedItemStack = quickMovedSlot.getItem();
            originalItemStack = movedItemStack.copy();

            if (quickMovedSlotIndex < getTotalInventorySlots()) {  // If the item is in the player's inventory
                // try moving the item into the target container
                if (!moveItemStackTo(movedItemStack, getTotalInventorySlots(), getTotalInventorySlots() + getTargetContainerSlots(), false)) {
                    return ItemStack.EMPTY;  // if failed, stop quick moving.
                }
                quickMovedSlot.setChanged();
            } else {  // If the item is in the target container
                if (!moveItemStackTo(movedItemStack, 0, getTotalInventorySlots(), false)) {
                    return ItemStack.EMPTY;  // if failed, stop quic moving.
                }
            }

            if (movedItemStack.isEmpty()) {
                quickMovedSlot.set(ItemStack.EMPTY);  // clear the original slot if all the item have been moved
            } else {
                quickMovedSlot.setChanged();  // otherwise notify the slot to change the count of item.
            }

            // Container Menu things
            if (movedItemStack.getCount() == originalItemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            quickMovedSlot.onTake(player, movedItemStack);

        }
        return originalItemStack;
    }

    private final int HOTBAR_SLOTS = 9;
    private final int INVENTORY_SLOTS = 9 * 3;
    private final int EXTRA_INVENTORY_SLOTS = 0;

    private int getTotalInventorySlots() {
        return HOTBAR_SLOTS + INVENTORY_SLOTS + EXTRA_INVENTORY_SLOTS;
    }

    private int getTargetContainerSlots() {
        return 2;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.CUSTOM_DATA_STRIP_MACHINE.get());
    }
}
