package com.artemis.artemislib.network;

import com.artemis.artemislib.Reference;
import com.artemis.artemislib.network.packets.CapDataMessage;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	private static int nextId = 0;
	
	public static void init()
	{
		INSTANCE.registerMessage(CapDataMessage.Handler.class, CapDataMessage.class, next(), Side.CLIENT);
	}
	
	public static int next()
	{
		return nextId++;
	}
}
