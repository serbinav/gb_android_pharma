package com.example.comparepharma.model

object Constants {
    const val APTEKA_APRIL_BASE_URL = "https://web-api.apteka-april.ru/"
    const val APTEKA_APRIL_PRODUCT_INFO = "catalog/products"
    const val PROPERTIES_NUMBER_VENDOR = 13
    const val PROPERTIES_NUMBER_DOSAGE = 20
    const val DESCRIPTION_NUMBER_RELEASE_FORM = 19

// получение картинок и информации о нескольких товарах
// https://web-api.apteka-april.ru/
// ID,slug,price,priceZakaz,sticker,name,images,typeIDs,subtypeIDs,categoryID,properties,
// reviewsNumber,rating,isInStock,cardProjects,allowDelivery,allowOnlinePayment,
// averageRating,bonuses,deliveryRuleID,limitWithCard,limitWithoutCard,isAvailable,
// isWaitingArrive,isRecipe,discountTemplate
// @search?q=%22%D0%BF%D0%B0%D1%80%D0%B0%D1%86%D0%B5%D1%82%D0%B0%D0%BC%D0%BE%D0%BB%22&cityID=168660%5B0:23%5D

    const val LONG_WAIT: Long = 3000
    const val SERVER_ERROR = "Ошибка сервера"
    const val REQUEST_ERROR = "Ошибка запроса на сервер"
    const val CORRUPTED_ERROR = "Неполные данные"
    const val IS_APTEKA_RU_KEY = "LIST_OF_APTEKA_RU_KEY"
    const val DB_NAME = "Favorites.db"
}