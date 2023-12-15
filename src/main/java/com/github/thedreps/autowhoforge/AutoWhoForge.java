package com.github.thedreps.autowhoforge;

import com.github.thedreps.autowhoforge.events.AutowhoManager;
import com.github.thedreps.autowhoforge.reference.Reference;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = "AutoWhoForge", useMetadata=true)
public class AutoWhoForge {

    @Mod.Instance(Reference.MOD_ID)
    public static AutoWhoForge instance = new AutoWhoForge();
    private static ServerInfo serverInfo = new ServerInfo();
    public static boolean isAwaitingAutowho = true;

    public static ServerInfo getServerInfo(){return serverInfo;}


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){

        //Mods
        MinecraftForge.EVENT_BUS.register(new AutowhoManager());

    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent e) {
        String message = e.message.getUnformattedText();

        //Catches /locraw
        if(message.contains("{\"server\":")) {
            isAwaitingLocation = false;
            serverInfo.reset();
            Gson g = new Gson();
            serverInfo = g.fromJson(message, ServerInfo.class);

            serverInfo.json = message;
            e.setCanceled(true);


            //Starts autowho timer
            if(isAwaitingAutowho) {
                if((serverInfo.gametype.equals("BEDWARS") || serverInfo.gametype.equals("SKYWARS")) && !(serverInfo.mode.equals("default"))) {
                    AutowhoManager.startAutowho(20);
                }
            }
        }
    }

    boolean isAwaitingLocation = true;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        serverInfo.reset();
        isAwaitingLocation = true;
        isAwaitingAutowho = true;

    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onChunkLoad(ChunkEvent.Load event) {
        if(isAwaitingLocation) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/locraw");
            isAwaitingLocation = false;
        }
    }




}
