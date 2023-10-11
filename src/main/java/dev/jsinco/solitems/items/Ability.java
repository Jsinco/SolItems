package dev.jsinco.solitems.items;

public enum Ability {
    PROJECTILE_LAUNCH, // Mapped, Offhand supported
    PROJECTILE_LAND, // Mapped, Converted to persistent data container
    CROSSBOW_LOAD,  // Mapped TODO: Add support for offhand
    RIGHT_CLICK, // Mapped, full equipment support
    LEFT_CLICK, // Mapped, full equipment support
    BREAK_BLOCK, // Mapped
    PLACE_BLOCK,
    ENTITY_DAMAGE, // Mapped
    ENTITY_DEATH, // Mapped
    FISH, // Mapped, Offhand supported
    SWAP_HAND, // Mapped
    DROP_ITEM, // Mapped
    ELYTRA_BOOST, // Mapped
    ARMOR_SWAP,
    RUNNABLE, // Mapped, full equipment support
    PLAYER_CROUCH // Mapped, full equipment support
}
