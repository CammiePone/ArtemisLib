package com.artemis.artemislib.network.packets;

import com.artemis.artemislib.Main;
import com.artemis.artemislib.util.capability.resizing.ResizeCap;
import com.artemis.artemislib.util.capability.resizing.IResizeCap;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CapDataMessage implements IMessage
{
	public CapDataMessage()
	{
		
	}
	
	public int size = 100;
	public boolean trans = false;
	public float width = 0.6F;
	public float height = 1.8F;
	public float defaultWidth = 0.6F;
	public float defaultHeight = 1.8F;
	public float eyeHeight = 1.62F;
	public static int entityID = 0;
	
	public CapDataMessage(int size, boolean trans, float width, float height, float defaultWidth, float defaultHeight, float eyeHeight, int entityID)
	{
		this.size = size;
		this.trans = trans;
		this.width = width;
		this.height = height;
		this.defaultWidth = defaultWidth;
		this.defaultHeight = defaultHeight;
		this.eyeHeight = eyeHeight;
		this.entityID = entityID;
	}
	
	@Override public void toBytes(ByteBuf buf)
	{
		// Writes the int into the buf
		buf.writeInt(size);
		buf.writeBoolean(trans);
		buf.writeFloat(width);
		buf.writeFloat(height);
		buf.writeFloat(defaultWidth);
		buf.writeFloat(defaultHeight);
		buf.writeFloat(eyeHeight);
		buf.writeInt(entityID);
	}
	
	@Override public void fromBytes(ByteBuf buf)
	{
		this.size = buf.readInt();
		this.trans = buf.readBoolean();
		this.width = buf.readFloat();
		this.height = buf.readFloat();
		this.defaultWidth = buf.readFloat();
		this.defaultHeight = buf.readFloat();
		this.eyeHeight = buf.readFloat();
		this.entityID = buf.readInt();
	}
	
	public static class Handler implements IMessageHandler<CapDataMessage, IMessage>
	{
		@Override public IMessage onMessage(CapDataMessage message, MessageContext ctx)
		{
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() ->
			{
				if(Main.proxy.getEntityLivingBase(ctx, entityID) != null)
				{
					EntityLivingBase entity = Main.proxy.getEntityLivingBase(ctx, entityID);
					IResizeCap cap = entity.getCapability(ResizeCap.sizeCapability, null);
					
					cap.setSize(message.size);
					cap.setTrans(message.trans);
					cap.setWidth(message.width);
					cap.setHeight(message.height);
					cap.setDefaultWidth(message.defaultWidth);
					cap.setDefaultHeight(message.defaultHeight);
					
					if(entity instanceof EntityPlayer)
					{
						EntityPlayer player = (EntityPlayer) entity;
						
						player.eyeHeight = message.eyeHeight;
					}
				}
			});
			return null;
		}
	}
}
