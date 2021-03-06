package mariculture.core.blocks;

import java.util.List;
import java.util.Random;

import mariculture.api.fishery.Fishing;
import mariculture.core.blocks.base.BlockFunctional;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.WaterMeta;
import mariculture.fishery.Fish;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemFishy;
import mariculture.lib.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTicking extends BlockFunctional {
    public BlockTicking() {
        super(Material.cloth);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
        setTickRandomly(true);
    }

    @Override
    public String getToolType(int meta) {
        return null;
    }

    @Override
    public int getToolLevel(int meta) {
        return 0;
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        return 0.05F;
    }

    @Override
    public int getRenderType() {
        return RenderIds.RENDER_ALL;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        byte b0 = 0;
        float f = 0.0625F;
        return AxisAlignedBB.getBoundingBox((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) ((float) y + (float) b0 * f), (double) z + this.maxZ);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
        byte b0 = 0;
        float f = (float) (1 * (1 + b0)) / 16.0F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return null;
    }

    @Override
    public boolean doesDrop(int meta) {
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!BlockHelper.isFishable(world, x, y - 1, z)) {
            ItemHelper.spawnItem(world, x, y, z, new ItemStack(Fishery.net));
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public boolean destroyBlock(World world, int x, int y, int z) {
        ItemHelper.spawnItem(world, x, y, z, new ItemStack(Fishery.net));
        world.setBlockToAir(x, y, z);
        return false;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.rand.nextInt(MachineSpeeds.getNetSpeed()) == 0) {
            ItemStack loot = Fishing.fishing.getCatch(world, x, y, z, null, null);
            if (loot != null && loot.getItem() instanceof ItemFishy) {
                ItemHelper.spawnItem(world, x, y, z, loot, true, OreDictionary.WILDCARD_VALUE);
            } else {
                ItemHelper.spawnItem(world, x, y, z, new ItemStack(Items.fish, 1, Fish.cod.getID()), true, OreDictionary.WILDCARD_VALUE);
            }
        }
    }

    @Override
    public int getMetaCount() {
        return WaterMeta.COUNT;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return new ItemStack(Fishery.net, 1, 0);
    }

    @Override
    public boolean isActive(int meta) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creative, List list) {
        return;
    }
}