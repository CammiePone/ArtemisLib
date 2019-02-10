package com.artemis.artemislib.util.debug;

import java.util.UUID;

import com.artemis.artemislib.util.attributes.ArtemisLibAttributes;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.event.entity.player.PlayerEvent.StopTracking;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class debugMethods {

	private static UUID uuidH = UUID.fromString("f269dd95-41c1-49b5-ab89-be40c5da69b0");
	private static UUID uuidW = UUID.fromString("0bc6b919-49e9-4f64-8702-20b220ea9d84");

	private static int target = -1;

	@SubscribeEvent
	public void playerTick(TickEvent.PlayerTickEvent event) {
		final EntityPlayer player = event.player;
		final boolean client = player.world.isRemote;

		if(event.phase == Phase.END && client) {
			if(player.isSneaking() && player.ticksExisted%20==0) {
				if(player.getHeldItemMainhand().getItem() == Items.APPLE) {
					setTarget(player.getEntityId());
				}
				if(Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.typeOfHit == Type.ENTITY) {
					final int id = Minecraft.getMinecraft().objectMouseOver.entityHit.getEntityId();
					if(getTarget() != id) {
						setTarget(id);
						System.out.println("Setting Target: Id= " + id);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void LivingUpdateEvent(LivingUpdateEvent event) {

		final EntityLivingBase ent = event.getEntityLiving();

		if(!(ent instanceof EntityPlayer)) {
			if(ent.world.isRemote) {

			}
		}

		if(event.getEntityLiving() instanceof EntityPlayer) {
			final EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			if(!(getTarget() < 0))
			{
				final Entity target = player.world.getEntityByID(getTarget());
				System.out.println(target.getName() + " With ID: " + getTarget() + " Found");

				if(target instanceof EntityLivingBase)
				{
					final EntityLivingBase entity = (EntityLivingBase) target;
					//					UserMethods.addModifier(entity, -0.8D, -0.8D);
					//					UserMethods.removeodifier(entity);
					//					UserMethods.addAndReplaceModifier(entity, -0.8, -0.8);
					final IAttributeInstance entityHeight = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT);
					final IAttributeInstance entityWidth = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH);
					final AttributeModifier heightModifier = entityHeight.getModifier(uuidH);
					final AttributeModifier widthModifier = entityWidth.getModifier(uuidW);

					if(heightModifier == null && widthModifier == null)
					{
						System.out.println("Adding Modifiers");
						entityHeight.applyModifier(constructHeightModifier());
						entityWidth.applyModifier(constructWidthModifier());
					}

					if(heightModifier != null && widthModifier != null)
					{
						System.out.println("Removing Modifiers");
						entityHeight.removeModifier(uuidH);
						entityWidth.removeModifier(uuidW);
					}
				}
			}
			setTarget(-1);
		}
	}

	@SubscribeEvent
	public void startTracking(StartTracking event)
	{
		//		if(event.getEntityLiving() != null && event.getEntityLiving().hasCapability(SizeCapPro.sizeCapability, null))
		//		{
		//			final EntityLivingBase entity = event.getEntityLiving();
		//			System.out.println(entity.getName() + " started Tracking " + event.getTarget().getName());
		//
		//			if(event.getTarget() instanceof EntityLivingBase)
		//			{
		//				final EntityLivingBase target = (EntityLivingBase) event.getTarget();
		//				System.out.println(target.getEntityAttribute(ArtemisLibAttributes.ENTITY_HEIGHT).getModifiers());
		//			}
		//		}
	}

	@SubscribeEvent
	public void stopTracking(StopTracking event)
	{
		//		if(event.getEntityLiving() != null && event.getEntityLiving().hasCapability(SizeCapPro.sizeCapability, null))
		//		{
		//			final EntityLivingBase entity = event.getEntityLiving();
		//			System.out.println(entity.getName() + " stopped Tracking " + event.getTarget().getName());
		//
		//			if(event.getTarget() instanceof EntityLivingBase)
		//			{
		//				final EntityLivingBase target = (EntityLivingBase) event.getTarget();
		//				System.out.println(target.getEntityAttribute(ArtemisLibAttributes.ENTITY_HEIGHT).getModifiers());
		//			}
		//		}
	}

	public static AttributeModifier constructHeightModifier()
	{
		return new AttributeModifier(uuidH, "resize", -0.5f, 0);
	}
	public static AttributeModifier constructWidthModifier()
	{
		return new AttributeModifier(uuidW, "resize", -0.5f, 0);
	}

	public static int getTarget()
	{
		return target;
	}

	public static void setTarget(int target)
	{
		debugMethods.target = target;
	}

}
