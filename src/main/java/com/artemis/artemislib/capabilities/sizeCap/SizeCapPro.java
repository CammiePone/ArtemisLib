package com.artemis.artemislib.capabilities.sizeCap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class SizeCapPro implements ICapabilitySerializable<NBTTagCompound>
{
	private ISizeCap capabilitySize = null;

	public SizeCapPro()
	{
		this.capabilitySize = new SizeDefaultCap();
	}

	public SizeCapPro(ISizeCap capability)
	{
		this.capabilitySize = capability;
	}

	@CapabilityInject(ISizeCap.class)
	public static final Capability<ISizeCap> sizeCapability = null;

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, EnumFacing facing)
	{
		if((capability == sizeCapability))
		{
			return LazyOptional.of(() -> {return (T) this.capabilitySize;});
		}
		return LazyOptional.empty();
		//		return sizeCapability.orEmpty(capability, LazyOptional.of(()-> this.capabilitySize));
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return this.capabilitySize.saveNBT();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.capabilitySize.loadNBT(nbt);
	}
}