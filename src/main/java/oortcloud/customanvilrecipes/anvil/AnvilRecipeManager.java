package oortcloud.customanvilrecipes.anvil;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import oortcloud.customanvilrecipes.configuration.HashItemType;
import oortcloud.customanvilrecipes.configuration.HashPairedItemType;

import org.apache.commons.lang3.tuple.Pair;

public class AnvilRecipeManager {
	
	public HashMap<HashPairedItemType,Pair<Integer,ItemStack>> anvilMap;
	
	private static AnvilRecipeManager instance;
	
	private AnvilRecipeManager() {
		anvilMap = new HashMap<HashPairedItemType, Pair<Integer,ItemStack>>();
	}
	
	public static AnvilRecipeManager getInstance() {
		if (instance==null) {
			instance = new AnvilRecipeManager();
		}
		return instance;
	}
	
	
}
