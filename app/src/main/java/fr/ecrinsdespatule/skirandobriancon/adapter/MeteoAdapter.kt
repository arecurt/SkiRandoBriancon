package fr.ecrinsdespatule.skirandobriancon.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import fr.ecrinsdespatule.skirandobriancon.MainActivity
import fr.ecrinsdespatule.skirandobriancon.MeteoModel
import fr.ecrinsdespatule.skirandobriancon.MeteoPopup
import fr.ecrinsdespatule.skirandobriancon.MeteoRepository
import fr.ecrinsdespatule.skirandobriancon.R


class MeteoAdapter(
    val context: MainActivity,
    private val meteoList: List<MeteoModel>,
    private val layoutId: Int) : RecyclerView.Adapter<MeteoAdapter.ViewHolder>(){

    //boite pour ranger tous les composants à contrôler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val meteoImage = view.findViewById<ImageView>(R.id.image_item)
        val meteoName:TextView? = view.findViewById(R.id.name_item)
        val meteoDescription:TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //récupérer les informations de la météo
        val currentMeteo = meteoList[position]

        //récupérer le repository
        val repo = MeteoRepository()

        // Supprimer le cache de Glide après avoir téléchargé les nouvelles images
        //Glide.get(context).clearMemory()
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
            .skipMemoryCache(true)

        //utiliser Glide pour récupérer l'image / lien
        Glide.with(context)
            .load(Uri.parse(currentMeteo.imageUrl))
            .apply(requestOptions)
            .into(holder.meteoImage)

        //mettre à jour le nom de la météo
        holder.meteoName?.text = currentMeteo.name

        //mettre à jour la description de la météo
        holder.meteoDescription?.text = currentMeteo.description

        //vérifier si la météo est liké
        if (currentMeteo.liked) {
            holder.starIcon.setImageResource(R.drawable.ic_star)
        } else {
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        //rajouter une interaction sur l'étoile
        holder.starIcon.setOnClickListener {
            //inverse si le bouton est like ou non
            currentMeteo.liked = !currentMeteo.liked
            //mettre à jour l'objet météo
            repo.updateMeteo(currentMeteo)
        }

        //interaction lors du click sur la météo
        /*holder.itemView.setOnClickListener{
            //afficher la popup
            MeteoPopup(this, currentMeteo).show()
        }*/

// Interaction lors du clic sur la météo
        holder.itemView.setOnClickListener {
            // Vérifier si c'est un RecyclerView horizontal ou vertical
            when (layoutId) {
                R.layout.item_horizontal_meteo -> {
                    // Ouvrir l'activité en plein écran pour afficher l'image en grand
                    val intent = Intent(context, MeteoFullScreenImageActivity::class.java)
                    intent.putExtra("imageUrl", currentMeteo.imageUrl)
                    context.startActivity(intent)
                }
                R.layout.item_vertical_meteo -> {
                    // Ouvrir la MeteoPopup que vous avez programmée
                    MeteoPopup(this, currentMeteo).show()
                }
                // Ajoutez d'autres cas si nécessaire
            }
        }
    }

    override fun getItemCount(): Int = meteoList.size
}


