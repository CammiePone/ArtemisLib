package com.artemis.artemislib.proxy;

import com.artemis.artemislib.capabilities.Capabilities;
import com.artemis.artemislib.capabilities.CapabilitiesHandler;
import com.artemis.artemislib.util.AttachAttributes;
import com.artemis.artemislib.util.debug.debugMethods;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonProxy
{
	public void setup(final FMLCommonSetupEvent event)
	{
		Capabilities.init();
		MinecraftForge.EVENT_BUS.register(new CapabilitiesHandler());
		MinecraftForge.EVENT_BUS.register(new AttachAttributes());
		MinecraftForge.EVENT_BUS.register(new debugMethods());
	}
}
