package com.bangkit.crealth.data.response

import com.google.gson.annotations.SerializedName

data class NutritionixResponse(
    @SerializedName("common")
    val common: List<Any>,
    @SerializedName("branded")
    val branded: List<BrandedFood>
)

data class BrandedFood(
    @SerializedName("serving_qty")
    val servingQty: Double,
    @SerializedName("nf_calories")
    val calories: Int,
    @SerializedName("brand_name")
    val brandName: String,
    @SerializedName("serving_unit")
    val servingUnit: String,
    @SerializedName("brand_name_item_name")
    val brandNameItemName: String,
    @SerializedName("nix_item_id")
    val nixItemId: String,
    @SerializedName("food_name")
    val foodName: String,
    @SerializedName("photo")
    val photo: Photo,
    @SerializedName("locale")
    val locale: String
)

data class Photo(
    @SerializedName("thumb")
    val thumb: String
)