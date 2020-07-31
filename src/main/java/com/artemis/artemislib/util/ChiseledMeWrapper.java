package com.artemis.artemislib.util;

import dev.necauqua.mods.cm.api.ChiseledMeAPI;
import net.minecraft.entity.Entity;

public class ChiseledMeWrapper {
	public static void setSize(Entity entity, float size) {
		ChiseledMeAPI.interaction.setSizeOf(entity, size, true);
	}
	
	public static float getSize(Entity entity) {
		return ChiseledMeAPI.interaction.getSizeOf(entity);
	}
}
