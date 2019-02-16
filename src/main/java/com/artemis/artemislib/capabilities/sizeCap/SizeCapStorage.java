package com.artemis.artemislib.capabilities.sizeCap;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class SizeCapStorage implements IStorage<ISizeCap>
{
	public static final SizeCapStorage storage = new SizeCapStorage();
	
	@Override
	public INBTBase writeNBT(Capability<ISizeCap> capability, ISizeCap instance, EnumFacing side)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		tag.setBoolean("transformed", instance.getTrans());
		return tag;
	}
	
	@Override
	public void readNBT(Capability<ISizeCap> capability, ISizeCap instance, EnumFacing side, INBTBase nbt)
	{
		if (nbt instanceof NBTTagCompound)
		{
			final NBTTagCompound tag = (NBTTagCompound) nbt;
			
			if (tag.hasKey("transformed"))
			{
				instance.setTrans(tag.getBoolean("transformed"));
			}
		}
	}
}
