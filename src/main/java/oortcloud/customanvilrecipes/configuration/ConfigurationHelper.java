package oortcloud.customanvilrecipes.configuration;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import oortcloud.customanvilrecipes.CustomAnvilRecipes;
import oortcloud.customanvilrecipes.anvil.AnvilRecipeManager;

import org.apache.commons.lang3.tuple.Pair;

public class ConfigurationHelper {
	
	/**
	 * 
	 * @param input
	 *            : ((itemstack),(itemstack))=(integer,(itemstack))
	 * @return
	 */
	public static void addAnvilRecipe(String input) {
		String[] splitByEq = input.split("=");
		if (splitByEq.length!=2) {
			exceptionInvalidFormat(input);
			return;
		}
		HashPairedItemType ingr = getItemTypePair(splitByEq[0]);
		Pair rest = getAnvilRecipeResult(splitByEq[1]);
		if (ingr==null) {
			exceptionInvalidFormat(splitByEq[0]);
			return;
		}
		if (rest==null) {
			exceptionInvalidFormat(splitByEq[1]);
			return;
		}
		AnvilRecipeManager.getInstance().anvilMap.put(ingr, rest);
	}
	
	/**
	 * 
	 * @param input
	 *            : (integer,(itemstack))
	 * @return
	 */
	public static Pair getAnvilRecipeResult(String input) {
		String[] split = StringParser.splitByLevel(StringParser.reduceLevel(input));
		if (split.length == 2) {
			Pair output;
			int level = Integer.parseInt(split[0]);
			ItemStack itemStack2 = getItemStack(split[1]);
			if (itemStack2 == null) {
				exceptionInvalidFormat(split[1]);
				return null;
			}
			return Pair.of(level, itemStack2);
		} else {
			exceptionInvalidNumberOfArgument(input);
			return null;
		}
	}
	
	/**
	 * 
	 * @param input
	 *            : ((itemstack),(itemstack))
	 * @return
	 */
	public static HashPairedItemType getItemTypePair(String input) {
		String[] split = StringParser.splitByLevel(StringParser.reduceLevel(input));
		if (split.length == 2) {
			HashItemType itemStack1 = getHashItem(split[0]);
			if (itemStack1 == null) {
				exceptionInvalidFormat(split[0]);
				return null;
			}
			HashItemType itemStack2 = getHashItem(split[1]);
			if (itemStack2 == null) {
				exceptionInvalidFormat(split[1]);
				return null;
			}
			return new HashPairedItemType(itemStack1, itemStack2);
		} else {
			exceptionInvalidNumberOfArgument(input);
			return null;
		}
	}
	
	/**
	 * 
	 * @param input
	 *            : (modid:itemname, number, damage)
	 * @return
	 */
	public static ItemStack getItemStack(String input) {
		String[] split = StringParser.splitByLevel(StringParser.reduceLevel(input));
		if (split.length == 1) {
			Item item = (Item) Item.itemRegistry.getObject(split[0]);
			if (item == null) {
				exceptionNameDoesntExist(split[0]);
				return null;
			}
			return new ItemStack(item);
		} else if (split.length == 2) {
			Item item = (Item) Item.itemRegistry.getObject(split[0]);
			if (item == null) {
				exceptionNameDoesntExist(split[0]);
				return null;
			}
			return new ItemStack(item, Integer.parseInt(split[1]));
		} else if (split.length == 3) {
			Item item = (Item) Item.itemRegistry.getObject(split[0]);
			if (item == null) {
				exceptionNameDoesntExist(split[0]);
				return null;
			}
			return new ItemStack(item, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
		} else {
			exceptionInvalidNumberOfArgument(input);
			return null;
		}
	}
	
	/**
	 * 
	 * @param input
	 *            : (modid:itemname,damage)
	 * @return
	 */
	public static HashItemType getHashItem(String input) {
		String[] split = StringParser.splitByLevel(StringParser.reduceLevel(input));
		if (split.length == 1) {
			Item item = (Item) Item.itemRegistry.getObject(split[0]);
			if (item == null) {
				exceptionNameDoesntExist(split[0]);
				return null;
			}
			return new HashItemType(item);
		} else if (split.length == 2) {
			Item item = (Item) Item.itemRegistry.getObject(split[0]);
			if (item == null) {
				exceptionNameDoesntExist(split[0]);
				return null;
			}
			return new HashItemType(item, Integer.parseInt(split[1]));
		} else {
			exceptionInvalidNumberOfArgument(input);
			return null;
		}
	}
	
	public static void exceptionInvalidNumberOfArgument(String input) {
		CustomAnvilRecipes.logger.warn("Invalid number of arguments : " + input);
	}

	public static void exceptionNameDoesntExist(String name) {
		CustomAnvilRecipes.logger.warn(name + " doesn't exist.");
	}

	public static void exceptionInvalidFormat(String argument) {
		CustomAnvilRecipes.logger.warn(argument + " is invalid.");
	}

	public static void exceptionEmptyList(String input) {
		CustomAnvilRecipes.logger.warn(input + " is an empty list.");
	}
}
