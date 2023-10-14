package dev.jsinco.solitems.items.magical

import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.items.CreateItem
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import dev.jsinco.solitems.util.AbilityUtil
import org.bukkit.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.*
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class ParrallelParadigmWandItem : CustomItem {

    companion object {
        private val plugin: SolItems = SolItems.getPlugin()
    }

    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateItem(
            "&#349532&lP&#489433&la&#5c9333&lr&#709234&lr&#849134&la&#999035&ll&#ad8e35&ll&#c18d36&le&#d58c36&ll &#e98b37&lP&#fd8a37&la&#f9844b&lr&#f47e5f&la&#f07873&ld&#eb7287&li&#e76c9b&lg&#e365af&lm &#de5fc3&lW&#da59d7&la&#d553eb&ln&#d14dff&ld",
            mutableListOf("&#49b12fP&#5ca34da&#70946ar&#838688a&#9778a6d&#aa6ac4i&#be5be1g&#d14dffm"),
            mutableListOf("Left-click to cast a spell", "", "Spells from this wand may", "vary, caution is advised!", "", "&c1 Lapis per spell"),
            Material.BLAZE_ROD,
            mutableListOf("parrallelparadigmwand"),
            mutableMapOf(Enchantment.DAMAGE_ALL to 5, Enchantment.DAMAGE_ARTHROPODS to 5, Enchantment.FIRE_ASPECT to 4)
        )
        item.tier = "&#c46bfb&lH&#c86eee&la&#cd71e2&ll&#d174d5&ll&#d677c8&lo&#da7abc&lm&#de7daf&la&#e380a2&lr&#e78395&le&#eb8689&ls &#f0897c&l2&#f48c6f&l0&#f98f63&l2&#fd9256&l3"
        return Pair("parrallelparadigmwand", item.createItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        when (type) {
            Ability.LEFT_CLICK -> {
                spawnWandProjectile(player)
            }

            Ability.PROJECTILE_LAND -> {
                event as ProjectileHitEvent
                val entity = event.hitEntity ?: return false
                if (entity is LivingEntity && entity.type != EntityType.ARMOR_STAND && !AbilityUtil.noDamagePermission(player, entity)) {
                    if (!takeLapisCost(player)) return false
                    player.playSound(player.location, Sound.ENTITY_EVOKER_CAST_SPELL, 1f, 1f)

                    when (Random.nextInt(1, 6)) {
                        1 -> ingniteEntity(entity)
                        2 -> potionEntity(entity)
                        3 -> tntEntity(entity)
                        4 -> swapEntityLocations(entity, player)
                        5 -> expEntity(entity)
                    }
                }
            }
            else -> return false
        }
        return true
    }

    fun takeLapisCost(player: Player): Boolean {
        if (player.inventory.contains(Material.LAPIS_LAZULI, 1)) {
            player.inventory.removeItem(ItemStack(Material.LAPIS_LAZULI, 1))
            return true
        }
        return false
    }

    private fun spawnWandProjectile(player: Player) {
        val snowball = player.launchProjectile(Snowball::class.java)
        snowball.setGravity(false)
        snowball.velocity = player.location.direction.multiply(3)
        snowball.persistentDataContainer.set(NamespacedKey(plugin, "parrallelparadigmwand"), PersistentDataType.SHORT, 1)
        player.hideEntity(plugin, snowball)

        object : BukkitRunnable() {
            override fun run() {
                if (snowball.isDead) {
                    cancel()
                }
                snowball.world.spawnParticle(Particle.FIREWORKS_SPARK, snowball.location, 4, 0.1, 0.1, 0.1, 0.0)

            }
        }.runTaskTimer(plugin, 0, 1)
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            if (!snowball.isDead) {
                snowball.remove()
            }
        }, 120L)
    }

    private fun ingniteEntity(entity: LivingEntity) {
        for (i in 0..60) {
            entity.world.spawnParticle(Particle.FLAME, entity.location, 1, 0.3, 0.3, 0.3, 0.2)
        }
        entity.world.playSound(entity.location, Sound.ENTITY_BLAZE_SHOOT, 1f, 0.9f)
        entity.fireTicks = 100
    }

    private fun potionEntity(entity: LivingEntity) {
        for (i in 0..60) {
            entity.world.spawnParticle(Particle.SPELL_WITCH, entity.location, 1, 0.3, 0.3, 0.3, 0.2)
        }
        entity.world.playSound(entity.location, Sound.ITEM_BOTTLE_FILL, 1f, 0.9f)
        entity.addPotionEffect(PotionEffect(PotionEffectType.values().random(), 100, 1, false, true, false))
    }

    private fun tntEntity(entity: LivingEntity) { // TODO: fake tnt?
        for (i in 0..3) {
            val random = Random.nextDouble(1.0)
            val random2 = Random.nextDouble(1.0)
            val tnt = entity.location.world.spawn(entity.location.add(random,2.0,random2), TNTPrimed::class.java)
            tnt.fuseTicks = 20
        }
        entity.world.playSound(entity.location, Sound.ENTITY_TNT_PRIMED, 1f, 0.9f)
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            entity.damage(30.0)
        }, 20)
    }

    private fun swapEntityLocations(entity: LivingEntity, entity2: LivingEntity) {
        val loc1 = entity.location
        val loc2 = entity2.location
        entity.teleport(loc2)
        entity2.teleport(loc1)
        entity.world.playSound(entity.location, Sound.ENTITY_WITCH_CELEBRATE, 1f, 0.9f)
        entity2.world.playSound(entity2.location, Sound.ENTITY_WITCH_CELEBRATE, 1f, 0.9f)
    }

    private fun expEntity(entity: Entity) {
        for (i in 0..3) {
            val random = Random.nextDouble(1.0)
            val random2 = Random.nextDouble(1.0)
            entity.world.spawnEntity(entity.location.add(random,3.0,random2), EntityType.THROWN_EXP_BOTTLE)
        }
    }
}