package com.github.thedreps.autowhoforge.utility;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class Utils {

    public static EntityPlayer nameToPlayer(String name){

        EntityPlayer ep = null;

        for(EntityPlayer player : Minecraft.getMinecraft().theWorld.playerEntities){
            if(player.getName().equalsIgnoreCase(name)){
                ep = player;
            }
        }

        return ep;
    }
}
