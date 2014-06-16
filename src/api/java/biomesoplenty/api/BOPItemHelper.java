package biomesoplenty.api;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;

public class BOPItemHelper
{
    public static ToolMaterial  toolMaterialMud;
    public static ArmorMaterial armorMaterialMud;
    
    public static ToolMaterial  toolMaterialAmethyst;
    public static ArmorMaterial armorMaterialAmethyst;
    
    public static ArmorMaterial armorMaterialUnprotective;
    
    public static Item get(String name)
    {
        return GameRegistry.findItem("BiomesOPlenty", name);
    }
    
    public static String getUniqueName(Item item)
    {
        return GameData.itemRegistry.getNameForObject(item);
    }
}
