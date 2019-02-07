package com.artemis.artemislib.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import com.artemis.artemislib.util.attributes.ArtemisLibAttributes;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class AttachAttributes
{
	protected static final Method setSize = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70105_a", void.class, float.class, float.class);
	
	@SubscribeEvent
	public static void attachAttributes(EntityEvent.EntityConstructing event)
	{
		if(event.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			AbstractAttributeMap map = entity.getAttributeMap();
			
			map.registerAttribute(ArtemisLibAttributes.ENTITY_HEIGHT);
			map.registerAttribute(ArtemisLibAttributes.ENTITY_WIDTH);
		}
	}
	
	@SubscribeEvent
	public static void onEntityTick(LivingEvent.LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		double heightAttribute = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT).getAttributeValue();
		double widthAttribute = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH).getAttributeValue();
		float height = (float) (entity.height * heightAttribute);
		float width = (float) (entity.width * widthAttribute);
		AxisAlignedBB aabb = entity.getEntityBoundingBox();
		double d0 = width / 2.0D;
		
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			float eyeHeight = (float) (player.getDefaultEyeHeight() * heightAttribute);
			player.eyeHeight = eyeHeight;
		}
		
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
	
	@SubscribeEvent
	public static void onLivingHurt(LivingDamageEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		UUID height = UUID.fromString("5017c7e0-413a-4023-b569-afcf57dbec18");
		UUID width = UUID.fromString("32b6d7e3-c5ba-4d9b-9244-b706a273c4b3");
		
		if(!entity.world.isRemote)
		{
			if(event.getSource().isMagicDamage())
			{
				Multimap<String, AttributeModifier> attributes = HashMultimap.create();
				
				attributes.put(ArtemisLibAttributes.ENTITY_HEIGHT.getName(), new AttributeModifier(height, "Height", 1.0, 2));
				attributes.put(ArtemisLibAttributes.ENTITY_WIDTH.getName(), new AttributeModifier(width, "Width", 1.0, 2));
				entity.getAttributeMap().applyAttributeModifiers(attributes);
			}
		}
	}
}
