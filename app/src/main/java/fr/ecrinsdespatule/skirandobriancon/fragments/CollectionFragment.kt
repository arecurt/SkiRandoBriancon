package fr.ecrinsdespatule.skirandobriancon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.ecrinsdespatule.skirandobriancon.MainActivity
import fr.ecrinsdespatule.skirandobriancon.MeteoRepository.Singleton.meteoList
import fr.ecrinsdespatule.skirandobriancon.R
import fr.ecrinsdespatule.skirandobriancon.adapter.MeteoAdapter
import fr.ecrinsdespatule.skirandobriancon.adapter.MeteoItemDecoration

class CollectionFragment(
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_collection, container, false)

        //récupérer le recyclerview
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView.adapter = MeteoAdapter(context, meteoList.filter { it.liked }, R.layout.item_vertical_meteo)
        collectionRecyclerView.layoutManager = LinearLayoutManager (context)
        collectionRecyclerView.addItemDecoration(MeteoItemDecoration())

        return view
    }

}