package com.iglossolalia.foundry.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
sealed class RulesItem {
    abstract val id: String
    abstract val name: String
    abstract val img: String?
    abstract val sort: Int?
    abstract val system: JsonObject

    /** A general special ability, offensive/defensive action, or free action a creature can use. */
    @Serializable
    @SerialName("action")
    data class Action(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** An NPC-only strike/attack entry (the NPC-sheet analogue of a PC's equipped weapon). */
    @Serializable
    @SerialName("melee")
    data class Melee(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** A carried/wielded weapon item (as opposed to the derived `melee` strike entry). */
    @Serializable
    @SerialName("weapon")
    data class Weapon(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** Worn armor or a shield. */
    @Serializable
    @SerialName("armor")
    data class Armor(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** General worn/carried gear that isn't a weapon, armor, consumable, or treasure. */
    @Serializable
    @SerialName("equipment")
    data class Equipment(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** A single-use item: potion, scroll, elixir, etc. */
    @Serializable
    @SerialName("consumable")
    data class Consumable(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** Currency or generic valuables (coin, gems, art objects). */
    @Serializable
    @SerialName("treasure")
    data class Treasure(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** A container item holding other items (rare on NPCs, seen on e.g. merchants/pack-bearers). */
    @Serializable
    @SerialName("backpack")
    data class Backpack(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** A spellcasting entry (innate/prepared/spontaneous/ritual) that groups the creature's spells. */
    @Serializable
    @SerialName("spellcastingEntry")
    data class SpellcastingEntry(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** An individual spell, linked to a parent spellcasting entry via system.location. */
    @Serializable
    @SerialName("spell")
    data class Spell(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** A temporary or ongoing effect (buffs, auras, self-applied conditions from an ability, etc.). */
    @Serializable
    @SerialName("effect")
    data class Effect(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** A rules-defined status condition (e.g. frightened, prone) baked into the actor's items. */
    @Serializable
    @SerialName("condition")
    data class Condition(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** An ongoing disease/curse/poison-track affliction the creature inflicts or suffers. */
    @Serializable
    @SerialName("affliction")
    data class Affliction(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** A Lore skill entry (e.g. "Dungeoneering Lore"), modeled as an item rather than a fixed skill. */
    @Serializable
    @SerialName("lore")
    data class Lore(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
    ) : RulesItem()

    /** Fallback bucket for any type not in the set above (new content, homebrew, or a schema I haven't seen). */
    @Serializable
    data class Unknown(
        @SerialName("_id") override val id: String,
        override val name: String,
        override val img: String? = null,
        override val sort: Int? = null,
        override val system: JsonObject,
        val type: String,
    ) : RulesItem()
}

/**
 * Minimal envelope for the parts of an NPC actor document relevant to
 * extracting its items array. Extend as needed (system.attributes, etc.).
 */
@Serializable
data class NpcActor(
    @SerialName("_id") val id: String,
    val name: String,
    val type: String, // expected to be "npc" for documents this model targets
    val img: String? = null,
    val items: List<RulesItem> = emptyList(),
)
