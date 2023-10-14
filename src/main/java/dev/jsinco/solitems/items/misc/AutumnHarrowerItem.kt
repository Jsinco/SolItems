package dev.jsinco.solitems.items.misc

import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.items.CreateItem
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.*
import org.bukkit.Particle.DustOptions
import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class AutumnHarrowerItem : CustomItem {

    companion object {
        private val plugin: SolItems = SolItems.getPlugin()
        private val crops: Map<Material, Color> = mapOf(
            Material.WHEAT to Color.fromRGB(220, 187, 101),
            Material.BEETROOT to Color.fromRGB(164, 39, 44),
            Material.CARROT to Color.fromRGB(255, 142, 9),
            Material.POTATO to Color.fromRGB(200, 151, 58),
            Material.NETHER_WART to Color.fromRGB(165, 36, 47)
        )
    }

    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateItem(
            "&#cd3c33&lA&#d3442b&lu&#da4c23&lt&#e0541b&lu&#e75b13&lm&#ec6a10&ln &#f07c11&lH&#f38d11&la&#f79f12&lr&#faac11&lr&#faaf0f&lo&#fbb10d&lw&#fbb40a&le&#fbb708&lr",
            mutableListOf("&#e95e10F&#eb670fu&#ed700el&#ee790el &#f0820dH&#f28b0ca&#f4930br&#f69c0av&#f7a50ae&#f9ae09s&#fbb708t"),
            mutableListOf("Breaking crops with this hoe", "will occasionally yield a", "surplus amount"),
            Material.NETHERITE_HOE,
            mutableListOf("autumnharrower"),
            mutableMapOf(Enchantment.MENDING to 1, Enchantment.DURABILITY to 10, Enchantment.LOOT_BONUS_BLOCKS to 5, Enchantment.DIG_SPEED to 8)
        )
        item.tier = "&#c46bfb&lH&#c86eee&la&#cd71e2&ll&#d174d5&ll&#d677c8&lo&#da7abc&lm&#de7daf&la&#e380a2&lr&#e78395&le&#eb8689&ls &#f0897c&l2&#f48c6f&l0&#f98f63&l2&#fd9256&l3"
        return Pair("autumnharrower", item.createItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        if (Random.nextInt(100) > 5) return false
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
        val item = findMostCommonItem(drops)

        if (crops.containsKey(item.type)) {
            block.world.spawnParticle(
                Particle.REDSTONE, block.location, 100, 0.5, 0.5, 0.5, DustOptions(crops[item.type]!!, 1f)
            )
            block.world.playSound(block.location, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, 1f, 1f)
            if (Random.nextBoolean()) {
                topHarvestAnimation(block.location, item.type, DustOptions(crops[item.type]!!, 1f))
            }
            block.world.dropItem(block.location.add(Random.nextDouble(0.1),Random.nextDouble(0.1),Random.nextDouble(0.1)),
                ItemStack(item.type, 300))
        }
    }

    private fun topHarvestAnimation(location: Location, material: Material, dustOptions: DustOptions) {
        val loc = location.add(0.0,0.2,0.0).toCenterLocation()
        val task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            loc.world.dropItem(loc, ItemStack(material))
            loc.world.spawnParticle(Particle.REDSTONE, loc, 30, 0.2, 0.2, 0.2, dustOptions)
        }, 0, 5)

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            Bukkit.getScheduler().cancelTask(task)
        }, 150)
    }

    // TODO: Move to Util?
    private fun findMostCommonItem(collection: Collection<ItemStack>): ItemStack {
        return collection.groupingBy { it }
            .eachCount()
            .maxBy { it.value }
            .key
    }
}