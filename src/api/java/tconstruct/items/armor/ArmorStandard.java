package tconstruct.items.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import tconstruct.common.TRepo;
import tconstruct.library.armor.ArmorCore;
import tconstruct.library.armor.EnumArmorPart;

public class ArmorStandard extends ArmorCore
{

    public ArmorStandard(int baseProtection, EnumArmorPart armorPart)
    {
        super(baseProtection, armorPart);
    }

    @Override
    public void registerIcons (IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("tinker:armor/wood_helmet");
    }

    @Override
    public String getArmorTexture (ItemStack stack, Entity entity, int slot, String layer)
    {
        int suffix = this.armorType == 2 ? 2 : 1;
        return "tinker:textures/armor/wood_" + suffix + ".png";
    }

}