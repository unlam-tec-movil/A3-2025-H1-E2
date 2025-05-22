package ar.edu.unlam.scaffoldingandroid3.data.navigation

sealed class NavigationRoutes(val route: String) {

    data object MapScreen : NavigationRoutes(route = "maps")

    data object ProfileScreen : NavigationRoutes(route = "profile")

    data object CreateMonument : NavigationRoutes(route = "create_monument")

    data object AjustesScreen : NavigationRoutes(route = "settings")

    data object NotificacionesScreen : NavigationRoutes(route = "notificaciones")

    data object ComunidadScreen : NavigationRoutes(route = "comunidad")

    data object MonumentosScreen : NavigationRoutes(route = "monumentos")

    data object AlbumScreen : NavigationRoutes(route = "album")


}