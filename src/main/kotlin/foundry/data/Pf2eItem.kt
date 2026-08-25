package com.iglossolalia.foundry.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class Pf2eItem(
    @SerialName("_id") override val id: String,
    override val name: String,
    override val img: String = "",
    override val type: String,
    val folder: String? = null,
    val sort: Int = 0,
    val system: PhysicalItemSystem,
    val flags: JsonObject = JsonObject(emptyMap()),
    @SerialName("_stats") val stats: JsonObject? = null
) : Pf2eObject

/**
 * Union of the `system` fields observed across weapon/armor/consumable/equipment/
 * treasure/backpack/ammo/shield/kit/melee documents. Fields that only apply to some
 * subtypes are nullable; check [Pf2eItem.type] to know which are meaningful. Common
 * fields (description, price, bulk, level, traits, rules, publication, quantity, hp,
 * hardness, material, containerId, size) apply to (almost) every subtype.
 */
@Serializable
data class PhysicalItemSystem(
    val description: DescriptionBlock,
    val price: Price = Price(),
    val bulk: Bulk = Bulk(),
    /** Absent on kit documents specifically; present on every other physical item type. */
    val level: IntValue? = null,
    val traits: Traits,
    val rules: List<JsonObject> = emptyList(),
    val publication: Publication,
    val quantity: Int = 1,
    val hp: Hp? = null,
    val hardness: Int? = null,
    val material: Material? = null,
    val containerId: String? = null,
    val size: String? = null,
    val baseItem: String? = null,
    val usage: ItemUsage? = null,
    val grade: String? = null,

    // --- weapon / melee (strike) specific ---
    val category: String? = null,
    val group: String? = null,
    val damage: WeaponDamage? = null,
    val range: Int? = null,
    val reload: ReloadValue? = null,
    val runes: Runes? = null,
    val splashDamage: JsonElement? = null,
    val ammo: WeaponAmmo? = null,
    val meleeUsage: MeleeUsage? = null,
    val bonus: IntValue? = null,
    val bonusDamage: IntValue? = null,
    val specific: ItemSpecific? = null,

    // --- armor / shield specific ---
    val acBonus: Int? = null,
    val dexCap: Int? = null,
    val checkPenalty: Int? = null,
    val speedPenalty: Int? = null,
    val strength: Int? = null,

    // --- consumable specific ---
    val uses: ItemUses? = null,
    val spell: JsonObject? = null,

    // --- ammo specific ---
    val craftableAs: List<String>? = null,

    // --- backpack (container) specific ---
    val stowing: Boolean? = null,
    val collapsed: Boolean? = null,
    val equippedBulk: StringValue? = null,

    // --- kit specific ---
    /** Recursive tree of sub-items packaged in a kit; keyed by random Foundry id. */
    val items: Map<String, KitItemEntry>? = null,

    // --- rarely-populated apex-item / subitem extras ---
    val apex: ApexAttribute? = null,
    val subitems: List<JsonObject> = emptyList()
)

@Serializable
data class ItemUsage(
    val value: String = "",
    val canBeAmmo: Boolean? = null
)

@Serializable
data class WeaponDamage(
    val dice: Int = 1,
    val die: String? = null,
    val damageType: String = "",
    val persistent: JsonElement? = null
)

@Serializable
data class ReloadValue(val value: String? = null)

@Serializable
data class Runes(
    val potency: Int = 0,
    val striking: Int? = null,
    val resilient: Int? = null,
    val reinforcing: Int? = null,
    val property: List<String> = emptyList()
)

@Serializable
data class WeaponAmmo(
    val baseType: String? = null,
    val builtIn: Boolean = false,
    val capacity: Int? = null
)

@Serializable
data class MeleeUsage(
    val group: String? = null,
    val damage: WeaponDamage? = null,
    val traits: List<String> = emptyList()
)

@Serializable
data class ItemSpecific(
    val material: JsonElement? = null,
    val runes: JsonElement? = null,
    val integrated: JsonElement? = null,
    val value: Boolean? = null
)

@Serializable
data class ItemUses(
    val value: Int = 0,
    val max: Int = 0,
    val autoDestroy: Boolean = false
)

@Serializable
data class ApexAttribute(val attribute: String = "")

/** Recursive entry of `kit.system.items`: `{ img, isContainer, quantity, uuid, items: {...} }`. */
@Serializable
data class KitItemEntry(
    val name: String? = null,
    val img: String? = null,
    val isContainer: Boolean = false,
    val quantity: Int = 1,
    val uuid: String? = null,
    val items: Map<String, KitItemEntry> = emptyMap()
)

