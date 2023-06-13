package data.network

enum class UrlRoutes(val path: String, val queryKey: String? = null) {

    // search
    SearchByCockTailName("/api/json/v1/1/search.php", "s"),
    SearchCocktailByIngredient(BASEPATH + "filter.php", "i"),
    SearchIngredient("/api/json/v1/1/search.php", queryKey = "i"),

    // details
    GetCocktailByID(BASEPATH + "lookup.php", "i"),
    GetIngredientByID(BASEPATH + "lookup.php", "iid"),
    GetRandomCocktail(BASEPATH + "random.php"),

    // filter
    FilterAlcoholic(BASEPATH + "filter.php", "a"),
    FilterbyCategory(BASEPATH + "filter.php", "c"),
    FilterbyGlass(BASEPATH + "filter.php", "g"),

    // list
    ListCategories(BASEPATH + "list.php?c=list"),
    ListGlasses(BASEPATH + "list.php?g=list"),
    ListIngredients(BASEPATH + "list.php?i=list"),
    ListAlcoholic(BASEPATH + "list.php?a=list"),
}

const val BASEPATH = "/api/json/v1/1/"
