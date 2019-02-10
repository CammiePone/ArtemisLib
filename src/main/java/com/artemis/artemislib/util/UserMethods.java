package com.artemis.artemislib.util;

import java.util.UUID;

import com.artemis.artemislib.util.attributes.ArtemisLibAttributes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class UserMethods {

	private static UUID uuidH = UUID.fromString("f269dd95-41c1-49b5-ab89-be40c5da69b0");
	private static UUID uuidW = UUID.fromString("0bc6b919-49e9-4f64-8702-20b220ea9d84");

	static double heightScale;
	static double widthScale;

	private static double getHeightScale()
	{
		return heightScale;
	}

	private static void setHeightScale(double height)
	{
		heightScale = height;
	}
	private static double getWidthScale()
	{
		return widthScale;
	}
	private static void setWidthScale(double width)
	{
		widthScale = width;
	}

	private static AttributeModifier constructHeightModifier()
	{
		return new AttributeModifier(uuidH, "resize", getHeightScale(), 0);
	}
	private static AttributeModifier constructWidthModifier()
	{
		return new AttributeModifier(uuidW, "resize", getWidthScale(), 0);
	}

	public static void addModifier(EntityLivingBase entity, double height, double width)
	{
		final IAttributeInstance entityHeight = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT);
		final IAttributeInstance entityWidth = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH);
		final AttributeModifier heightModifier = entityHeight.getModifier(uuidH);
		final AttributeModifier widthModifier = entityWidth.getModifier(uuidW);

		if(heightModifier == null && widthModifier == null)
		{
			System.out.println("Adding Modifiers");
			setHeightScale(height);
			setWidthScale(width);
			entityHeight.applyModifier(constructHeightModifier());
			entityWidth.applyModifier(constructWidthModifier());
		}
	}

	public static void addAndReplaceModifier(EntityLivingBase entity, double height, double width) {
		final IAttributeInstance entityHeight = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT);
		final IAttributeInstance entityWidth = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH);
		final AttributeModifier heightModifier = entityHeight.getModifier(uuidH);
		final AttributeModifier widthModifier = entityWidth.getModifier(uuidW);

		if(heightModifier == null && widthModifier == null)
		{
			System.out.println("Adding Modifiers");
			setHeightScale(height);
			setWidthScale(width);
			entityHeight.applyModifier(constructHeightModifier());
			entityWidth.applyModifier(constructWidthModifier());
		}

		if(heightModifier != null && heightModifier.getAmount() != height)
		{
			entityHeight.removeModifier(uuidH);
			setHeightScale(height);
			entityHeight.applyModifier(constructHeightModifier());
		}

		if(widthModifier != null && widthModifier.getAmount() != width)
		{
			entityWidth.removeModifier(uuidW);
			setWidthScale(width);
			entityWidth.applyModifier(constructWidthModifier());
		}
	}

	public static void replaceModifer(EntityLivingBase entity, double height, double width) {
		final IAttributeInstance entityHeight = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT);
		final IAttributeInstance entityWidth = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH);
		final AttributeModifier heightModifier = entityHeight.getModifier(uuidH);
		final AttributeModifier widthModifier = entityWidth.getModifier(uuidW);
		if(heightModifier != null && heightModifier.getAmount() != height)
		{
			entityHeight.removeModifier(uuidH);
			setHeightScale(height);
			entityHeight.applyModifier(constructHeightModifier());
		}
		if(widthModifier != null && widthModifier.getAmount() != width)
		{
			entityWidth.removeModifier(uuidW);
			setWidthScale(width);
			entityWidth.applyModifier(constructWidthModifier());
		}
	}

	public static void removeodifier(EntityLivingBase entity)
	{
		final IAttributeInstance entityHeight = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT);
		final IAttributeInstance entityWidth = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH);
		final AttributeModifier heightModifier = entityHeight.getModifier(uuidH);
		final AttributeModifier widthModifier = entityWidth.getModifier(uuidW);

		if(heightModifier != null && widthModifier != null)
		{
			System.out.println("Removing Modifiers");
			entityHeight.removeModifier(uuidH);
			entityWidth.removeModifier(uuidW);
		}
	}

}
