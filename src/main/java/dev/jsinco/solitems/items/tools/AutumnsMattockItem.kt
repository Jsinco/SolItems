package dev.jsinco.solitems.items.tools

import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.items.CreateItem
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import dev.jsinco.solitems.util.AbilityUtil
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class AutumnsMattockItem : CustomItem {

    companion object {
        val plugin: SolItems = SolItems.getPlugin()

        val oreColors: Map<Material, Color> = mapOf(
            Material.COAL to Color.fromRGB(33, 34, 31),
            Material.RAW_COPPER to Color.fromRGB(179, 92, 62),
            Material.LAPIS_LAZULI to Color.fromRGB(16, 67, 169),
            Material.RAW_IRON to Color.fromRGB(216, 175, 147),
            Material.RAW_GOLD to Color.fromRGB(191, 154, 31),
            Material.REDSTONE to Color.fromRGB(171, 1, 3),
            Material.DIAMOND to Color.fromRGB(124, 208, 200),
            Material.EMERALD to Color.fromRGB(43, 156, 82),
            // Nether ores
            Material.GOLD_NUGGET to Color.fromRGB(191, 154, 31),
            Material.QUARTZ to Color.fromRGB(185, 160, 154),
            Material.ANCIENT_DEBRIS to Color.fromRGB(77, 56, 50)
        )
    }

    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateItem(
            "&#cd3c33&lA&#d2452e&lu&#d74e29&lt&#dc5724&lu&#e16120&lm&#e66a1b&ln&#eb7316&l'&#f07c11&ls &#f28410&lM&#f38d0e&la&#f5950d&lt&#f69e0c&lt&#f8a60b&lo&#f9af09&lc&#fbb708&lk",
            mutableListOf("&#e95e10F&#eb670fu&#ed700el&#ee790el &#f0820dH&#f28b0ca&#f4930br&#f69c0av&#f7a50ae&#f9ae09s&#fbb708t"),
            mutableListOf("Breaking ores with this pickaxe", "will occasionally yield a", "surplus amount of drops"),
            Material.NETHERITE_PICKAXE,
            mutableListOf("autumnsmattock"),
            mutableMapOf(Enchantment.DURABILITY to 10, Enchantment.LOOT_BONUS_BLOCKS to 6, Enchantment.MENDING to 1, Enchantment.DIG_SPEED to 7)
        )
        item.tier = "&#c46bfb&lH&#c86eee&la&#cd71e2&ll&#d174d5&ll&#d677c8&lo&#da7abc&lm&#de7daf&la&#e380a2&lr&#e78395&le&#eb8689&ls &#f0897c&l2&#f48c6f&l0&#f98f63&l2&#fd9256&l3"
        return Pair("autumnsmattock", item.createItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        if (Random.nextInt(1000) > 2) return false
        when (type) {
            Ability.BREAK_BLOCK -> {
                val blockBreakEvent = event as BlockBreakEvent
                fullHarvest(blockBreakEvent.block, blockBreakEvent.block.drops)
            }

            else -> return false
        }
        return true
    }

    private fun fullHarvest(block: Block, drops: Collection<ItemStack>) {
        val item = AbilityUtil.findMostCommonItem(drops)

        if (oreColors.containsKey(item.type)) {
            block.world.spawnParticle(
                Particle.REDSTONE, block.location, 100, 0.5, 0.5, 0.5,
                Particle.DustOptions(oreColors[item.type]!!, 1f)
            )
            block.world.playSound(block.location, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, 1f, 1f)
            if (Random.nextBoolean()) {
                topHarvestAnimation(
                    block.location, item.type,
                    Particle.DustOptions(oreColors[item.type]!!, 1f)
                )
            }
            val amt = if (item.type == Material.ANCIENT_DEBRIS) 3 else 90
            block.world.dropItem(block.location.add(Random.nextDouble(0.1), Random.nextDouble(0.1), Random.nextDouble(0.1)), ItemStack(item.type, amt))
        }
    }

    private fun topHarvestAnimation(location: Location, material: Material, dustOptions: Particle.DustOptions) {
        val loc = location.add(0.0, 0.2, 0.0).toCenterLocation()
        val period: Long = if (material == Material.ANCIENT_DEBRIS) 20 else 8
        val task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            loc.world.dropItem(loc, ItemStack(material))
            loc.world.spawnParticle(Particle.REDSTONE, loc, 30, 0.2, 0.2, 0.2, dustOptions)
        }, 0, period)

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            Bukkit.getScheduler().cancelTask(task)
        }, 150)
    }

}