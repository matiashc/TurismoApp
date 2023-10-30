package co.edu.ufps.turismoapp.modelo

data class Sitio(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val foto: String,
    val longitud: Double,
    val latitud: Double
)
