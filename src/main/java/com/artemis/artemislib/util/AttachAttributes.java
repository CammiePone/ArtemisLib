package com.artemis.artemislib.util;

import java.lang.reflect.Method;

import com.artemis.artemislib.compatibilities.sizeCap.ISizeCap;
import com.artemis.artemislib.compatibilities.sizeCap.SizeCapPro;
import com.artemis.artemislib.util.attributes.ArtemisLibAttributes;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//@EventBusSubscriber
public class AttachAttributes
{
	protected static final Method setSize = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70105_a", void.class, float.class, float.class);

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

	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		final EntityLivingBase entity = event.getEntityLiving();
		if(entity.hasCapability(SizeCapPro.sizeCapability, null)) {
			final ISizeCap cap = entity.getCapability(SizeCapPro.sizeCapability, null);
			if(cap.getTrans() != true) {
				cap.setDefaultHeight(entity.height);
				cap.setDefaultWidth(entity.width);
				cap.setTrans(true);
			}
			if(cap.getTrans() == true) {
				final double heightAttribute = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT).getAttributeValue();
				final double widthAttribute = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH).getAttributeValue();
				final float height = (float) (cap.getDefaultHeight() * heightAttribute);
				final float width = (float) (cap.getDefaultWidth() * widthAttribute);

				if(entity instanceof EntityPlayer) {
					final EntityPlayer player = (EntityPlayer) entity;
					final float eyeHeight = (float) (player.getDefaultEyeHeight() * heightAttribute);
					player.eyeHeight = eyeHeight;
				}
				entity.height = height;
				entity.width = width;

				final double d0 = width / 2.0D;
				final AxisAlignedBB aabb = entity.getEntityBoundingBox();
				entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, aabb.minY, entity.posZ - d0,
						entity.posX + d0, aabb.minY + height, entity.posZ + d0));
			}
		}
	}

	@SubscribeEvent
	public void startTrackingEvent(StartTracking event) {
		if(event.getEntityPlayer() != null) {
			final EntityPlayer player = event.getEntityPlayer();
			final boolean client = player.world.isRemote;
			if(event.getTarget() != null && event.getTarget() instanceof EntityLivingBase && event.getTarget().hasCapability(SizeCapPro.sizeCapability, null)) {
				final EntityLivingBase entity = (EntityLivingBase) event.getTarget();
				final ISizeCap cap = entity.getCapability(SizeCapPro.sizeCapability, null);
				if(entity instanceof EntityPlayer) {
					if(!client) {

					}
				} else {
					if(!client) {

					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onEntityRenderPre(RenderLivingEvent.Pre event)
	{
		final EntityLivingBase entity = event.getEntity();
		final float scaleHeight = (float) entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT).getAttributeValue();
		final float scaleWidth = (float) entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH).getAttributeValue();

		GlStateManager.pushMatrix();
		GlStateManager.scale(scaleWidth, scaleHeight, scaleWidth);
		GlStateManager.translate(event.getX() / scaleWidth - event.getX(),
				event.getY() / scaleHeight - event.getY(), event.getZ() / scaleWidth - event.getZ());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onLivingRenderPost(RenderLivingEvent.Post event)
	{
		GlStateManager.popMatrix();
	}
}
