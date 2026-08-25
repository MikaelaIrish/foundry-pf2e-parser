package com.iglossolalia.foundry.data

import com.iglossolalia.foundry.parser.FlexibleIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
@SerialName("npc")
data class Pf2eNpc(
    @SerialName("_id") override val id: String,
    override val name: String,
    override val img: String = "",
    override val type: String = "npc",
    val sort: Int = 0,
    val folder: String? = null,
    val items: List<RulesItem> = emptyList(),
    val system: NpcSystem,
    val flags: JsonObject = JsonObject(emptyMap()),
    val prototypeToken: PrototypeToken? = null,
    @SerialName("_stats") val stats: JsonObject? = null
) : Pf2eObject

@Serializable
data class PrototypeToken(val name: String? = null)

@Serializable
data class NpcSystem(
    val abilities: Map<String, AbilityScore> = emptyMap(),
    val attributes: NpcAttributes,
    val details: NpcDetails,
    val initiative: NpcInitiative? = null,
    val perception: NpcPerception,
    val resources: NpcResources? = null,
    val saves: NpcSaves,
    val skills: Map<String, NpcSkill> = emptyMap(),
    val traits: Traits,
    //Not sure this is used
    val spellcasting: JsonObject? = null,
    //Not sure this is used
    val customModifiers: JsonObject? = null,
    //This won't be used for creation I think
    val statusEffects: List<JsonElement> = emptyList(),
    val flavourText: String? = null,
    val recallKnowledgeText: String? = null,
    val sidebarText: String? = null,
    val creatureType: String? = null
)

/** `system.abilities.<cha|con|dex|int|str|wis>` */
@Serializable
data class AbilityScore(
    val mod: Int = 0,
    val value: Int? = null
)

@Serializable
data class NpcAttributes(
    val ac: AcValue,
    val adjustment: String? = null,
    val allSaves: AllSaves? = null,
    val hardness: IntValue? = null,
    val hp: Hp,
    val immunities: List<ImmunityEntry> = emptyList(),
    val resistances: List<ResistanceEntry> = emptyList(),
    val weaknesses: List<WeaknessEntry> = emptyList(),
    val shield: NpcShield? = null,
    val speed: NpcSpeed
)

@Serializable
data class AcValue(val value: Int = 10, val details: String = "")

@Serializable
data class AllSaves(val value: String? = null)

@Serializable
data class NpcShield(
    val ac: Int = 0,
    val brokenThreshold: Int = 0,
    val hardness: Int = 0,
    val max: Int = 0,
    val value: Int = 0
)

@Serializable
data class NpcSpeed(
    val value: Int? = null,
    val details: String? = null,
    val special: String? = null,
    val otherSpeeds: List<OtherSpeed> = emptyList()
)

@Serializable
data class NpcDetails(
    val blurb: String = "",
    val level: IntValue,
    val languages: Languages? = null,
    val privateNotes: String = "",
    val publicNotes: String = "",
    val publication: Publication,
    val alliance: String? = null,
    val age: StringValue? = null,
    val background: StringValue? = null,
    val ethnicity: StringValue? = null,
    val gender: StringValue? = null,
    val height: StringValue? = null,
    val nationality: StringValue? = null,
    val rarity: StringValue? = null,
    val weight: StringValue? = null,
    val sidebarText: String? = null
)

@Serializable
data class Languages(
    val value: List<String> = emptyList(),
    val details: String = ""
)

@Serializable
data class NpcInitiative(val statistic: String = "perception")

@Serializable
data class NpcPerception(
    val details: String = "",
    val mod: Int = 0,
    val senses: List<Sense> = emptyList(),
    val value: Int? = null,
    val vision: Boolean? = null
)

@Serializable
data class NpcResources(
    val focus: FocusPoints? = null,
    val mythicPoints: IntValue? = null
)

@Serializable
data class FocusPoints(
    @Serializable(with = FlexibleIntSerializer::class) val max: Int? = null,
    val value: Int? = null
)

@Serializable
data class NpcSaves(
    val fortitude: SaveEntry,
    val reflex: SaveEntry,
    val will: SaveEntry
)

@Serializable
data class SaveEntry(
    val value: Int = 0,
    val saveDetail: String = ""
)

/** One entry of `system.skills`, keyed by skill slug (e.g. "acrobatics", "society"). */
@Serializable
data class NpcSkill(
    val base: Int? = null,
    val note: String? = null,
    val special: List<NpcSkillSpecial> = emptyList()
)

@Serializable
data class NpcSkillSpecial(
    val base: Int? = null,
    val label: String? = null,
    val predicate: List<String> = emptyList()
)
