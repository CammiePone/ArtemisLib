package com.artemis.artemislib;

import com.artemis.artemislib.util.EntityResizing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class TestingTheLibrary
{
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityResizing.resizeEntityLiving(event.getEntityLiving(), 5.0F, 5.0F);
	}
	
	@SubscribeEvent
	public static void onPlayerUpdate(TickEvent.PlayerTickEvent event)
	{
		EntityResizing.resizeEntityPlayer(event.player, 0.5F, 0.5F, 0.35F);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onLivingRenderPre(RenderLivingEvent.Pre event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			EntityResizing.renderEntityPre(event, (EntityPlayer) event.getEntity(), 0.5F);
		}
		else
		{
			EntityResizing.renderEntityPre(event, event.getEntity(), 5.0F);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onLivingRenderPost(RenderLivingEvent.Post event)
	{
		EntityResizing.renderEntityPost();
	}
}
