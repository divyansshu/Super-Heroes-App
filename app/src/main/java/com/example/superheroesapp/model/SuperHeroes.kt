package com.example.superheroesapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SuperHeroes(
     @StringRes val heroName: Int,
     @StringRes val heroPower: Int,
    @DrawableRes val heroImage: Int
)
