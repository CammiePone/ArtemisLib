package com.artemis.artemislib.network.packets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.artemis.artemislib.Main;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketAlteredSize extends PacketOnResize
{
	public float height, width;
	
	public PacketAlteredSize() {}
	
	public PacketAlteredSize(EntityLivingBase entity, float height, float width, boolean shouldSpawnParticles)
	{
		super(entity, shouldSpawnParticles);
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
	
	public class Handler implements IMessageHandler<PacketAlteredSize, IMessage>
	{
		public final Method setSize = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70105_a", void.class, float.class, float.class);
		
		@Override
		public IMessage onMessage(PacketAlteredSize message, MessageContext ctx)
		{
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() ->
			{
				EntityLivingBase entity = Main.proxy.getEntityLivingBase(ctx, entityID);
				
				if (entity != null)
				{
					AxisAlignedBB aabb = entity.getEntityBoundingBox();
					double d0 = message.width / 2.0D;
					
					try
					{
						setSize.invoke(entity, message.width, message.height);
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
					catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					}
					catch (InvocationTargetException e)
					{
						e.printStackTrace();
					}
		            
					entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, aabb.minY, entity.posZ - d0, 
		            		entity.posX + d0, aabb.minY + entity.height, entity.posZ + d0));
				}
			});
			
			return null;
		}
	}
}
