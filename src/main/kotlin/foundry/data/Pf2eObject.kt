package com.iglossolalia.foundry.data

import com.iglossolalia.foundry.parser.FlexibleIntSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// =====================================================================================
// Entry point / polymorphic dispatch
// =====================================================================================

/** Common interface implemented by every root pf2e document type this model covers. */
sealed interface Pf2eObject {
    val id: String
    val name: String
    val img: String
    val type: String
}

// =====================================================================================
// Shared building blocks
// =====================================================================================

/** `{ "license": "OGL", "remaster": false, "title": "...", "authors"?: "..." }` */
@Serializable
data class Publication(
    val license: String = "",
    val remaster: Boolean = false,
    val title: String = "",
    val authors: String? = null
)

/** `{ "value": [...traits], "rarity": "common", "size"?: {...}, "traditions"?: [...] }` */
@Serializable
data class Traits(
    val value: List<String> = emptyList(),
    val rarity: String = "common",
    val size: SizeValue? = null,
    val traditions: List<String> = emptyList(),
    val otherTags: List<String> = emptyList(),
    val selected: JsonElement? = null,
    val config: JsonElement? = null,
    //Could
    val integrated: JsonElement? = null
)

@Serializable
data class SizeValue(val value: String = "med")

/** `{ "value": "<html description>", "gm"?: "<gm-only html>" }` */
@Serializable
data class DescriptionBlock(
    val value: String = "",
    val gm: String? = null
)

/** `{ "value": N }` wrapper used for level, hardness, etc. */
@Serializable
data class IntValue(@Serializable(with = FlexibleIntSerializer::class) val value: Int? = null)

/** `{ "value": "..." }` wrapper used for a handful of free-text detail fields. */
@Serializable
data class StringValue(val value: String = "")

/** Hit points block shared by actors (npc/hazard) and physical items, shapes differ slightly. */
@Serializable
data class Hp(
    @Serializable(with = FlexibleIntSerializer::class) val value: Int? = null,
    val max: Int? = null,
    val temp: Int? = null,
    val tempmax: Int? = null,
    val details: String = ""
)

/** `{ "value": {"gp": 5, "sp": 2, ...} }` - denomination -> amount. */
@Serializable
data class Price(val value: Map<String, Int> = emptyMap(), val per: Int? = null)

/** Bulk. Physical items: `{"value": N}`. Backpacks additionally carry capacity info. */
@Serializable
data class Bulk(
    val value: Double? = null,
    val capacity: Double? = null,
    val heldOrStowed: Double? = null,
    val ignored: Double? = null
)

@Serializable
data class Material(
    val type: String? = null,
    val grade: String? = null,
    val effects: List<String> = emptyList()
)

/** Immunity entry: `{"type": "fire", "exceptions"?: [...] }` */
@Serializable
data class ImmunityEntry(
    val type: String,
    val exceptions: List<String> = emptyList()
)

/** Resistance entry: `{"type": "fire", "value": 5, "exceptions"?: [...], "doubleVs"?: [...] }` */
@Serializable
data class ResistanceEntry(
    val type: String,
    val value: Int = 0,
    val exceptions: List<String> = emptyList(),
    val doubleVs: List<String> = emptyList()
)

/** Weakness entry: `{"type": "cold-iron", "value": 5, "exceptions"?: [...] }` */
@Serializable
data class WeaknessEntry(
    val type: String,
    val value: Int = 0,
    val exceptions: List<String> = emptyList()
)

/** A single alternate movement speed, e.g. `{"type": "fly", "value": 30}`. */
@Serializable
data class OtherSpeed(
    val type: String = "",
    val value: Int = 0
)

/** A creature sense, e.g. `{"type": "darkvision"}` or `{"type": "scent", "acuity": "imprecise", "range": 30}`. */
@Serializable
data class Sense(
    val type: String = "",
    val acuity: String? = null,
    val range: Int? = null,
    val value: String? = null
)

// =====================================================================================
// Embedded items (npc.items[] / hazard.items[])
// =====================================================================================

