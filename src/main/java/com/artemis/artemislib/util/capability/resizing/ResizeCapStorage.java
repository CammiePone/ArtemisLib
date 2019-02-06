package com.artemis.artemislib.util.capability.resizing;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ResizeCapStorage implements IStorage<IResizeCap>
{
	public static final ResizeCapStorage storage = new ResizeCapStorage();

	@Override
	public NBTBase writeNBT(Capability<IResizeCap> capability, IResizeCap instance, EnumFacing side)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("size", instance.getSize());
		tag.setBoolean("transformed", instance.getTrans());
		tag.setFloat("height", instance.getHeight());
		tag.setFloat("width", instance.getWidth());
		return tag;
	}

	@Override
	public void readNBT(Capability<IResizeCap> capability, IResizeCap instance, EnumFacing side, NBTBase nbt)
	{
		if (nbt instanceof NBTTagCompound)
		{
			NBTTagCompound tag = (NBTTagCompound) nbt;
			
			if (tag.hasKey("size"))
			{
				instance.setSize(tag.getInteger("size"));
			}
			
			if (tag.hasKey("transformed"))
			{
				instance.setTrans(tag.getBoolean("transformed"));
			}
			
			if(tag.hasKey("height"))
			{
				instance.setHeight(tag.getFloat("height"));
			}
			
			if(tag.hasKey("width"))
			{
				instance.setWidth(tag.getFloat("width"));
			}
		}
	}
}
