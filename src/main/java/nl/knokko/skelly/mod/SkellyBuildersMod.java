package nl.knokko.skelly.mod;

import java.lang.reflect.Field;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nl.knokko.skelly.entity.EntityHandler;
import nl.knokko.skelly.proxy.Proxy;

@Mod(modid = SkellyBuildersMod.MODID, version = SkellyBuildersMod.VERSION)
public class SkellyBuildersMod {
	
    public static final String MODID = "skellybuilders";
    public static final String MODNAME = "Skelly Builders";
    public static final String VERSION = "0.0";
    
    @Instance
    public static SkellyBuildersMod instance;
    
    @SidedProxy(clientSide = "nl.knokko.skelly.proxy.ClientProxy", serverSide = "nl.knokko.skelly.proxy.ServerProxy")
    public static Proxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	MinecraftForge.EVENT_BUS.register(new EntityHandler());
    	proxy.preInit();
    	try {
    		Field[] blocks = Blocks.class.getFields();
    		for(Field block : blocks){
    			System.out.println("	public static final BlockType " + block.getName() + " = new BlockType(Blocks." + block.getName() + ");");
    		}
    	} catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    }
}