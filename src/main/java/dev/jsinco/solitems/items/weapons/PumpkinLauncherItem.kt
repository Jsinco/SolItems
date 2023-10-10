package dev.jsinco.solitems.items.weapons

import dev.jsinco.solitems.manager.CustomItem
import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.items.Ability
import dev.jsinco.solitems.manager.CreateItem
import org.bukkit.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.*
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.persistence.PersistentDataType
import org.bukkit.scheduler.BukkitRunnable
import java.util.function.Consumer

class PumpkinLauncherItem : CustomItem {

    companion object {
        private val plugin: SolItems = SolItems.getPlugin()
    }

    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateItem(
            "&#fb782e&lP&#fb8535&lu&#fb933b&lm&#fba042&lp&#fcb34d&lk&#fdcb5c&li&#fee26c&ln &#fffa7b&lL&#fee26c&la&#fdcb5c&lu&#fcb34d&ln&#fca142&lc&#fd943c&lh&#fe8735&le&#ff7a2f&lr",
            mutableListOf("&#fb9638J&#fba737a&#fcb736c&#fcc836k&#fdd835-&#fde934O"),
            mutableListOf("Shoots explosive pumpkins"),
            Material.BOW,
            mutableListOf("pumpkinlauncher"),
            mutableMapOf(Enchantment.ARROW_DAMAGE to(8), Enchantment.ARROW_INFINITE to 1, Enchantment.ARROW_KNOCKBACK to 5, Enchantment.DURABILITY to 10, Enchantment.MENDING to 1)
        )
        return Pair("pumpkinlauncher", item.createItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        val projectileLaunch: ProjectileLaunchEvent? = event as? ProjectileLaunchEvent
        val projectileHit: ProjectileHitEvent? = event as? ProjectileHitEvent

        when (type) {
            Ability.PROJECTILE_LAUNCH -> {
                replaceProjectile(projectileLaunch!!.entity)
            }
            Ability.PROJECTILE_LAND -> {
                jackOLand(projectileHit!!.entity)
            }
            else -> return false
        }
        return true
    }

    private fun replaceProjectile(projectile: Projectile) {
        val player = projectile.shooter as? Player ?: return

        val armorStand: ArmorStand = projectile.world.spawnEntity(projectile.location, EntityType.ARMOR_STAND) as ArmorStand
        armorStand.equipment.helmet = ItemStack(Material.JACK_O_LANTERN);
        armorStand.isInvisible = true
        armorStand.isInvulnerable = false
        armorStand.setGravity(false);
        armorStand.persistentDataContainer.set(NamespacedKey(plugin, "pumpkinlauncher"), PersistentDataType.SHORT, 1)


        object : BukkitRunnable() {
            override fun run() {
                val loc: Location = projectile.location.add(0.0,-2.0,0.0);
                loc.setDirection(player.location.toVector().subtract(armorStand.location.toVector()).normalize());

                armorStand.teleport(loc);
                projectile.world.spawnParticle(Particle.LAVA, projectile.location, 3, 0.1, 0.1, 0.1, 0.1);
                projectile.world.playSound(projectile.location, Sound.ENTITY_WITCH_CELEBRATE, 1f, 1f);
                if (projectile.isDead){
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
        for (entity in projectile.getNearbyEntities(100.0,100.0,100.0)) {
            if (entity is Player) {
                entity.hideEntity(plugin, projectile)
            }
        }


        projectile.setGravity(false);
        projectile.persistentDataContainer.set(NamespacedKey(plugin, "pumpkinlauncher"), PersistentDataType.SHORT, 1)

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            if (!projectile.isDead) {
                jackOLand(projectile);
            }
        }, 100L);
    }

    private fun jackOLand(projectile: Projectile) {
        projectile.getNearbyEntities(4.0, 4.0, 4.0).forEach(Consumer { entity: Entity ->
            if (entity.persistentDataContainer.has(NamespacedKey(plugin, "pumpkinlauncher"), PersistentDataType.SHORT)) {
                entity.remove()
            }
        })

        projectile.world.createExplosion(projectile.location, 10f, false, false)
        projectile.remove()
    }


}