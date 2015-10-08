package oortcloud.customanvilrecipes.configuration;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigurationHandler {
	
	public static Configuration config;
	
	public static void init(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
	}
	
	public static void sync() {
		
		String[] rawInput = config.get("Anvil Recipe", "Recipe", new String[]{}).getStringList();
		
		for (String i : rawInput) {
			ConfigurationHelper.addAnvilRecipe(i);
		}
		
		config.save();
	}
	
}
