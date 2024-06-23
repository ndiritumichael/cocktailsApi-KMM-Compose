package data.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.devmike.CocktailsQueries
import com.devmike.Database
import data.database.listOfInstructionsAdapter
import data.database.sqlBooleanAdpater
import domain.models.DrinkDetailModel
import domain.models.DrinkModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class CocktailsDao(sqlDriver: SqlDriver) {

    private val database = Database(sqlDriver)
    private val cocktailsQueries:CocktailsQueries = database.cocktailsQueries


    fun saveFavoriteDrink(drinkModel: DrinkDetailModel){
        cocktailsQueries.transaction {
            cocktailsQueries.insertDrink(
                drinkModel.id,
                drinkModel.name,
                sqlBooleanAdpater.encode(drinkModel.isAlcoholic),
                drinkModel.drinkImage,
                listOfInstructionsAdapter.encode(drinkModel.instructions)
            )

            drinkModel.ingredient.forEach {
                cocktailsQueries.insertIngredient(it.name,it.measurements)
                cocktailsQueries.insertIngredientRelation(drinkModel.id,it.name)

            }
        }
    }

    val favCocktails = cocktailsQueries.getAllDrinks().asFlow().mapToList(Dispatchers.IO)


    fun deleteDrink(drinkModel: DrinkModel){

cocktailsQueries.transaction {
            cocktailsQueries.deleteDrink(drinkModel.id)
            cocktailsQueries.deleteDrinkRelation(drinkModel.id)
            cocktailsQueries.deleteUnusedIngredient()

        }

    }





}


