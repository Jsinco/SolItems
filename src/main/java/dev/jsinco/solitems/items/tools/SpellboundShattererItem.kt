package dev.jsinco.solitems.items.tools

import dev.jsinco.solitems.items.CreateItem
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import dev.jsinco.solitems.util.AbilityUtil
import dev.jsinco.solitems.util.Cuboid
import dev.jsinco.solitems.util.Util
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import java.util.Random

class SpellboundShattererItem : CustomItem {

    companion object {
        private val dustOptions: List<DustOptions> = listOf(
            DustOptions(Color.fromRGB(118, 0, 117), 1f),
            DustOptions(Color.fromRGB(245, 96, 1), 1f),
            DustOptions(Color.fromRGB(61, 143, 0), 1f)
        )
    }

    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateItem(
            "&#760075&lS&#840b68&lp&#92155b&le&#a0204e&ll&#ae2b41&ll&#bd3535&lb&#cb4028&lo&#d94b1b&lu&#e7550e&ln&#f56001&ld &#e16501&lS&#cc6a01&lh&#b87001&la&#a37501&lt&#8f7a00&lt&#7a7f00&le&#668500&lr&#518a00&le&#3d8f00&lr",
            mutableListOf("&#f56001B&#e9570cu&#de4f16r&#d24621s&#c73d2bt&#bb3436i&#b02c40n&#a4234bg &#991a55B&#8d1160r&#82096ae&#760075w"),
            mutableListOf("Breaking blocks with this pickaxe grants the", "chance to shatter nearby blocks in a 5x5 radius"),
            Material.NETHERITE_PICKAXE,
            mutableListOf("spellboundshatterer"),
            mutableMapOf(Enchantment.DIG_SPEED to 8, Enchantment.LOOT_BONUS_BLOCKS to 5, Enchantment.DURABILITY to 10, Enchantment.MENDING to 1)
        )
        item.tier = "&#c46bfb&lH&#c86eee&la&#cd71e2&ll&#d174d5&ll&#d677c8&lo&#da7abc&lm&#de7daf&la&#e380a2&lr&#e78395&le&#eb8689&ls &#f0897c&l2&#f48c6f&l0&#f98f63&l2&#fd9256&l3"
        return Pair("spellboundshatterer", item.createItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        val blockBreakEvent: BlockBreakEvent? = event as? BlockBreakEvent
        when (type) {
            Ability.BREAK_BLOCK -> {
                if (Random().nextInt(100) >= 7) return false
                shatterNearbyBlocks(blockBreakEvent!!.block)
            }
            else -> return false
        }
        return true
    }

    private fun shatterNearbyBlocks(block: Block) {
        val cuboid = Cuboid(block.location.add(2.0,2.0,2.0), block.location.add(-2.0,-2.0,-2.0))
        block.world.playSound(block.location, Sound.ENTITY_WITCH_AMBIENT, 0.5f, 1f)
        block.world.playSound(block.location, Sound.ENTITY_GENERIC_EXPLODE, 0.2f, 1f)
        for (b in cuboid.blockList()) {
            if (AbilityUtil.blockTypeBlacklist.contains(b.type)) continue

            if (Random().nextInt(50) <= 5) {
                b.world.spawnParticle(Particle.REDSTONE, block.location, 10, 0.5, 0.5, 0.5, 0.1, dustOptions.random())
            }
            b.breakNaturally()
            b.world.spawnParticle(Particle.BLOCK_DUST, b.location.add(0.5, 0.5, 0.5), 10, 0.5, 0.5, 0.5, 0.1, b.blockData)
        }
    }
}