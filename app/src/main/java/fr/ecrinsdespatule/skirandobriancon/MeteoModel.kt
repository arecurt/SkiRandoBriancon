package fr.ecrinsdespatule.skirandobriancon

class MeteoModel (
    val id: String = "Meteo0",
    val name: String = "Nom Météo",
    val description: String = "Zone Météo",
    val imageUrl: String = "https://cdn.pixabay.com/photo/2023/06/02/21/24/portrait-8036356_1280.jpg",
    val altitude: String = "0000m",
    val multimodel: String = "https://meteoblue.com",
    var liked: Boolean = false,
) {
}

