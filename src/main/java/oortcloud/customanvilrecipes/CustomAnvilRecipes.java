package oortcloud.customanvilrecipes;

import net.minecraftforge.common.MinecraftForge;
import oortcloud.customanvilrecipes.anvil.AnvilUpdateEventHandler;
import oortcloud.customanvilrecipes.configuration.ConfigurationHandler;
import oortcloud.customanvilrecipes.core.proxy.CommonProxy;
import oortcloud.customanvilrecipes.libs.References;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MODID, name = References.MODNAME, version = References.VERSION)

public class CustomAnvilRecipes {
	@Mod.Instance
	public static CustomAnvilRecipes instance;

	@SidedProxy(clientSide = References.CLIENTPROXYLOCATION, serverSide = References.COMMONPROXYLOCATION)
	public static CommonProxy proxy;
	
	public static Logger logger;
	
	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		ConfigurationHandler.init(event);
	}
	
	@Mod.EventHandler
	public static void Init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new AnvilUpdateEventHandler());
	}
	
	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		ConfigurationHandler.sync();
		if (Loader.isModLoaded("NotEnoughItems"))
			proxy.initNEI();
	}
}
