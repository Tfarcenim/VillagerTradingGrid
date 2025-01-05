package tfar.villagertradinggrid;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.npc.ClientSideMerchant;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffers;

public class VillagerTradingGridMenu extends AbstractContainerMenu {
    private final Merchant merchant;

    public static final int ROWS = 8;
    public static final int COL = 5;

    protected final GridTradeContainer tradeContainer;
    private int merchantLevel;
    private boolean showProgressBar;
    private boolean canRestock;

    //from InventoryMenu
    private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};


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

        this.addSlot(new EquipmentInventorySlot(inventory, 39, 152, 18,inventory.player,SLOT_IDS[0]));//head
        this.addSlot(new EquipmentInventorySlot(inventory, 39 - 1, 152, 45,inventory.player,SLOT_IDS[1]));

        this.addSlot(new EquipmentInventorySlot(inventory, 39 - 2, 206, 18,inventory.player,SLOT_IDS[2]));
        this.addSlot(new EquipmentInventorySlot(inventory, 39 - 3, 206, 45,inventory.player,SLOT_IDS[3]));//feet


        this.addSlot(new Slot(inventory, 40, 133, 32) {
            public void setByPlayer(ItemStack stack) {
                InventoryMenu.onEquipItem(inventory.player, EquipmentSlot.OFFHAND, stack, this.getItem());
                super.setByPlayer(stack);
            }

            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });

    }

    public int getTraderXp() {
        return this.merchant.getVillagerXp();
    }

    public int getFutureTraderXp() {
        return this.tradeContainer.getFutureXp();
    }

    public void setXp(int pXp) {
        this.merchant.overrideXp(pXp);
    }

    public int getTraderLevel() {
        return this.merchantLevel;
    }

    public void setMerchantLevel(int pLevel) {
        this.merchantLevel = pLevel;
    }

    public void setCanRestock(boolean pCanRestock) {
        this.canRestock = pCanRestock;
    }

    public boolean canRestock() {
        return this.canRestock;
    }

    public void setShowProgressBar(boolean pShowProgressBar) {
        this.showProgressBar = pShowProgressBar;
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

    public boolean showProgressBar() {
        return this.showProgressBar;
    }

    public void setClientsideOffers(MerchantOffers merchantOffers) {
        this.merchant.overrideOffers(merchantOffers);
    }
}
