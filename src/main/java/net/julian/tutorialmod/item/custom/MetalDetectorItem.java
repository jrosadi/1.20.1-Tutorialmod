package net.julian.tutorialmod.item.custom;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

//end class name with item
//control h item to view children of item
public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        //if serverside
        if(!pContext.getLevel().isClientSide()){
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundblock = false;

            //makes the for go to y = -64
            for (int i = 0; i <= positionClicked.getY()+64; i++) {
                //get blocks
                //below subtracts y vector by i
                BlockState state = pContext.getLevel().getBlockState(positionClicked.below(i));

                if(isValueableBlock(state)){
                    outputValuableCoordinates(positionClicked.below(i),player, state.getBlock());
                    foundblock = true;
                    break;
                }
            }

            if(!foundblock){
                player.sendSystemMessage(Component.literal("No valuables Found!"));
            }
        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;
    }

    private void outputValuableCoordinates(BlockPos blocksPos, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Found " + I18n.get(block.getDescriptionId()) + " at " +
                "(" + blocksPos.getX() + ", " + blocksPos.getY() + ", " + blocksPos.getZ() + ")"));
    }

    private boolean isValueableBlock(BlockState state) {
        return state.is(Blocks.IRON_ORE) || state.is(Blocks.DIAMOND_ORE);
    }
}
