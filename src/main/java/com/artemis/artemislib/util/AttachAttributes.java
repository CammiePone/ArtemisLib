package com.artemis.artemislib.util;

import com.artemis.artemislib.capabilities.sizeCap.SizeCapPro;
import com.artemis.artemislib.util.attributes.ArtemisLibAttributes;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AttachAttributes
{

	@SubscribeEvent
	public void attachAttributes(EntityEvent.EntityConstructing event)
	{
		if(event.getEntity() instanceof EntityLivingBase)
		{
			final EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			final AbstractAttributeMap map = entity.getAttributeMap();

			map.registerAttribute(ArtemisLibAttributes.ENTITY_HEIGHT);
			map.registerAttribute(ArtemisLibAttributes.ENTITY_WIDTH);
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		final EntityPlayer player = event.player;
		player.getCapability(SizeCapPro.sizeCapability, null).ifPresent(cap ->
		{
			final boolean hasHeightModifier = player.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT).getModifiers().isEmpty();
			final boolean hasWidthModifier = player.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH).getModifiers().isEmpty();

			final double heightAttribute = player.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT).getValue();
			final double widthAttribute = player.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH).getValue();
			float height = (float) (cap.getDefaultHeight() * heightAttribute);
			float width = (float) (cap.getDefaultWidth() * widthAttribute);

			/* Makes Sure to only Run the Code IF the Entity Has Modifiers */
			if((hasHeightModifier != true) || (hasWidthModifier != true))
			{
				/* If the Entity Does have a Modifier get it's size before changing it's size */
				if(cap.getTrans() != true)
				{
					cap.setDefaultHeight(1.8f);
					cap.setDefaultWidth(0.6f);
					cap.setTrans(true);
				}
				/* Handles Resizing while true */
				if(cap.getTrans() == true)
				{
					float eyeHeight = (float) (player.getDefaultEyeHeight() * heightAttribute);
					if (player.isSneaking())
					{
						height = height*MathHelper.ceil(0.9f);
						eyeHeight = height*MathHelper.ceil(0.9f);
					}
					if (player.isElytraFlying())
					{
						height = height*0.33f;
					}
					if (player.isPlayerSleeping())
					{
						width = 0.2F;
						height = 0.2F;
					}
					if (player.isOnePlayerRiding())
					{
						//eyeHeight = (float) (player.getDefaultEyeHeight() * heightAttribute)*1.4f;
						//height = height*1.4f;
					}

					eyeHeight = MathHelper.clamp(eyeHeight, 0.22F, eyeHeight);
					width = MathHelper.clamp(width, 0.252F, width);
					height = MathHelper.clamp(height, 0.252F, height);
					if(player.getEyeHeight() != eyeHeight) {
						ObfuscationReflectionHelper.setPrivateValue(EntityPlayer.class, player, eyeHeight, "eyeHeight");
					}
					player.height = height;
					player.width = width;

					final double d0 = width / 2.0D;
					final AxisAlignedBB aabb = player.getBoundingBox();
					player.setBoundingBox(new AxisAlignedBB(player.posX - d0, aabb.minY, player.posZ - d0,
							player.posX + d0, aabb.minY + player.height, player.posZ + d0));
				}
			}
			else /* If the Entity Does not have any Modifiers */
			{
				/* Returned the Entities Size Back to Normal */
				if(cap.getTrans() == true)
				{
					player.height = height;
					player.width = width;
					final double d0 = width / 2.0D;
					final AxisAlignedBB aabb = player.getBoundingBox();
					player.setBoundingBox(new AxisAlignedBB(player.posX - d0, aabb.minY, player.posZ - d0,
							player.posX + d0, aabb.minY + height, player.posZ + d0));
					ObfuscationReflectionHelper.setPrivateValue(EntityPlayer.class, player, player.getDefaultEyeHeight(), "eyeHeight");
					//player.eyeHeight = player.getDefaultEyeHeight();
					cap.setTrans(false);
				}
			}
		});
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		final EntityLivingBase entity = event.getEntityLiving();
		if(!(entity instanceof EntityPlayer))
		{
			entity.getCapability(SizeCapPro.sizeCapability, null).ifPresent(cap ->
			{
				final boolean hasHeightModifier = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT).getModifiers().isEmpty();
				final boolean hasWidthModifier = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH).getModifiers().isEmpty();
				final double heightAttribute = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT).getValue();
				final double widthAttribute = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH).getValue();
				final float height = (float) (cap.getDefaultHeight() * heightAttribute);
				final float width = (float) (cap.getDefaultWidth() * widthAttribute);

				/* Makes Sure to only Run the Code IF the Entity Has Modifiers */
				if((hasHeightModifier != true) || (hasWidthModifier != true))
				{
					/* If the Entity Does have a Modifier get it's size before changing it's size */
					if(cap.getTrans() != true)
					{
						cap.setDefaultHeight(entity.height);
						cap.setDefaultWidth(entity.width);
						cap.setTrans(true);
					}

					/* Handles Resizing while true */
					if(cap.getTrans() == true)
					{
						entity.height = height;
						entity.width = width;

						final double d0 = width / 2.0D;
						final AxisAlignedBB aabb = entity.getBoundingBox();
						entity.setBoundingBox(new AxisAlignedBB(entity.posX - d0, aabb.minY, entity.posZ - d0,
								entity.posX + d0, aabb.minY + entity.height, entity.posZ + d0));
					}
				}
				else /* If the Entity Does not have any Modifiers */
				{
					/* Returned the Entities Size Back to Normal */
					if(cap.getTrans() == true)
					{
						entity.height = height;
						entity.width = width;
						final double d0 = width / 2.0D;
						final AxisAlignedBB aabb = entity.getBoundingBox();
						entity.setBoundingBox(new AxisAlignedBB(entity.posX - d0, aabb.minY, entity.posZ - d0,
								entity.posX + d0, aabb.minY + height, entity.posZ + d0));
						cap.setTrans(false);
					}
				}
			});
		}
	}

	@SuppressWarnings("rawtypes")
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onEntityRenderPre(RenderLivingEvent.Pre event)
	{
		final EntityLivingBase entity = event.getEntity();
		final float height = (float) entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT).getValue();
		final float width = (float) entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH).getValue();
		final float scaleHeight = MathHelper.clamp(height, 0.01F, height);
		final float scaleWidth = MathHelper.clamp(width, 0.01F, width);

		GlStateManager.pushMatrix();
		GlStateManager.scaled(scaleWidth, scaleHeight, scaleWidth);
		GlStateManager.translated((event.getX() / scaleWidth) - event.getX(),
				(event.getY() / scaleHeight) - event.getY(), (event.getZ() / scaleWidth) - event.getZ());

		if(entity instanceof EntityPlayer)
		{
			final EntityPlayer player = (EntityPlayer) entity;
			if(player.getRidingEntity() instanceof AbstractHorse)
			{
				//GlStateManager.translate(0F, (1.7F-scaleHeight)*scaleHeight, 0F);
				//GlStateManager.translate(0, scaleHeight * 2, 0);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onLivingRenderPost(RenderLivingEvent.Post event)
	{
		GlStateManager.popMatrix();
	}
}
