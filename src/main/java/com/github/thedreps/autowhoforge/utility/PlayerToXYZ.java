package com.github.thedreps.autowhoforge.utility;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerToXYZ {

    private final double x;
    private final double y;
    private final double z;

    public PlayerToXYZ(EntityPlayer player){
        String playerStr = player.toString();
        x = Double.parseDouble(playerStr.substring(playerStr.indexOf(", x=") + 4, playerStr.indexOf(", y=")));
        y = Double.parseDouble(playerStr.substring(playerStr.indexOf(", y=") + 4, playerStr.indexOf(", z=")));
        z = Double.parseDouble(playerStr.substring(playerStr.indexOf(", z=") + 4, playerStr.length()-1));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
