package com.artemis.artemislib.network.packets;

import java.util.Random;

import javax.annotation.Nullable;

import com.artemis.artemislib.Main;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class PacketOnResize implements IMessage
{
	public int entityID;
	private boolean shouldSpawnParticles;
	
	public PacketOnResize() {}
	
	public PacketOnResize(EntityLivingBase entity, boolean shouldSpawnParticles)
	{
		entityID = entity.getEntityId();
		this.shouldSpawnParticles = shouldSpawnParticles;
	}
	
	public boolean shouldSpawnParticles()
	{
		return shouldSpawnParticles;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityID);
		buf.writeBoolean(shouldSpawnParticles);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		entityID = buf.readInt();
		shouldSpawnParticles = buf.readBoolean();
	}
	
	/**
	 * Removes the specified resize effects from the player and, if allowed by the packet, spawns particles at the resized player's location
	 * 
	 * @param ctx handler context the packet is in
	 * @param removeGrowth removes growth effect from player
	 * @param removeShrinking removes shrinking effect from player
	 * @return resized player, if that player was found, or null if not found
	 */
	@Nullable
	protected EntityLivingBase resetDefaultSize(MessageContext ctx, float height, float width)
	{
		EntityLivingBase entity = Main.proxy.getEntityLivingBase(ctx, entityID);
		if (entity != null)
		{
			return entity;
		}
		return null;
	}
}