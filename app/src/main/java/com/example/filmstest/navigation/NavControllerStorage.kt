package com.example.filmstest.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavController

object NavControllerStorage {
    @SuppressLint("StaticFieldLeak")
    private lateinit var navController: NavController

    fun setNavController(navController: NavController) {
        NavControllerStorage.navController = navController
    }

    fun getNavController(): NavController = navController
}