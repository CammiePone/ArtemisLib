package com.artemis.artemislib.util.capability.resizing;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;

public class DesizeCap implements IResizeCap
{
	int size = 100;
	boolean transformed = false;
	int target = 100;
	float width;
	float height;
	float defaultWidth;
	float defaultHeight;
	
	public DesizeCap()
	{
		
	}
	
	public DesizeCap(int size, boolean transformed, int target, float width, float height, float defaultWidth, float defaultHeight)
	{
		this.size = size;
		this.transformed = transformed;
		this.target = target;
		this.width = width;
		this.height = height;
		this.defaultWidth = defaultWidth;
		this.defaultHeight = defaultHeight;
	}
	
	@Override
	public boolean getTrans()
	{
		return transformed;
	}
	
	@Override
	public void setTrans(boolean transformed)
	{
		if(this.transformed != transformed)
		{
			this.transformed = transformed;
		}
	}
	
	@Override
	public int getSize()
	{
		return size;
	}
	
	@Override
	public void setSize(int size)
	{
		size = MathHelper.clamp(size, 20, 200);
		
		if (this.size != size)
		{
			this.size = size;
		}
	}
	
	@Override
	public float getWidth()
	{
		return width;
	}
	
	@Override
	public void setWidth(float width)
	{
		width = MathHelper.clamp(width, 0.1F, 1.2F);
		
		if(this.width != width)
		{
			this.width = width;
		}
	}
	
	@Override
	public float getHeight()
	{
		return height;
	}
	
	@Override
	public void setHeight(float height)
	{
		height = MathHelper.clamp(height, 0.1F, 3.6F);
		
		if(this.height != height)
		{
			this.height = height;
		}
	}
	
	@Override
	public float getDefaultWidth()
	{
		return defaultWidth;
	}
	
	@Override
	public void setDefaultWidth(float defaultWidth)
	{
		if(this.defaultWidth != defaultWidth)
		{
			this.defaultWidth = defaultWidth;
		}
	}
	
	@Override
	public float getDefaultHeight()
	{
		return defaultHeight;
	}
	
	@Override
	public void setDefaultHeight(float defaultHeight)
	{
		if(this.defaultHeight != defaultHeight)
		{
			this.defaultHeight = defaultHeight;
		}
	}
	
	@Override
	public NBTTagCompound saveNBT()
	{
		return (NBTTagCompound) ResizeCapStorage.storage.writeNBT(ResizeCap.sizeCapability, this, null);
	}
	
	@Override
	public void loadNBT(NBTTagCompound compound)
	{
		ResizeCapStorage.storage.readNBT(ResizeCap.sizeCapability, this, null, compound);
	}	
}
