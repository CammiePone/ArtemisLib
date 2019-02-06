package com.artemis.artemislib;

import com.artemis.artemislib.util.EntityResizing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		EntityResizing.resizeEntityPlayer(event.player, 1.0F, 1.0F, 1.65F);
	}
	
	@SubscribeEvent
	public static void onLivingTick(LivingUpdateEvent event)
	{
		EntityResizing.resizeEntityLiving(event.getEntityLiving(), 0.25F, 0.25F);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onLivingRenderPre(RenderLivingEvent.Pre event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			EntityResizing.renderEntityPre(event, (EntityPlayer) event.getEntity(), 1.0F);
		}
		else
		{
			EntityResizing.renderEntityPre(event, event.getEntity(), 0.25F);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onLivingRenderPost(RenderLivingEvent.Post event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			EntityResizing.renderEntityPost();
		}
		else
		{
			EntityResizing.renderEntityPost();
		}
	}
}
