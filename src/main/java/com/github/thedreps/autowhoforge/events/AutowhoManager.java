package com.github.thedreps.autowhoforge.events;

import com.github.thedreps.autowhoforge.AutoWhoForge;
import com.github.thedreps.autowhoforge.utility.LogHelper;
import com.github.thedreps.autowhoforge.utility.PlayerToXYZ;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

@SuppressWarnings("unused")
public class AutowhoManager {

    public static void startAutowho(int delay){
        ticksLeft = delay;
        countingTicksAutowho = true;
        AutoWhoForge.isAwaitingAutowho = false;
    }

    private static boolean countingTicksAutowho = false;
    private static int ticksLeft;

    @SubscribeEvent
    public void autowhoTimer(TickEvent.ClientTickEvent e) {
        if(countingTicksAutowho) {
            if(ticksLeft > 0) {
                ticksLeft--;

            }else {
                autowho();
                countingTicksAutowho = false;
            }
        }
    }



    private void autowho() {
        String me = Minecraft.getMinecraft().thePlayer.getName();
        List<EntityPlayer> players = Minecraft.getMinecraft().theWorld.playerEntities;
        StringBuilder sb = new StringBuilder();
        sb.append("ONLINE: ");

        double myX = 0;
        double myY = 0;
        double myZ = 0;

        for (EntityPlayer player : players) {
            if(player.getName().equalsIgnoreCase(me)){
                PlayerToXYZ pXYZ = new PlayerToXYZ(player);
                myX = pXYZ.getX();
                myY = pXYZ.getY();
                myZ = pXYZ.getZ();
            }
        }

        String fakeName = null;

        for (EntityPlayer player : players) {
            LogHelper.info(player);
            PlayerToXYZ pXYZ = new PlayerToXYZ(player);
            if (
                    (pXYZ.getX() > myX-3 && pXYZ.getX() < myX +3)
                            &&
                            (pXYZ.getY() < myY - 3 || pXYZ.getY() > myY + 3)
                            &&
                            (pXYZ.getZ() > myZ-3 && pXYZ.getZ() < myZ +3)
                            &&
                            !(player.getName().equalsIgnoreCase(me))
            ) {
                fakeName = player.getName();
            }
        }

        for (EntityPlayer player : players) {
            double index = 0;
            boolean isNicked = false;

            if(player.getDisplayNameString().equalsIgnoreCase(fakeName)){
                continue;
            }

            sb.append(player.getDisplayNameString()).append(", ");

        }

        String online = sb.toString().trim();
        online = online.substring(0, online.length()-1);

        LogHelper.info("[CHAT] " + online);

    }

}