package com.iglossolalia.foundry.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
@SerialName("hazard")
data class Pf2eHazard(
    @SerialName("_id") override val id: String,
    override val name: String,
    override val img: String = "",
    override val type: String = "hazard",
    val folder: String? = null,
    val items: List<RulesItem> = emptyList(),
    val system: HazardSystem,
    val flags: JsonObject = JsonObject(emptyMap()),
    @SerialName("_stats") val stats: JsonObject? = null
) : Pf2eObject

@Serializable
data class HazardSystem(
    val attributes: HazardAttributes,
    val creatureType: String = "",
    val details: HazardDetails,
    val saves: HazardSaves,
    val statusEffects: List<JsonElement> = emptyList(),
    val traits: Traits,
    val flavourText: String? = null,
    val recallKnowledgeText: String? = null,
    val sidebarText: String? = null,
    val customModifiers: JsonObject? = null
)

@Serializable
data class HazardAttributes(
    val ac: IntValue,
    /** Observed as either a boolean or a free-text string (e.g. "encounter"); kept raw. */
    val emitsSound: JsonElement? = null,
    val hardness: Int = 0,
    val hasHealth: Boolean = false,
    val hp: Hp,
    val stealth: HazardStealth,
    val immunities: List<ImmunityEntry> = emptyList(),
    val resistances: List<ResistanceEntry> = emptyList(),
    val weaknesses: List<WeaknessEntry> = emptyList()
)

@Serializable
data class HazardStealth(
    val value: Int? = null,
    val details: String = ""
)

@Serializable
data class HazardDetails(
    val description: String = "",
    val disable: String = "",
    val isComplex: Boolean = false,
    val level: IntValue,
    val publication: Publication,
    val reset: String = "",
    val routine: String? = null
)

@Serializable
data class HazardSaves(
    val fortitude: SaveEntry? = null,
    val reflex: SaveEntry? = null,
    val will: SaveEntry? = null
)
