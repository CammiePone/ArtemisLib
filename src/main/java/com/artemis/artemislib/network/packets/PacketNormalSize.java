package com.artemis.artemislib.network.packets;

import com.artemis.artemislib.Main;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketNormalSize extends PacketOnResize
{
	public PacketNormalSize() {}
	
	public PacketNormalSize(EntityLivingBase entity)
	{
		super(entity, true);
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
	}
	
	public static class Handler implements IMessageHandler<PacketNormalSize, IMessage>
	{
		@Override
		public IMessage onMessage(PacketNormalSize message, MessageContext ctx)
		{
			EntityLivingBase entity = Main.proxy.getEntityLivingBase(ctx, message.entityID);
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> message.resetDefaultSize(ctx, entity.height, entity.width));
			
			return null;
		}
	}
}
