package nl.knokko.skelly.entity;

import java.awt.Color;

import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nl.knokko.skelly.mod.SkellyBuildersMod;

public class EntityHandler {
	
	private static int ID = 0;
	
	@SubscribeEvent
	public void onEntityRegistry(RegistryEvent.Register<EntityEntry> event){
		register(event, EntitySkelly.class, EntitySkelly.ID);
	}
	
	private void register(RegistryEvent.Register<EntityEntry> event, Class entityClass, String name){
		EntityEntry entry = EntityEntryBuilder.create().entity(entityClass)
				.id(new ResourceLocation(SkellyBuildersMod.MODID, name), ID++)
			    .name(name)
			    .egg(0xFFFFFF, 0xAAAAAA)
			    .tracker(64, 1, true)
			    .build();
		event.getRegistry().register(entry);
	}
}