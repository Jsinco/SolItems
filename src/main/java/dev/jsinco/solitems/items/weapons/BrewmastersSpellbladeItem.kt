package dev.jsinco.solitems.items.weapons

import dev.jsinco.solitems.items.CreateItem
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random

class BrewmastersSpellbladeItem : CustomItem {

    companion object {
        val opponentEffects: List<PotionEffectType> = listOf(
            PotionEffectType.BLINDNESS,
            PotionEffectType.CONFUSION,
            PotionEffectType.HARM,
            PotionEffectType.HUNGER,
            PotionEffectType.POISON,
            PotionEffectType.SLOW,
            PotionEffectType.SLOW_DIGGING,
            PotionEffectType.UNLUCK,
            PotionEffectType.WEAKNESS,
            PotionEffectType.WITHER
        )
        val attackerEffects: List<PotionEffectType> = listOf(
            PotionEffectType.DAMAGE_RESISTANCE,
            PotionEffectType.FAST_DIGGING,
            PotionEffectType.FIRE_RESISTANCE,
            PotionEffectType.HEAL,
            PotionEffectType.INCREASE_DAMAGE,
            PotionEffectType.INVISIBILITY,
            PotionEffectType.JUMP,
            PotionEffectType.NIGHT_VISION,
            PotionEffectType.REGENERATION,
            PotionEffectType.SATURATION,
            PotionEffectType.SPEED,
            PotionEffectType.WATER_BREATHING
        )
    }

    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateItem(
            "&#813f73&lB&#874976&lr&#8d537a&le&#935d7d&lw&#996780&lm&#9f7184&la&#a57b87&ls&#ab858a&lt&#b18f8e&le&#b79991&lr&#bda394&l'&#c0a68f&ls &#c1a281&lS&#c19f72&lp&#c29b64&le&#c29756&ll&#c39447&ll&#c39039&lb&#c48c2b&ll&#c4881d&la&#c5850e&ld&#c58100&le",
            mutableListOf("&#ab6982H&#b0706de&#b57759x&#bb7d44x&#c08430e&#c58b1bd"),
            mutableListOf("This tool bestows useful potion effects", "to the wielder and harmful potion effects", "to their opponent"),
            Material.NETHERITE_SWORD,
            mutableListOf("brewmastersspellblade"),
            mutableMapOf(Enchantment.DAMAGE_ALL to 8, Enchantment.DAMAGE_ARTHROPODS to 9, Enchantment.DURABILITY to 10, Enchantment.LOOT_BONUS_MOBS to 5, Enchantment.MENDING to 1)
        )
        item.tier = "&#c46bfb&lH&#c86eee&la&#cd71e2&ll&#d174d5&ll&#d677c8&lo&#da7abc&lm&#de7daf&la&#e380a2&lr&#e78395&le&#eb8689&ls &#f0897c&l2&#f48c6f&l0&#f98f63&l2&#fd9256&l3"
        return Pair("brewmastersspellblade", item.createItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        val entityDamageEvent: EntityDamageByEntityEvent? = event as? EntityDamageByEntityEvent

        when (type) {
            Ability.ENTITY_DAMAGE -> {
                if (Random.nextInt(100) >= 7) return false

                if (Random.nextBoolean()) {
                    addEffect(opponentEffects.random(), entityDamageEvent!!.entity as LivingEntity)
                } else {
                    addEffect(attackerEffects.random(), player)
                }
            }
            else -> return false
        }
        return true
    }

    private fun addEffect(potionEffectType: PotionEffectType, entity: LivingEntity) {
        if (Random.nextBoolean()) {
            entity.world.spawnParticle(Particle.SPELL_WITCH, entity.location, 25, 0.5, 0.5, 0.5, 0.1)
            entity.world.playSound(entity.location, Sound.ITEM_BOTTLE_FILL, 0.5f, 1f)
        } else {
            entity.world.spawnParticle(Particle.WAX_ON, entity.location, 25, 0.5, 0.5, 0.5, 0.1)
            entity.world.playSound(entity.location, Sound.BLOCK_BREWING_STAND_BREW, 0.5f, 0.82f)
        }
        entity.addPotionEffect(potionEffectType.createEffect(200, 1))
    }
}