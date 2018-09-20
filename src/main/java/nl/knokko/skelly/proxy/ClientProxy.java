package nl.knokko.skelly.proxy;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import nl.knokko.skelly.entity.EntitySkelly;
import nl.knokko.skelly.entity.model.ModelSkelly;
import nl.knokko.skelly.mod.SkellyBuildersMod;

public class ClientProxy implements Proxy {

	public void preInit() {
		System.out.println("ClientProxy.registerRenderThings()");
		registerEntityRenderer(EntitySkelly.class, EntitySkelly.ID, new ModelSkelly());
	}
	
	private static void registerEntityRenderer(Class entityClass, String id, ModelBase model){
		RenderingRegistry.registerEntityRenderingHandler(entityClass, new RenderFactory(new ResourceLocation(SkellyBuildersMod.MODID, "textures/entities/" + id + ".png"), model));
	}
	
	private static class RenderFactory implements IRenderFactory {
		
		private final ResourceLocation texture;
		private final ModelBase model;
		
		private RenderFactory(ResourceLocation texture, ModelBase model){
			this.texture = texture;
			this.model = model;
		}

		public Render createRenderFor(RenderManager manager) {
			return new CRender(manager, texture, model);
		}
		
		private static class CRender extends RenderLiving {
			
			private final ResourceLocation texture;

			protected CRender(RenderManager manager, ResourceLocation texture, ModelBase model) {
				super(manager, model, 0.4f);
				this.texture = texture;
			}

			@Override
			protected ResourceLocation getEntityTexture(Entity entity) {
				return texture;
			}
		}
	}
}
