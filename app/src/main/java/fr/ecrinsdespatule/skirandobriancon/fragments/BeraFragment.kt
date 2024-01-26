package fr.ecrinsdespatule.skirandobriancon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.ecrinsdespatule.skirandobriancon.BeraModel
import fr.ecrinsdespatule.skirandobriancon.MainActivity
import fr.ecrinsdespatule.skirandobriancon.R
import fr.ecrinsdespatule.skirandobriancon.adapter.BeraAdapter

class BeraFragment (
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bera, container, false)

        // Créez une liste de données pour les URLs que vous souhaitez afficher
        val beraDataList = listOf(
            BeraModel("https://bulletin.metabera.ovh/bera.php?massif=QUEYRAS"),
            BeraModel("https://bulletin.metabera.ovh/bera.php?massif=THABOR"),
            BeraModel("https://bulletin.metabera.ovh/bera.php?massif=PELVOUX"),
            BeraModel("https://bulletin.metabera.ovh/bera.php?massif=OISANS"),
            BeraModel("https://bulletin.metabera.ovh/bera.php?massif=GRANDES-ROUSSES")
            // Ajoutez d'autres URLs ici
        )

        val beraRecyclerView = view.findViewById<RecyclerView>(R.id.bera_recycler_view)
        beraRecyclerView.adapter = BeraAdapter(context,beraDataList)
        beraRecyclerView.layoutManager = LinearLayoutManager (context, LinearLayoutManager.HORIZONTAL, false)

        return view
    }
}