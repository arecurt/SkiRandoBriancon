package fr.ecrinsdespatule.skirandobriancon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.Fragment
import fr.ecrinsdespatule.skirandobriancon.MainActivity
import fr.ecrinsdespatule.skirandobriancon.R

class IgnFragment(private val context: MainActivity) : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ign, container, false)

        // Récupérer la référence de la WebView depuis le layout
        webView = view.findViewById(R.id.ignView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuration de la WebView
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Chargement d'une URL (remplacez par votre URL)
        webView.loadUrl("https://spatules-des-ecrins.ovh/Condition/conditionapplication.php")

    }
}
