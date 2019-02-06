package com.artemis.artemislib;

import com.artemis.artemislib.proxy.CommonProxy;
import com.artemis.artemislib.util.capability.CapStorage;
import com.artemis.artemislib.util.capability.CapabilityFactory;
import com.artemis.artemislib.util.capability.ICap;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
	modid = Reference.MODID, 
	name = Reference.NAME, 
	version = Reference.VERSION, 
	acceptedMinecraftVersions = Reference.ACCEPTEDVERSIONS, 
	dependencies = Reference.DEPENDENCIES)
public class Main 
{
	@Instance
	public static Main instance;
	
	//----Proxy----//
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	//----Initialization----//
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		CapabilityManager.INSTANCE.register(ICap.class, new CapStorage(), new CapabilityFactory());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
