package tfar.villagertradinggrid;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.ClientSideMerchant;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;

public class VillagerTradingGridMenu extends AbstractContainerMenu {
    private final Merchant merchant;

    public static final int ROWS = 8;
    public static final int COL = 5;

    protected final Container tradeContainer;

    protected VillagerTradingGridMenu(int id, Inventory inventory, Merchant merchant) {
        super(Init.VILLAGER_TRADING_GRID, id);
        this.merchant = merchant;

        tradeContainer = new GridTradeContainer(ROWS * COL,merchant);

        for (int y = 0; y < ROWS;y++) {
           for (int x = 0; x < COL;x++) {
               int i = x + y * COL;
               addSlot(new Slot(tradeContainer,i,x * 18 + 5,y * 18 + 18));
           }
        }

        int i;
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 108 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 108 + i * 18, 142));
        }

        merchant.setTradingPlayer(inventory.player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return merchant.getTradingPlayer() == player;
    }

    public static VillagerTradingGridMenu client(int i, Inventory inventory) {
        return new VillagerTradingGridMenu(i,inventory,new ClientSideMerchant(inventory.player));
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        merchant.setTradingPlayer(null);
    }
}
