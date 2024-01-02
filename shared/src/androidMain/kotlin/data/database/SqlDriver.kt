import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "test.db")
    }
}

