package net.julian.tutorialmod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ForceWandItem extends Item {
    public ForceWandItem(Properties pProperties) {
        super(pProperties);
    }

    //on item use
    //get player
    //raycast from player look position to five blocks
    //if hit
    //throw mob with x velocity
    //return use result success


    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();

        Vec3 lookpos = player.getLookAngle();

        List<Entity> entities = player.level().getEntities(player, player.getBoundingBox().inflate(10,2,10));

        for (Entity entity:entities) {
            player.sendSystemMessage(Component.literal("Entity: " + entity.getName()));
        }
        return InteractionResult.SUCCESS;
    }
}
