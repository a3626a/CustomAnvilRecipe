package oortcloud.customanvilrecipes.anvil;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import oortcloud.customanvilrecipes.configuration.HashItemType;
import oortcloud.customanvilrecipes.configuration.HashPairedItemType;

import org.apache.commons.lang3.tuple.Pair;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AnvilUpdateEventHandler {

	@SubscribeEvent
	public void anvilUpdate(AnvilUpdateEvent event) {
		Pair result = AnvilRecipeManager.getInstance().anvilMap.get(new HashPairedItemType(new HashItemType(event.left), new HashItemType(event.right)));
		if (result!=null) {
			event.output=(ItemStack) result.getRight();
			event.cost=(Integer) result.getLeft();
		}
	}
	
}
