package ar.edu.unlam.scaffoldingandroid3.data.navigation

sealed class NavigationRoutes(val route: String) {

    data object MapScreen : NavigationRoutes(route = "maps")

    data object ProfileScreen : NavigationRoutes(route = "profile")


}