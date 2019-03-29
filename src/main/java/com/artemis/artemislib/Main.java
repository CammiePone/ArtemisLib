package com.artemis.artemislib;

import java.util.logging.Logger;

import com.artemis.artemislib.proxy.ClientProxy;
import com.artemis.artemislib.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MODID)
public class Main
{
	//Instance
	public static Main instance;

	public static Logger logger;

	public Main()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		instance = this;

		MinecraftForge.EVENT_BUS.register(this);
	}

	//----Proxy----//
	public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

	//----Initialization----//
	public void setup(final FMLCommonSetupEvent event)
	{
		proxy.setup(event);
	}
}
