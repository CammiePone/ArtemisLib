package com.artemis.artemislib.util;

import java.lang.reflect.Method;

import com.artemis.artemislib.util.capability.CapPro;
import com.artemis.artemislib.util.capability.ICap;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class EntityResizing
{
	protected static final Method setSize = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70105_a", void.class, float.class, float.class);
	
	/**
	 * This method belongs in the TickEvent.PlayerTickEvent, as it only applies to players. It cannot go into LivingUpdateEvent.
	 * @param player
	 * @param height
	 * @param width
	 * @param eyeHeight
	 */
	public static void resizeEntityPlayer(EntityPlayer player, float height, float width, float eyeHeight)
	{
		if(player.hasCapability(CapPro.sizeCapability, null))
		{
			ICap capability = player.getCapability(CapPro.sizeCapability, null);
			
			capability.setHeight(height);
			capability.setWidth(width);
			ResizingHandler.setSize(player, capability);
			player.eyeHeight = eyeHeight;
		}
	}
	
	
	
	
	
	/**
	 * Sets EntityLivingBase's height and width. For players, use resizeEnitityPlayer() instead.
	 * @param entity
	 * @param height
	 * @param width
	 */
	public static void resizeEntityLiving(EntityLivingBase entity, float height, float width)
	{
		if(!(entity instanceof EntityPlayer))
		{
			if(entity.hasCapability(CapPro.sizeCapability, null))
			{
				ICap capability = entity.getCapability(CapPro.sizeCapability, null);
				
				capability.setHeight(height);
				capability.setWidth(width);
				ResizingHandler.setSize(entity, capability);
			}
		}
	}
	
	
	
	
	
	/**
	 * Resets the entity's size to normal. Recommended to use this over manually resetting entity size and eye height.
	 * @param entity
	 */
	public static void resetEntitySize(EntityLivingBase entity)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			player.eyeHeight = player.getDefaultEyeHeight();
		}
	}
	
	
	
	
	
	/**
	 * Renders the entity's model to be different sizes. Should go in RenderLivingEvent.Pre, and followed by renderEntityPost() in RenderLivingEvent.Post.
	 * @param event
	 * @param entity
	 * @param scale
	 */
	public static void renderEntityPre(RenderLivingEvent.Pre event, EntityLivingBase entity, float scale)
	{
		GlStateManager.pushMatrix();
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.translate((event.getX() / scale) - event.getX(), 
				(event.getY() / scale) - event.getY(), (event.getZ() / scale) - event.getZ());
	}
	
	
	
	
	
	/**
	 * Meant for RenderLivingEvent.Post. Used in the rendering process, and necessary.
	 */
	public static void renderEntityPost()
	{
		GlStateManager.popMatrix();
	}
}
