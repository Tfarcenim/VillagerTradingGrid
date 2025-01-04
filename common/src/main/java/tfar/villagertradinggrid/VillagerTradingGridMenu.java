package tfar.villagertradinggrid;

import net.minecraft.world.entity.npc.ClientSideMerchant;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;

public class VillagerTradingGridMenu extends AbstractContainerMenu {
    private final Merchant merchant;

    protected VillagerTradingGridMenu(int id, Inventory inventory, Merchant merchant) {
        super(Init.VILLAGER_TRADING_GRID, id);
        this.merchant = merchant;
        int i;
        for(i = 0; i < 3; ++i) {
            for(int $$4 = 0; $$4 < 9; ++$$4) {
                this.addSlot(new Slot(inventory, $$4 + i * 9 + 9, 108 + $$4 * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 108 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    public static VillagerTradingGridMenu client(int i, Inventory inventory) {
        return new VillagerTradingGridMenu(i,inventory,new ClientSideMerchant(inventory.player));
    }
}
