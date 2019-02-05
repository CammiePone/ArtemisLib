package com.artemis.artemislib;

import com.artemis.artemislib.util.EntityResizing;

import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class BeepBoop
{
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityResizing.resizeEntityLiving(event.getEntityLiving(), 0.5F, 0.5F);
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
		EntityResizing.renderEntityPre(event, event.getEntity(), 0.5F);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onLivingRenderPost(RenderLivingEvent.Post event)
	{
		EntityResizing.renderEntityPost();
	}
}
