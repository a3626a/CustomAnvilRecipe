package oortcloud.customanvilrecipes.api.nei;

import net.minecraft.client.gui.GuiRepair;
import codechicken.nei.api.API;

public class NEIHandler {

	public static void init() {
		API.registerRecipeHandler(new AnvilRecipeHandler());
		API.registerUsageHandler(new AnvilRecipeHandler());
	}

}
