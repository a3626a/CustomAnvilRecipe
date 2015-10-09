package oortcloud.customanvilrecipes.api.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import oortcloud.customanvilrecipes.anvil.AnvilRecipeManager;
import oortcloud.customanvilrecipes.configuration.HashPairedItemType;
import oortcloud.customanvilrecipes.libs.References;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.DefaultOverlayRenderer;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.api.IStackPositioner;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class AnvilRecipeHandler extends TemplateRecipeHandler {
	public class CachedAnvilRecipe extends CachedRecipe {

		public PositionedStack ingred1;
		public PositionedStack ingred2;
		public PositionedStack result;
		public int xp;

		public CachedAnvilRecipe(ItemStack ingred1, ItemStack ingred2, ItemStack result, int xp) {
			ingred1.stackSize = 1;
			this.ingred1 = new PositionedStack(ingred1, 22, 36);
			this.ingred2 = new PositionedStack(ingred2, 71, 36);
			this.result = new PositionedStack(result, 129, 36);
			this.xp = xp;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(ingred1, ingred2));
		}

		@Override
		public PositionedStack getResult() {
			return result;
		}

	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(96, 36, 22, 20), "anvil"));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiRepair.class;
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("api.nei.hanlder.anvil");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("anvil") && getClass() == AnvilRecipeHandler.class) {
			HashMap<HashPairedItemType, Pair<Integer, ItemStack>> recipes = AnvilRecipeManager.getInstance().anvilMap;
			for (java.util.Map.Entry<HashPairedItemType, Pair<Integer, ItemStack>> recipe : recipes.entrySet())
				arecipes.add(new CachedAnvilRecipe(recipe.getKey().getLeft().toItemStack(), recipe.getKey().getRight().toItemStack(), recipe.getValue().getRight(), recipe.getValue().getLeft()));
		} else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		HashMap<HashPairedItemType, Pair<Integer, ItemStack>> recipes = AnvilRecipeManager.getInstance().anvilMap;
		for (java.util.Map.Entry<HashPairedItemType, Pair<Integer, ItemStack>> recipe : recipes.entrySet())
			if (NEIServerUtils.areStacksSameType(recipe.getValue().getRight(), result))
				arecipes.add(new CachedAnvilRecipe(recipe.getKey().getLeft().toItemStack(), recipe.getKey().getRight().toItemStack(), recipe.getValue().getRight(), recipe.getValue().getLeft()));
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		HashMap<HashPairedItemType, Pair<Integer, ItemStack>> recipes = AnvilRecipeManager.getInstance().anvilMap;
		for (java.util.Map.Entry<HashPairedItemType, Pair<Integer, ItemStack>> recipe : recipes.entrySet())
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey().getLeft().toItemStack(), ingredient)
					|| NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey().getRight().toItemStack(), ingredient)) {
				CachedAnvilRecipe arecipe = new CachedAnvilRecipe(recipe.getKey().getLeft().toItemStack(), recipe.getKey().getRight().toItemStack(), recipe.getValue().getRight(), recipe.getValue()
						.getLeft());
				arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingred1, arecipe.ingred2), ingredient);
				arecipes.add(arecipe);
			}
	}

	@Override
	public String getGuiTexture() {
		return "textures/gui/container/anvil.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "anvil";
	}

	@Override
	public void drawForeground(int recipe) {
		super.drawForeground(recipe);
        GL11.glDisable(GL11.GL_BLEND);
		FontRenderer fontRendererObj = GuiDraw.fontRenderer;
		int k = 8453920;
		String s = I18n.format("container.repair.cost", new Object[] { Integer.valueOf(((CachedAnvilRecipe)arecipes.get(recipe)).xp) });
		int l = -16777216 | (k & 16579836) >> 2 | k & -16777216;
		int i1 = 176 - 8 - fontRendererObj.getStringWidth(s);
		byte b0 = 57;
		fontRendererObj.drawString(s, i1, b0 + 1, l);
		fontRendererObj.drawString(s, i1 + 1, b0, l);
		fontRendererObj.drawString(s, i1 + 1, b0 + 1, l);
		fontRendererObj.drawString(s, i1, b0, k);
		 GL11.glEnable(GL11.GL_LIGHTING);
		super.drawForeground(recipe);
	}
}
