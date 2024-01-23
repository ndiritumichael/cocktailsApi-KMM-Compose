import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.devmike.Database

actual class DriverFactory {
    actual fun createDriver(): SqlDriver{
        return NativeSqliteDriver(Database.Schema, "cocktails.db")
    }
}

