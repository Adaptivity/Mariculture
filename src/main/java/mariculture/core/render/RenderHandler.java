package mariculture.core.render;

import java.util.HashMap;

import mariculture.core.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderHandler implements ISimpleBlockRenderingHandler {
    private static final HashMap<String, RenderBase> renders = new HashMap();

    public static void register(Block block, int meta, Class clazz) {
        try {
            renders.put(Block.blockRegistry.getNameForObject(block) + ":" + meta, (RenderBase) clazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer) {
        String data = Block.blockRegistry.getNameForObject(block) + ":" + meta;
        if (renders.get(data) != null) {
            renders.get(data).render(renderer, block, meta);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        String data = Block.blockRegistry.getNameForObject(block) + ":" + world.getBlockMetadata(x, y, z);
        if (renders.get(data) != null) return renders.get(data).render(renderer, world, x, y, z);
        else return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return RenderIds.RENDER_ALL;
    }
}
