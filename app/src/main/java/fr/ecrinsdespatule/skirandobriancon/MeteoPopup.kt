package fr.ecrinsdespatule.skirandobriancon

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.ecrinsdespatule.skirandobriancon.adapter.MeteoAdapter

class MeteoPopup(
    private val adapter: MeteoAdapter,
    private val currentMeteo: MeteoModel) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_meteo_details)
        setupComponents()
        setupCloseButton()
        //setupDeleteButton()
        setupStarButton()
    }

    private fun updateStar(button: ImageView){
        if (currentMeteo.liked){
            button.setImageResource(R.drawable.ic_star)
        }
        else {
            button.setImageResource(R.drawable.ic_unstar)
        }
    }

    private fun setupStarButton() {
        //récupérer l'info
        val starButton = findViewById<ImageView>(R.id.star_button)
        updateStar(starButton)

            //intéraction avec le bouton
        starButton.setOnClickListener {
            currentMeteo.liked = !currentMeteo.liked
            val repo = MeteoRepository()
            repo.updateMeteo(currentMeteo)
            updateStar(starButton)
        }
    }

    /*private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener {
            //supprimer la météo de base de données
            val repo = MeteoRepository()
            repo.deleteMeteo(currentMeteo)
            dismiss()
        }
    }*/

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener {
            //fermer le popup
            dismiss()
        }
    }

    private fun setupComponents() {
        //actualiser l'image unique de la météo
        val meteoImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentMeteo.imageUrl)).into(meteoImage)

        //actualiser le nom de la météo
        findViewById<TextView>(R.id.popup_meteo_name).text = currentMeteo.name

        //actualiser la description
        findViewById<TextView>(R.id.popup_meteo_description_subtitle).text = currentMeteo.description

        //actualiser l'altitude
        findViewById<TextView>(R.id.popup_meteo_altitude_subtitle).text = currentMeteo.altitude

        //actualiser le lien multimodel
        findViewById<TextView>(R.id.popup_meteo_multimodel_subtitle).text = currentMeteo.multimodel
    }
}