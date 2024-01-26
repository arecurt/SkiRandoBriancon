package fr.ecrinsdespatule.skirandobriancon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.ecrinsdespatule.skirandobriancon.MainActivity
import fr.ecrinsdespatule.skirandobriancon.R
import fr.ecrinsdespatule.skirandobriancon.WebcamModel
import fr.ecrinsdespatule.skirandobriancon.adapter.WebcamAdapter

class WebcamFragment(
    private val context: MainActivity
) : Fragment() {
    private lateinit var webcamRecyclerView: RecyclerView
    private lateinit var webcamAdapter: WebcamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_webcam, container, false)

        webcamRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_webcam_recycler_view)
        webcamRecyclerView.layoutManager = LinearLayoutManager(context)

        val webcams = listOf(
            WebcamModel(
                "Col du Lautaret - 2058m",
                "https://www.skaping.com/serrechevalier/coldulautaret"
            ),
            WebcamModel(
                "Col du Prorel - 2396m",
                "https://app.webcam-hd.com/serrechevalier/col-du-prorel"
            ),
            WebcamModel(
                "Crête des Bans - 2700m",
                "https://www.vision-environnement.com/live/player/psv50.php"
            ),
            /*WebcamModel(
                "Pelvoux-Vallouise \"Sommet de la crête\" - 2300m",
                "https://visionenvironnement.quanteec.com/embeded/embeded.html?uuid=03bdf3e6-ffac-41d3-746c-7561-6665-64-a7fc-251978d5a69bd&amp;type=live&amp;analyticsID=visionenvironnement"
            ),*/
            WebcamModel(
                "Montgenèvre \"Sommet des Gondrans\" - 2460m",
                "https://app.webcam-hd.com/montgenevre/sommet-des-gondrans"
            ),
            WebcamModel(
                "Montgenèvre \"Sommet du Chalvet\" - 2577m",
                "https://app.webcam-hd.com/montgenevre/sommet-chalvet"
            )
            // Ajoutez d'autres webcams ici
        )

        webcamAdapter = WebcamAdapter(context, webcams, R.layout.item_vertical_webcam)
        webcamRecyclerView.adapter = webcamAdapter

        return view
    }
}
