package com.example.countries_challenge.presentation.feature.mainnavigation.screens.model

import com.example.countries_challenge.domain.feature.countries.model.CityModel
import com.example.countries_challenge.presentation.feature.mainnavigation.components.CityListItemUiModel

fun CityModel.toCityListItemUIModel(): CityListItemUiModel {
    return CityListItemUiModel(
        id = id,
        country = country,
        name = name,
        lat = coordinates.lat,
        lon = coordinates.lon,
        isFavourite = isFavourite,
        isUpdatingFavoriteState = false,
    )
}