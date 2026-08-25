package com.iglossolalia.foundry.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
@SerialName("spell")
data class Pf2eSpell(
    @SerialName("_id") override val id: String,
    override val name: String,
    override val img: String = "",
    override val type: String = "spell",
    val folder: String? = null,
    val sort: Int = 0,
    val system: SpellSystem,
    val flags: JsonObject = JsonObject(emptyMap()),
    @SerialName("_stats") val stats: JsonObject? = null
) : Pf2eObject

@Serializable
data class SpellSystem(
    val area: SpellArea? = null,
    val cost: StringValue = StringValue(),
    val counteraction: Boolean = false,
    /** Keyed by a random Foundry ID rather than an index; see file header. */
    val damage: Map<String, SpellDamagePartial> = emptyMap(),
    val defense: SpellDefense? = null,
    val description: DescriptionBlock,
    val duration: SpellDuration,
    val heightening: SpellHeightening? = null,
    val level: IntValue,
    val overlays: Map<String, SpellOverlay> = emptyMap(),
    val publication: Publication,
    val range: StringValue = StringValue(),
    val requirements: String = "",
    val ritual: SpellRitual? = null,
    val rules: List<JsonObject> = emptyList(),
    val target: StringValue = StringValue(),
    val time: StringValue = StringValue(),
    val traits: SpellTraits
)

@Serializable
data class SpellArea(
    val type: String? = null,
    val value: Int? = null,
    val details: String? = null
)

@Serializable
data class SpellDamagePartial(
    val formula: String = "",
    val type: String = "",
    /** "damage" or "healing". */
    val kinds: List<String> = emptyList(),
    /** e.g. "persistent", "splash"; null for a plain direct-damage entry. */
    val category: String? = null,
    val applyMod: Boolean = false,
    val materials: List<String> = emptyList()
)

@Serializable
data class SpellDefense(
    val passive: SpellDefensePassive? = null,
    val save: SpellDefenseSave? = null
)

@Serializable
data class SpellDefensePassive(val statistic: String = "ac")

@Serializable
data class SpellDefenseSave(
    val statistic: String = "",
    val basic: Boolean = false
)

@Serializable
data class SpellDuration(
    val value: String = "",
    val sustained: Boolean = false
)

@Serializable
data class SpellHeightening(
    /** "fixed" (flat effect per +1 rank, `damage` keyed by id -> dice string) or "interval". */
    val type: String? = null,
    val interval: Int? = null,
    val area: Int? = null,
    /** Only meaningful when [type] == "fixed": id -> dice string, e.g. "1d8". */
    val damage: Map<String, String>? = null,
    /** Only meaningful when [type] == "interval" or for fixed per-level overrides. */
    val levels: Map<String, SpellHeighteningLevel>? = null
)

@Serializable
data class SpellHeighteningLevel(
    val area: SpellArea? = null,
    val damage: Map<String, SpellDamagePartial>? = null,
    val range: StringValue? = null,
    val target: StringValue? = null,
    val time: StringValue? = null,
    val traits: SpellHeighteningTraits? = null
)

@Serializable
data class SpellHeighteningTraits(val value: List<String> = emptyList())

/**
 * A named alternate casting of the spell (e.g. elemental-damage-type variants).
 * `system` here mirrors a partial [SpellSystem] but every field is optional since an
 * overlay only overrides a subset; kept as raw JSON to avoid duplicating SpellSystem
 * with every field nullable.
 */
@Serializable
data class SpellOverlay(
    @SerialName("_id") val id: String? = null,
    val name: String? = null,
    val overlayType: String = "override",
    val sort: Int = 0,
    val system: JsonObject = JsonObject(emptyMap())
)

@Serializable
data class SpellRitual(
    val primary: SpellRitualPrimary? = null,
    val secondary: SpellRitualSecondary? = null
)

@Serializable
data class SpellRitualPrimary(val check: String = "")

@Serializable
data class SpellRitualSecondary(
    val casters: Int = 0,
    val checks: String = ""
)

@Serializable
data class SpellTraits(
    val value: List<String> = emptyList(),
    val rarity: String = "common",
    val traditions: List<String> = emptyList(),
    /** Rare (~2%) editor artifact for trait pickers; kept raw. */
    val selected: JsonElement? = null
)
