package dev.jsinco.solitems.manager;

public enum Ability {
    // TODO: Add Runnable types

    PROJECTILE_LAUNCH, // Mapped, Offhand supported
    PROJECTILE_LAND, // Mapped, Converted to persistent data container
    CROSSBOW_LOAD,  // Mapped
    RIGHT_CLICK, // Mapped, full equipment support
    LEFT_CLICK, // Mapped, full equipment support
    GENERIC_INTERACT, // Mapped, full equipment support
    BREAK_BLOCK, // Mapped
    PLACE_BLOCK, // Mapped
    ENTITY_DAMAGE, // Mapped
    ENTITY_DEATH, // Mapped
    FISH, // Mapped, Offhand supported
    SWAP_HAND, // Mapped
    DROP_ITEM, // Mapped
    ELYTRA_BOOST, // Mapped
    ARMOR_SWAP,
    RUNNABLE, // Mapped, full equipment support
    PLAYER_CROUCH, // Mapped, full equipment support
    ENTITY_DAMAGE_BY_SELF,
    CHAT,
    MOVE,
    CONSUME_ITEM,
    ENTITY_CHANGE_BLOCK,
    POTION_EFFECT
}
