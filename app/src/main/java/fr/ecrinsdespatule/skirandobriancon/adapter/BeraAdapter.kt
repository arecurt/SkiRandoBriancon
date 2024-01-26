package fr.ecrinsdespatule.skirandobriancon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import fr.ecrinsdespatule.skirandobriancon.BeraModel
import fr.ecrinsdespatule.skirandobriancon.MainActivity
import fr.ecrinsdespatule.skirandobriancon.R

class BeraAdapter(
    val context: MainActivity,
    private val beraDataList: List<BeraModel>
) : RecyclerView.Adapter<BeraAdapter.WebViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_bera, parent, false)
        return WebViewHolder(view)
    }

    override fun onBindViewHolder(holder: WebViewHolder, position: Int) {
        val webViewData = beraDataList[position]
        holder.bind(webViewData)
    }

    override fun getItemCount(): Int {
        return beraDataList.size
    }

    inner class WebViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val webView: WebView = itemView.findViewById(R.id.bera)

        fun bind(webViewData: BeraModel) {
            webView.setInitialScale(2);
            webView.settings.loadWithOverviewMode = true;
            webView.settings.useWideViewPort = true;
            webView.settings.javaScriptEnabled = true // Active JavaScript si nécessaire
            //webView.settings.setSupportZoom = true;
            webView.settings.builtInZoomControls = true;
            webView.settings.displayZoomControls = false;
            webView.webViewClient = WebViewClient()

            webView.loadUrl(webViewData.url)
        }
    }
}
