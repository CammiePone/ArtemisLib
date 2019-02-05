package com.artemis.artemislib.util;

import com.artemis.artemislib.network.ResizePacketHandler;
import com.artemis.artemislib.network.packets.PacketAlteredSize;
import com.artemis.artemislib.network.packets.PacketNormalSize;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;

public class EntityResizing
{
	/**
	 * This method belongs in the TickEvent.PlayerTickEvent, as it only applies to players. It cannot go into LivingUpdateEvent.
	 * @param player
	 * @param height
	 * @param width
	 * @param eyeHeight
	 */
	public static void resizeEntityPlayer(EntityPlayer player, float height, float width, float eyeHeight)
	{
		ResizePacketHandler.INSTANCE.sendToAllTracking(new PacketAlteredSize(player, height, width, false), player);
		player.eyeHeight = eyeHeight;
	}
	
	/**
	 * Sets EntityLivingBase's height and width. Recommended to not use for EntityPlayer. Use resizeEnitityPlayer() instead.
	 * @param entity
	 * @param height
	 * @param width
	 */
	public static void resizeEntityLiving(EntityLivingBase entity, float height, float width)
	{
		ResizePacketHandler.INSTANCE.sendToAllTracking(new PacketAlteredSize(entity, height, width, false), entity);
	}
	
	/**
	 * Resets the entity's size to normal. Recommended to use this over manually resetting entity size and eye height.
	 * @param entity
	 */
	public static void resetEntitySize(EntityLivingBase entity)
	{
		ResizePacketHandler.INSTANCE.sendToAllTracking(new PacketNormalSize(entity), entity);
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
