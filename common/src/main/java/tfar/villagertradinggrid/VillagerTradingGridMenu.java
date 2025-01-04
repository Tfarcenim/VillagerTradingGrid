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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.Merchant;
import tfar.villagertradinggrid.platform.Services;

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

        for(int k = 0; k < 4; ++k) {
            final EquipmentSlot equipmentslot = SLOT_IDS[k];
            this.addSlot(new Slot(inventory, 39 - k, 8, 8 + k * 18) {
                @Override
                public void setByPlayer(ItemStack stack) {
                    InventoryMenu.onEquipItem(inventory.player, equipmentslot, stack, this.getItem());
                    super.setByPlayer(stack);
                }

                /**
                 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in
                 * the case of armor slots)
                 */
                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                /**
                 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
                 */
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return Services.PLATFORM.canEquip(stack,equipmentslot, inventory.player);
                }

                /**
                 * Return whether this slot's stack can be taken from this slot.
                 */
                @Override
                public boolean mayPickup(Player p_39744_) {
                    ItemStack itemstack = this.getItem();
                    return (itemstack.isEmpty() || p_39744_.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.mayPickup(p_39744_);
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.TEXTURE_EMPTY_SLOTS[equipmentslot.getIndex()]);
                }
            });
        }
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
}
