package data.database

import app.cash.sqldelight.ColumnAdapter
import com.devmike.Database
import domain.models.DrinkInstructionLanguages
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json





 val listOfInstructionsAdapter = object : ColumnAdapter<List<DrinkInstructionLanguages>, String> {

        private val json = Json

        override fun decode(databaseValue: String): List<DrinkInstructionLanguages> {
            return if (databaseValue.isEmpty()) {
                listOf()
            } else {
                try {
                    json.decodeFromString(databaseValue)
                } catch (e: Exception) {
                    throw IllegalStateException("Failed to decode JSON string: $databaseValue", e)
                }
            }
        }

        override fun encode(value: List<DrinkInstructionLanguages>): String {
            return json.encodeToString(value)
        }
    }


val sqlBooleanAdpater = object : ColumnAdapter<Boolean, Long> {
    override fun encode(value: Boolean): Long = if (value) 1 else 0
    override fun decode(databaseValue: Long): Boolean = databaseValue != 0L
}

