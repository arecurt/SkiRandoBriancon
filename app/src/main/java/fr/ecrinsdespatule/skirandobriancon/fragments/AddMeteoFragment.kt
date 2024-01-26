package fr.ecrinsdespatule.skirandobriancon.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import fr.ecrinsdespatule.skirandobriancon.MainActivity
import fr.ecrinsdespatule.skirandobriancon.MeteoModel
import fr.ecrinsdespatule.skirandobriancon.MeteoRepository
import fr.ecrinsdespatule.skirandobriancon.MeteoRepository.Singleton.downloadUri
import fr.ecrinsdespatule.skirandobriancon.R
import java.util.UUID

class AddMeteoFragment(
    private val context: MainActivity
) : Fragment() {
    private var file: Uri? = null
    private var uploadedImage: ImageView? = null
    private var imagePickerLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_meteo, container, false)

        // Récupérer uploadedImage pour lui associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        // Récupérer le bouton pour charger l'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        // Initialiser le ActivityResultLauncher
        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data
                    // Vérifier si les données sont nulles
                    if (data == null || data.data == null) return@registerForActivityResult

                    // Récupérer l'URI de l'image sélectionnée
                    file = data.data

                    // Mettre à jour l'image
                    uploadedImage?.setImageURI(file)

                }
            }
            //récuperer le bouton confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener { sendForm(view) }


        // Ouvrir les images du téléphone
        pickupImageButton.setOnClickListener { pickupImage() }

        return view
    }

    private fun sendForm(view : View) {
        val repo = MeteoRepository()
        repo.uploadImage(file!!){
            val meteoName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val meteoDescription = view.findViewById<Spinner>(R.id.region_spinner).selectedItem.toString()
            val altitude = view.findViewById<EditText>(R.id.altitude_input).text.toString()
            //val urlmeteo = view.findViewById<EditText>(R.id.url_meteo_input).text.toString()
            val urlmutimodel = view.findViewById<EditText>(R.id.multimodel_input).text.toString()
            val downloadImageUrl = downloadUri

            //Créer un nouvel objet MeteoModel
            val meteo = MeteoModel(
                UUID.randomUUID().toString(),
                meteoName,
                meteoDescription,
                downloadImageUrl.toString(),
                altitude,
                urlmutimodel,
                true,
                )

            //Envoyer en bdd
            repo.insertMeteo(meteo)
        }
    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        imagePickerLauncher?.launch(Intent.createChooser(intent, "Sélectionnez une image"))
    }
}