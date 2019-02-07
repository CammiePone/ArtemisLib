package com.artemis.artemislib.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.artemis.artemislib.util.attributes.ArtemisLibAttributes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@EventBusSubscriber
public class AttachAttributes
{
	protected static final Method setSize = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70105_a", void.class, float.class, float.class);
	
	@SubscribeEvent
	public static void attackAttributes(EntityEvent.EntityConstructing event)
	{
		if(event.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			AttributeMap map = (AttributeMap) entity.getAttributeMap();
			
			map.registerAttribute(ArtemisLibAttributes.ENTITY_HEIGHT);
			map.registerAttribute(ArtemisLibAttributes.ENTITY_WIDTH);
			
			if(entity instanceof EntityPlayer)
			{
				map.registerAttribute(ArtemisLibAttributes.ENTITY_EYEHEIGHT);
			}
		}
	}
	
	@SubscribeEvent
	public static void onEntityTick(LivingUpdateEvent event)
	{
		if(!(event.getEntityLiving() instanceof EntityPlayer))
		{
			EntityLivingBase entity = event.getEntityLiving();
			double heightAttribute = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT).getAttributeValue();
			double widthAttribute = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH).getAttributeValue();
			AxisAlignedBB aabb = entity.getEntityBoundingBox();
			float height = (float) (entity.height * heightAttribute);
			float width = (float) (entity.width * widthAttribute);
			double d0 = width / 2.0D;
			
			try
			{
				setSize.invoke(entity, width, height);
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
	        
			entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, aabb.minY, entity.posZ - d0, 
	        		entity.posX + d0, aabb.minY + height, entity.posZ + d0));
		}
	}
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		double heightAttribute = player.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT).getAttributeValue();
		double widthAttribute = player.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH).getAttributeValue();
		double eyeHeightAttribute = player.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_EYEHEIGHT).getAttributeValue();
		AxisAlignedBB aabb = player.getEntityBoundingBox();
		float height = (float) (player.height * heightAttribute);
		float width = (float) (player.width * widthAttribute);
		float eyeHeight = (float) (player.getDefaultEyeHeight() * eyeHeightAttribute);
		double d0 = width / 2.0D;
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
}
