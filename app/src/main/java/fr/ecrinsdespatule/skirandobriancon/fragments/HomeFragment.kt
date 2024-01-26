package fr.ecrinsdespatule.skirandobriancon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.ecrinsdespatule.skirandobriancon.MainActivity
import fr.ecrinsdespatule.skirandobriancon.MeteoRepository.Singleton.meteoList
import fr.ecrinsdespatule.skirandobriancon.R
import fr.ecrinsdespatule.skirandobriancon.adapter.MeteoAdapter
import fr.ecrinsdespatule.skirandobriancon.adapter.MeteoItemDecoration

class HomeFragment (
    private val context : MainActivity
): Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home,container,false)

        //recupérer le 1er recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = MeteoAdapter(context,meteoList.filter { it.liked },R.layout.item_horizontal_meteo)

        //recupérer le 2nd recyclerview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = MeteoAdapter(context,meteoList,R.layout.item_vertical_meteo)
        verticalRecyclerView.addItemDecoration(MeteoItemDecoration())

        val imageViewPlus = view.findViewById<ImageView>(R.id.add_meteo_click) // Utilisez l'ID add_meteo_click
        imageViewPlus.setOnClickListener {
            // Lorsque l'icône est cliquée, ouvrez le fragment AddMeteoFragment
            val addMeteoFragment = AddMeteoFragment(context)
            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, addMeteoFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }
}
