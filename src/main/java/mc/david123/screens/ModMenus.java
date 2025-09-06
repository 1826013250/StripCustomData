package mc.david123.screens;

import mc.david123.StripCustomData;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MOD_MENUS = DeferredRegister.create(Registries.MENU, StripCustomData.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<CustomDataStripMachineMenu>> CUSTOM_DATA_STRIP_MACHINE_MENU =
            MOD_MENUS.register("custom_data_strip_machine",
                    () -> IMenuTypeExtension.create(CustomDataStripMachineMenu::new));

    public static void register(IEventBus eventBus) {
        MOD_MENUS.register(eventBus);
    }
}
