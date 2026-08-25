package com.iglossolalia.foundry.parser

import com.iglossolalia.foundry.data.Pf2eHazard
import com.iglossolalia.foundry.data.Pf2eItem
import com.iglossolalia.foundry.data.Pf2eNpc
import com.iglossolalia.foundry.data.Pf2eObject
import com.iglossolalia.foundry.data.Pf2eSpell
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Parses any pf2e pack JSON file into the appropriate [com.iglossolalia.foundry.data.Pf2eObject] subtype by peeking
 * at the top-level "type" field. Not using kotlinx's built-in class-discriminator
 * polymorphism because several distinct "type" values (weapon, armor, consumable, ...)
 * all map to the single [com.iglossolalia.foundry.data.Pf2eItem] class.
 */
object Pf2eObjectParser {

    val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
        explicitNulls = false
    }

    /** The root-level "type" values that deserialize to [com.iglossolalia.foundry.data.Pf2eNpc]/[com.iglossolalia.foundry.data.Pf2eHazard]/[com.iglossolalia.foundry.data.Pf2eSpell]. */
    private const val TYPE_NPC = "npc"
    private const val TYPE_HAZARD = "hazard"
    private const val TYPE_SPELL = "spell"

    /**
     * Every other physical-item document type this model covers. Informational only.
     */
    val ITEM_TYPES: Set<String> = setOf(
        "weapon", "armor", "consumable", "equipment", "treasure",
        "backpack", "ammo", "shield", "kit"
    )

    fun parse(text: String): Pf2eObject {
        val element = json.parseToJsonElement(text).jsonObject
        return parse(element)
    }

    fun parse(element: JsonObject): Pf2eObject {
        return when (val type = element["type"]?.jsonPrimitive?.contentOrNull) {
            TYPE_NPC -> json.decodeFromJsonElement<Pf2eNpc>(element)
            TYPE_HAZARD -> json.decodeFromJsonElement<Pf2eHazard>(element)
            TYPE_SPELL -> json.decodeFromJsonElement<Pf2eSpell>(element)
            else -> json.decodeFromJsonElement<Pf2eItem>( element)
        }
    }
}

/**
 * A small number of integer fields in this dataset (e.g. npc `hp.value`,
 * `resources.focus.max`) are inconsistently authored as either a JSON number or a
 * numeric string. This serializer accepts either.
 */
object FlexibleIntSerializer : KSerializer<Int?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("FlexibleInt", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): Int? {
        val input = decoder as? JsonDecoder ?: return decoder.decodeInt()
        return when (val element = input.decodeJsonElement()) {
            is JsonNull -> null
            is JsonPrimitive -> element.intOrNull
                ?: element.doubleOrNull?.toInt()
                ?: element.contentOrNull?.toIntOrNull()
            else -> null
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: Int?) {
        if (value == null) encoder.encodeNull() else encoder.encodeInt(value)
    }
}
