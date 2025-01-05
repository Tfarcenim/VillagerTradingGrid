package tfar.villagertradinggrid;

import net.minecraft.core.NonNullList;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;

public class TradeSlot extends Slot {
    private final Player player;
    private final Merchant merchant;
    private final VillagerTradingGridMenu menu;
    private int removeCount;

    public TradeSlot(Container pContainer, int pSlot, int pX, int pY, Player player, Merchant merchant, VillagerTradingGridMenu menu) {
        super(pContainer, pSlot, pX, pY);
        this.player = player;
        this.merchant = merchant;
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return container.canPlaceItem(getContainerSlot(), stack);
    }

    @Override
    public ItemStack remove(int pAmount) {
        if (this.hasItem()) {
            this.removeCount += Math.min(pAmount, this.getItem().getCount());
        }

        return super.remove(pAmount);
    }

    /**
     * Typically increases an internal count, then calls {@code onCrafting(item)}.
     *
     * @param pStack the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    @Override
    protected void onQuickCraft(ItemStack pStack, int pAmount) {
        this.removeCount += pAmount;
        this.checkTakeAchievements(pStack);
    }

    /**
     * @param pStack the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    @Override
    protected void checkTakeAchievements(ItemStack pStack) {
        pStack.onCraftedBy(this.player.level(), this.player, this.removeCount);
        this.removeCount = 0;
    }

    @Override
    public boolean mayPickup(Player player) {
        MerchantOffer offer = getBoundOffer();
        return !offer.isOutOfStock() && hasSufficientItems(player, offer);
    }

    public boolean hasSufficientItems(Player player, MerchantOffer offer) {
        boolean hasStackA = false;
        boolean hasStackB = offer.getCostB().isEmpty();
        NonNullList<ItemStack> items = player.getInventory().items;
        int skip = -1;
        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);
            if (ItemStack.isSameItem(stack, offer.getCostA())) {
                if (stack.getCount() >= offer.getCostA().getCount()) {
                    hasStackA = true;
                    skip = i;
                    break;
                }
            }
        }

        if (!hasStackB) {
            for (int i = 0; i < items.size(); i++) {
                if (i == skip) continue;
                ItemStack stack = items.get(i);
                if (ItemStack.isSameItem(stack, offer.getCostB()) && stack.getCount() >= offer.getCostB().getCount()) {
                    hasStackB = true;
                    break;
                }
            }
        }

        return hasStackA && hasStackB;
    }

    @Nullable
    public MerchantOffer getBoundOffer() {
        int id = getContainerSlot() + menu.rowOffset * VillagerTradingGridMenu.COL;
        return id < menu.getOffers().size() ? menu.getOffers().get(id) : null;
    }

    @Override
    public void onTake(Player pPlayer, ItemStack pStack) {
        this.checkTakeAchievements(pStack);
        MerchantOffer merchantOffer = getBoundOffer();
        if (merchantOffer != null) {

            ItemStack findStackA = ItemStack.EMPTY;
            int slotA = -1;
            ItemStack findStackB = ItemStack.EMPTY;
            int slotB = -1;

            NonNullList<ItemStack> items = player.getInventory().items;
            int skip = -1;
            for (int i = 0; i < items.size(); i++) {
                ItemStack stack = items.get(i);
                if (ItemStack.isSameItem(stack, merchantOffer.getCostA()) && stack.getCount() >= merchantOffer.getCostA().getCount()) {
                    findStackA = stack;
                    skip = i;
                    slotA = i;
                    break;
                }
            }

            if (!merchantOffer.getCostB().isEmpty()) {
                for (int i = 0; i < items.size(); i++) {
                    if (i == skip) continue;
                    ItemStack stack = items.get(i);
                    if (ItemStack.isSameItem(stack, merchantOffer.getCostB()) && stack.getCount() >= merchantOffer.getCostB().getCount()) {
                        findStackB = stack;
                        slotB  = i;
                        break;
                    }
                }
            }

            if (merchantOffer.take(findStackA,findStackB) || merchantOffer.take(findStackB,findStackA)) {
                merchant.notifyTrade(merchantOffer);
                pPlayer.awardStat(Stats.TRADED_WITH_VILLAGER);
                if (slotA != -1) {
                    player.getInventory().items.set(slotA, findStackA);
                }
                if (slotB != -1) {
                    player.getInventory().items.set(slotB, findStackB);
                }

            }
            this.merchant.overrideXp(this.merchant.getVillagerXp() + merchantOffer.getXp());
        }
    }
}
