package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.blocks.base.BlockFunctionalMulti;
import mariculture.core.events.MaricultureEvents;
import mariculture.core.lib.MachineMultiMeta;
import mariculture.core.lib.RockMeta;
import mariculture.core.tile.TileCrucible;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachineMulti extends BlockFunctionalMulti {
    private IIcon[] crucibleIcons;

    public BlockMachineMulti() {
        super(Material.piston);
    }

    @Override
    public String getToolType(int meta) {
        return MaricultureEvents.getToolType(this, meta, "pickaxe");
    }

    @Override
    public int getToolLevel(int meta) {
        return MaricultureEvents.getToolLevel(this, meta, 2);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        float hardness = 5F;
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case MachineMultiMeta.CRUCIBLE:
                hardness = 6F;
            case MachineMultiMeta.INCUBATOR_BASE:
                hardness = 10F;
        }

        return MaricultureEvents.getBlockHardness(this, meta, hardness);
    }
    
    @Override
    public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
    	int meta = world.getBlockMetadata(x, y, z);
    	return meta == MachineMultiMeta.CRUCIBLE? 50F: super.getExplosionResistance(entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        IIcon icon = null;
        icon = side < 2 && meta == MachineMultiMeta.CRUCIBLE ? Core.rocks.getIcon(side, RockMeta.BASE_BRICK) : super.getIcon(side, meta);
        return MaricultureEvents.getInventoryIcon(this, meta, side, icon);
    }

    @Override
    public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
        IIcon icon = null;
        if (side > 1) {
            TileEntity tile = block.getTileEntity(x, y, z);
            if (tile instanceof TileCrucible) {
                TileCrucible crucible = (TileCrucible) tile;
                if (crucible.master == null) icon = getIcon(side, MachineMultiMeta.CRUCIBLE);
                else if (crucible.isMaster()) icon = crucibleIcons[1];
                else return crucibleIcons[0];
            }
        }

        if (icon == null) {
            icon = super.getIcon(block, x, y, z, side);
        }

        return MaricultureEvents.getWorldIcon(this, side, icon, block, x, y, z);
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        if(meta == MachineMultiMeta.CRUCIBLE) return new TileCrucible();
        else return MaricultureEvents.getTileEntity(this, meta, null);
    }

    @Override
    public boolean isActive(int meta) {
        if(meta == MachineMultiMeta.CRUCIBLE) return true;
        else return MaricultureEvents.isActive(this, meta, false);
    }

    @Override
    public boolean isValidTab(CreativeTabs tab, int meta) {
        boolean isValid = tab == MaricultureTab.tabFishery;
        if (meta == MachineMultiMeta.CRUCIBLE) {
            isValid = tab == MaricultureTab.tabFactory;
        }

        return MaricultureEvents.isValidTab(this, tab, meta, isValid);
    }

    @Override
    public int getMetaCount() {
        return MachineMultiMeta.COUNT;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        String name = prefix != null ? prefix : "";
        icons = new IIcon[getMetaCount()];

        for (int i = 0; i < icons.length; i++) {
            icons[i] = iconRegister.registerIcon(MaricultureEvents.getMod(this, i, "mariculture") + ":" + name + getName(i));
        }
        
        crucibleIcons = new IIcon[2];
        crucibleIcons[0] = iconRegister.registerIcon(Mariculture.modid + ":crucibleTop");
        crucibleIcons[1] = iconRegister.registerIcon(Mariculture.modid + ":crucibleBottom");
    }
}
