package fr.ecrinsdespatule.skirandobriancon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.ecrinsdespatule.skirandobriancon.MainActivity
import fr.ecrinsdespatule.skirandobriancon.R
import fr.ecrinsdespatule.skirandobriancon.WebcamModel

class WebcamAdapter(
    val context: MainActivity,
    private val webcams: List<WebcamModel>,
    private val layoutId: Int
) : RecyclerView.Adapter<WebcamAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.webcamName)
        val webView: WebView = itemView.findViewById(R.id.webcamWebView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val webcam = webcams[position]

        holder.nameTextView.text = webcam.name

        holder.nameTextView.setOnClickListener {
            // Affichez le WebView et chargez le contenu de la webcam
            holder.webView.visibility = View.VISIBLE
            holder.webView.settings.javaScriptEnabled = true
            holder.webView.loadUrl(webcam.videoUrl)
        }

        // Configurez la gestion de la navigation du WebView (optionnel)
        holder.webView.webViewClient = WebViewClient()
    }

    override fun getItemCount(): Int {
        return webcams.size
    }
}
