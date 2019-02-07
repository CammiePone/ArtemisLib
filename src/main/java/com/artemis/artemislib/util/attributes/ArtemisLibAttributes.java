package com.artemis.artemislib.util.attributes;

import java.util.Collection;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ArtemisLibAttributes
{
	private static final Logger LOGGER = LogManager.getLogger();
	
	public static final IAttribute ENTITY_HEIGHT = (new RangedAttribute((IAttribute) null, "generic.entityHeight", 
			1.0F, Float.MIN_VALUE, Float.MAX_VALUE)).setDescription("Entity Height").setShouldWatch(true);
	
	public static final IAttribute ENTITY_WIDTH = (new RangedAttribute((IAttribute) null, "generic.entityWidth", 
			1.0F, Float.MIN_VALUE, Float.MAX_VALUE)).setDescription("Entity Width").setShouldWatch(true);
	
	public static final IAttribute ENTITY_EYEHEIGHT = (new RangedAttribute((IAttribute) null, "generic.eyeHeight", 
			1.0F, Float.MIN_VALUE, Float.MAX_VALUE)).setDescription("Entity Eye Height").setShouldWatch(true);
	
	public static NBTTagList writeBaseAttributeMapToNBT(AbstractAttributeMap map)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (IAttributeInstance iattributeinstance : map.getAllAttributes())
        {
            nbttaglist.appendTag(writeAttributeInstanceToNBT(iattributeinstance));
        }

        return nbttaglist;
    }
	
	private static NBTTagCompound writeAttributeInstanceToNBT(IAttributeInstance instance)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        IAttribute iattribute = instance.getAttribute();
        nbttagcompound.setString("Name", iattribute.getName());
        nbttagcompound.setDouble("Base", instance.getBaseValue());
        Collection<AttributeModifier> collection = instance.getModifiers();

        if (collection != null && !collection.isEmpty())
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (AttributeModifier attributemodifier : collection)
            {
                if (attributemodifier.isSaved())
                {
                    nbttaglist.appendTag(writeAttributeModifierToNBT(attributemodifier));
                }
            }

            nbttagcompound.setTag("Modifiers", nbttaglist);
        }

        return nbttagcompound;
    }
	
	public static NBTTagCompound writeAttributeModifierToNBT(AttributeModifier modifier)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("Name", modifier.getName());
        nbttagcompound.setDouble("Amount", modifier.getAmount());
        nbttagcompound.setInteger("Operation", modifier.getOperation());
        nbttagcompound.setUniqueId("UUID", modifier.getID());
        return nbttagcompound;
    }

    public static void setAttributeModifiers(AbstractAttributeMap map, NBTTagList list)
    {
        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = list.getCompoundTagAt(i);
            IAttributeInstance iattributeinstance = map.getAttributeInstanceByName(nbttagcompound.getString("Name"));

            if (iattributeinstance == null)
            {
                LOGGER.warn("Ignoring unknown attribute '{}'", (Object)nbttagcompound.getString("Name"));
            }
            else
            {
                applyModifiersToAttributeInstance(iattributeinstance, nbttagcompound);
            }
        }
    }

    private static void applyModifiersToAttributeInstance(IAttributeInstance instance, NBTTagCompound compound)
    {
        instance.setBaseValue(compound.getDouble("Base"));

        if (compound.hasKey("Modifiers", 9))
        {
            NBTTagList nbttaglist = compound.getTagList("Modifiers", 10);

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                AttributeModifier attributemodifier = readAttributeModifierFromNBT(nbttaglist.getCompoundTagAt(i));

                if (attributemodifier != null)
                {
                    AttributeModifier attributemodifier1 = instance.getModifier(attributemodifier.getID());

                    if (attributemodifier1 != null)
                    {
                        instance.removeModifier(attributemodifier1);
                    }

                    instance.applyModifier(attributemodifier);
                }
            }
        }
    }
    
    @Nullable
    public static AttributeModifier readAttributeModifierFromNBT(NBTTagCompound compound)
    {
        UUID uuid = compound.getUniqueId("UUID");

        try
        {
            return new AttributeModifier(uuid, compound.getString("Name"), compound.getDouble("Amount"), compound.getInteger("Operation"));
        }
        catch (Exception exception)
        {
            LOGGER.warn("Unable to create attribute: {}", (Object)exception.getMessage());
            return null;
        }
    }
}
