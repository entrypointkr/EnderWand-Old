package kr.entree.enderwand.bukkit.recipe

import kr.entree.enderwand.bukkit.item.isNotAir
import kr.entree.enderwand.bukkit.item.item
import kr.entree.enderwand.bukkit.material.base
import kr.entree.enderwand.bukkit.material.color
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapelessRecipe

/**
 * Created by JunHyung Lim on 2020-01-16
 */
val RECIPE_CACHES = mutableMapOf<Material, List<Recipe>>()

val ItemStack.recipes
    get() = if (isNotAir()) {
        type.recipes
    } else emptyList()
val Material.recipes
    get() = if (this != Material.AIR) {
        RECIPE_CACHES.getOrPut(this) {
            val recipes = Bukkit.recipeIterator().asSequence().filter {
                it.result.type == this
            }.filterNotNull().toMutableList()
            if (recipes.isEmpty()) {
                val base = base
                val dye = color.material
                if (base != null && dye != null) {
                    recipes += ShapelessRecipe(key, item(this)).also {
                        it.addIngredient(base)
                        it.addIngredient(dye)
                    }
                }
            }
            recipes
        }
    } else emptyList()

@Suppress("DEPRECATION")
val RecipeChoice.itemType
    get() = (this as? RecipeChoice.MaterialChoice)?.choices?.minBy {
        it.name.length
    } ?: itemStack.type