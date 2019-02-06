package com.artemis.artemislib.util.capability.resizing;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ResizeCap implements ICapabilitySerializable<NBTTagCompound>
{
	private IResizeCap capabilitySize = null;
	
	public ResizeCap()
	{
		this.capabilitySize = new DesizeCap();
	}
	
	public ResizeCap(IResizeCap capability)
	{
		this.capabilitySize = capability;
	}
	
	@CapabilityInject(IResizeCap.class)
	public static final Capability<IResizeCap> sizeCapability = null;
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == sizeCapability;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if ((sizeCapability != null) && (capability == sizeCapability))
		{
			return (T) capabilitySize;
		}
		
		return null;
	}
	
	@Override
	public NBTTagCompound serializeNBT()
	{
		return capabilitySize.saveNBT();
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		capabilitySize.loadNBT(nbt);
	}
}
