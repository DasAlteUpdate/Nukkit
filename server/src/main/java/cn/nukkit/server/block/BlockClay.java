package cn.nukkit.server.block;

import cn.nukkit.server.item.Item;
import cn.nukkit.server.item.ItemClay;
import cn.nukkit.server.item.ItemTool;
import cn.nukkit.server.utils.BlockColor;

/**
 * @author Nukkit Project Team
 */
public class BlockClay extends BlockSolid {

    public BlockClay(int meta) {
        super(0);
    }

    public BlockClay() {
        this(0);
    }

    @Override
    public double getHardness() {
        return 0.6;
    }

    @Override
    public double getResistance() {
        return 3;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_SHOVEL;
    }

    @Override
    public int getId() {
        return CLAY_BLOCK;
    }

    @Override
    public String getName() {
        return "Clay Block";
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{
                new ItemClay(0, 4)
        };
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.CLAY_BLOCK_COLOR;
    }
}