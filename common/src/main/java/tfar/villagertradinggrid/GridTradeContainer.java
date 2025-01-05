package tfar.villagertradinggrid;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public class GridTradeContainer implements Container {

    private int futureXp;
    private final Merchant merchant;
    private final int size;
    private final NonNullList<ItemStack> items;
    public GridTradeContainer(int slots, Merchant merchant) {
        this.size = slots;
        this.items = NonNullList.withSize(slots, ItemStack.EMPTY);
        this.merchant = merchant;
        MerchantOffers offers = merchant.getOffers();
        int i = 0;
        for (MerchantOffer offer : offers) {
            ItemStack copy = offer.getResult().copy();
            setItem(i++,copy);
            if (i >= slots) break;
        }
    }


    @Override
    public int getContainerSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot >= 0 && slot < this.items.size() ? this.items.get(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int i1) {
        ItemStack copy = getItem(slot).copy();
        return copy;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return false;
    }


    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = this.items.get(slot);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.items.set(slot, ItemStack.EMPTY);
            return stack;
        }
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.items.set(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return merchant.getTradingPlayer() == player;
    }

    @Override
    public boolean canTakeItem(Container container, int slot, ItemStack $$2) {
        return canMakeTrade(slot);
    }

    boolean canMakeTrade(int slot) {
        return true;
    }

    @Override
    public void clearContent() {

    }

    public int getFutureXp() {
        return this.futureXp;
    }

}
