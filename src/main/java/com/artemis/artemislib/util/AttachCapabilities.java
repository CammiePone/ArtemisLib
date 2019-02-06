package com.artemis.artemislib.util;

import com.artemis.artemislib.Reference;
import com.artemis.artemislib.util.capability.CapPro;
import com.artemis.artemislib.util.capability.DeCap;
import com.artemis.artemislib.util.capability.ICap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class AttachCapabilities
{
	@SubscribeEvent
	public static void onAddCapabilites(AttachCapabilitiesEvent<Entity> event)
	{
		if((event.getObject() != null) && event.getObject().isNonBoss() && !event.getObject().hasCapability(CapPro.sizeCapability, null)) 
		{
			if(event.getObject() instanceof EntityPlayer) 
			{
				EntityPlayer player = (EntityPlayer) event.getObject();
				int size = 100;
				boolean transformed = false;
				int target = 100;
				float width = player.width;
				float height = player.height;
				float defaultWidth = player.width;
				float defaultHeight = player.height;
				ICap cap = new DeCap(size, transformed, target, width, height, defaultWidth, defaultHeight);
				
				event.addCapability(new ResourceLocation(Reference.MODID, "Capability"), new CapPro(cap));
			}
			else
			{
				Entity entity = event.getObject();
				int size = 100;
				boolean transformed = false;
				int target = 100;
				float width = entity.width;
				float height = entity.height;
				float defaultWidth = entity.width;
				float defaultHeight = entity.height;
				ICap cap = new DeCap(size, transformed, target, width, height, defaultWidth, defaultHeight);
				
				event.addCapability(new ResourceLocation(Reference.MODID, "Capability"), new CapPro(cap));
			}
		}
	}
}
