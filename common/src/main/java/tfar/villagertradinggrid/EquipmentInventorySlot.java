package tfar.villagertradinggrid;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import tfar.villagertradinggrid.platform.Services;

public class EquipmentInventorySlot extends Slot {
    private final Player player;
    private final EquipmentSlot equipmentslot;

    public EquipmentInventorySlot(Container pContainer, int pSlot, int pX, int pY, Player player, EquipmentSlot equipmentslot) {
        super(pContainer, pSlot, pX, pY);
        this.player = player;
        this.equipmentslot = equipmentslot;
    }

    @Override
    public void setByPlayer(ItemStack stack) {
        InventoryMenu.onEquipItem(player, equipmentslot, stack, this.getItem());
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
        return Services.PLATFORM.canEquip(stack,equipmentslot, player);
    }

    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    @Override
    public boolean mayPickup(Player player) {
        ItemStack itemstack = this.getItem();
        return (itemstack.isEmpty() || player.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.mayPickup(player);
    }

    @Override
    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.TEXTURE_EMPTY_SLOTS[equipmentslot.getIndex()]);
    }
}
