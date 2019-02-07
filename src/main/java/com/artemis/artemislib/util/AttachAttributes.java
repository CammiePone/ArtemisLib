package com.artemis.artemislib.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.artemis.artemislib.util.attributes.ArtemisLibAttributes;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class AttachAttributes
{
	protected static final Method setSize = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70105_a", void.class, float.class, float.class);
	
	@SubscribeEvent
	public static void attachAttributes(EntityEvent.EntityConstructing event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer entity = (EntityPlayer) event.getEntity();
			AbstractAttributeMap map = entity.getAttributeMap();
			
			map.registerAttribute(ArtemisLibAttributes.ENTITY_HEIGHT);
			map.registerAttribute(ArtemisLibAttributes.ENTITY_WIDTH);
		}
	}
	
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		double heightAttribute = player.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT).getAttributeValue();
		double widthAttribute = player.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH).getAttributeValue();
		float height = (float) (player.height * heightAttribute);
		float width = (float) (player.width * widthAttribute);
		AxisAlignedBB aabb = player.getEntityBoundingBox();
		double d0 = width / 2.0D;
		float eyeHeight = (float) (player.getDefaultEyeHeight() * heightAttribute);
		player.eyeHeight = eyeHeight;
		
		try
		{
			setSize.invoke(player, width, height);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
        
		player.setEntityBoundingBox(new AxisAlignedBB(player.posX - d0, aabb.minY, player.posZ - d0, 
				player.posX + d0, aabb.minY + height, player.posZ + d0));
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onEntityRenderPre(RenderLivingEvent.Pre event)
	{
		EntityLivingBase entity = event.getEntity();
		float scaleHeight = (float) entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT).getAttributeValue();
		float scaleWidth = (float) entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH).getAttributeValue();
		
		GlStateManager.pushMatrix();
		GlStateManager.scale(scaleWidth, scaleHeight, scaleWidth);
		GlStateManager.translate((event.getX() / scaleWidth) - event.getX(), 
				(event.getY() / scaleHeight) - event.getY(), (event.getZ() / scaleWidth) - event.getZ());
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onLivingRenderPost(RenderLivingEvent.Post event)
	{
		GlStateManager.popMatrix();
	}
}
