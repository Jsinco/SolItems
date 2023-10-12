package dev.jsinco.solitems.manager

import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.candles.action.SaturationCandle
import dev.jsinco.solitems.candles.effects.HeroOfTheVillageCandle
import dev.jsinco.solitems.candles.effects.StrengthCandle
import dev.jsinco.solitems.candles.effects.WitherCandle
import dev.jsinco.solitems.items.armor.*
import dev.jsinco.solitems.items.misc.*
import dev.jsinco.solitems.items.tools.*
import dev.jsinco.solitems.items.weapons.*


class ItemManager(val plugin: SolItems) {

    companion object {
        val customItems: MutableMap<String, CustomItem> = mutableMapOf()
    }

    init { // TODO: find a way to replace with reflection
        // Weapons
        addCustomItemClass(CannonBowItem())
        addCustomItemClass(PumpkinLauncherItem())
        addCustomItemClass(RemakerItem())
        addCustomItemClass(RubyLongbowItem())
        addCustomItemClass(SoulEaterItem())
        addCustomItemClass(SunlightBladeItem())
        addCustomItemClass(SunlightScytheItem())
        addCustomItemClass(ViperHatchetItem())
        // Tools
        addCustomItemClass(CrescentMoonHatchetItem())
        addCustomItemClass(DarkMoonMattockItem())
        addCustomItemClass(DarkMoonShovelItem())
        addCustomItemClass(MagmaticPickaxeItem())
        addCustomItemClass(MagmaticShovelItem())
        addCustomItemClass(MistralMattockItem())
        addCustomItemClass(MoonstoneMattockItem())
        addCustomItemClass(SapphireShardItem())
        addCustomItemClass(StarstoneDrillerItem())
        addCustomItemClass(StellarisMattockItem())
        addCustomItemClass(StellarisSpadeItem())
        addCustomItemClass(StellarisTomahawkItem())
        // Misc
        addCustomItemClass(ArcaneAnglerItem())
        addCustomItemClass(CozyCampFireItem())
        addCustomItemClass(MoonshineFallowItem())
        addCustomItemClass(NeptunianLanceItem())
        addCustomItemClass(SatchelItem())
        addCustomItemClass(SollureItem())
        addCustomItemClass(SweetMelonItem())
        // TODO: Add OmniTool
        // Armor
        addCustomItemClass(EtherealWingsItem())
        addCustomItemClass(FlameguardGreavesItem())
        addCustomItemClass(LuminaLensesItem())
        addCustomItemClass(MoonWalkersItem())
        addCustomItemClass(NeonBootsItem())
        addCustomItemClass(NeptunesCrownItem())
        addCustomItemClass(OpticCuirassItem())
        addCustomItemClass(RibbonBootsItem())
        addCustomItemClass(RubyPinionsItem())
        addCustomItemClass(StarweaveAegisItem())
    }

    fun initializeCandleClasses() {
        // Effects
        addCustomItemClass(HeroOfTheVillageCandle())
        addCustomItemClass(StrengthCandle())
        addCustomItemClass(WitherCandle())
        // Actions
        addCustomItemClass(SaturationCandle())
    }

    /**
     * Adds a custom item class to the customItems map by the item's main NBT key
     * @param customItemClass The custom item class to add
     */
    fun addCustomItemClass(customItemClass: CustomItem) {
        val itemInfo = customItemClass.createItem()
        // NBT to Class
        customItems[itemInfo.first] = customItemClass
    }

}




/*
fun findInterfaceImplementations(interfaceClass: KClass<*>): Map<String, KClass<*>> {
        val implementations = mutableMapOf<String, KClass<*>>()

        val classLoader = Thread.currentThread().contextClassLoader
        val interfaceName = interfaceClass.qualifiedName

        val packageName = interfaceClass.java.getPackage().name
        val packagePath = packageName.replace(".", "/")

        val resources = classLoader.getResources(packagePath)

        while (resources.hasMoreElements()) {
            val resource = resources.nextElement()
            val file = File(resource.toURI())

            if (file.isDirectory) {
                val files = file.listFiles { dir, name ->
                    name.endsWith(".class")
                }

                files?.forEach { classFile ->
                    val className = packageName + "." + classFile.nameWithoutExtension
                    val clazz = Class.forName(className).javaClass.kotlin

                    if (interfaceClass.java.isAssignableFrom(clazz.java) && clazz != interfaceClass) {
                        implementations[className] = clazz
                    }
                }
            }
        }

        return implementations
    }
 */
/*
    //val customItemClassSet: Set<Class<out CustomItem>> = reflections.getSubTypesOf(CustomItem::class.java)
        //val subTypes: Set<Class<*>> = reflections[SubTypes.of(SomeType::class.java).asClass()]
        val serviceLoader: ServiceLoader<CustomItem> = ServiceLoader.load(CustomItem::class.java)
        for (customItemClass in serviceLoader) {
            Bukkit.broadcastMessage(customItemClass.javaClass.name)
        }

        for (classSet in customItemClassSet) {
            Bukkit.broadcastMessage(classSet.name)
            val customItemClass: CustomItem = classSet.getDeclaredConstructor().newInstance()
            val itemInfo = customItemClass.createItem(plugin)

            customItems[itemInfo.first] = customItemClass
        }
     */
/*
val interfaceImplementations = findInterfaceImplementations(CustomItem::class)
        for ((className, clazz) in interfaceImplementations) {
            Bukkit.getLogger().warning("Class Name: $className")
            Bukkit.getLogger().warning("Class Object: ${clazz.qualifiedName}")
        }
 */