package frgp.utn.edu.ar.quepasa.data.model.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): LocalDateTime {
        try {
            if (typeOfT == LocalDateTime::class.java) {
                return LocalDateTime.parse(json.asString)
            }
        } catch (e: DateTimeParseException) {
            throw JsonParseException(e)
        }
        throw JsonParseException("Unsupported type: $typeOfT")
    }
}
