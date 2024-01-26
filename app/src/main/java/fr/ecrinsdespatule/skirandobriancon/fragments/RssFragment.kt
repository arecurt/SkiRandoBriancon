package fr.ecrinsdespatule.skirandobriancon.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.google.firebase.crashlytics.buildtools.api.net.Constants.Http
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.CloseableHttpResponse
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.CloseableHttpClient
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClients
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils
import fr.ecrinsdespatule.skirandobriancon.MainActivity
import fr.ecrinsdespatule.skirandobriancon.R
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import org.jsoup.select.Elements
import java.nio.charset.StandardCharsets

class RssFragment(
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_rss, container, false)
        val webView = rootView.findViewById<WebView>(R.id.rssWebView)
        webView.settings.javaScriptEnabled = true

        // Activer la gestion des liens hypertextes
        webView.webViewClient = WebViewClient()

        // Récupérez et affichez le flux RSS ici
        val rssUrl =
            "https://www.metaskirando.ovh/ski_rss.php?days=7&nbr=20&site=all&zon=Cerces%7CThabor%7CAmbin%7CMaurienne%7Ccrins%7Cchampsaur%7Cvalg%7Ccombeyn%7Coisans%7CQueyras%7CParpaillon%7CUbaye%7CBrian"
        fetchAndDisplayRss(webView, rssUrl)

        return rootView
    }

    private fun fetchAndDisplayRss(webView: WebView, rssUrl: String) {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                // Check if the URL is from CamptoCamp
                if (url != null && url.contains("camptocamp.org")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                } else {
                    // Handle other URLs in the WebView
                    if (url != null) {
                        view?.loadUrl(url)
                    }
                }
                return true
            }
        }
        Thread {
            try {
                val httpClient: CloseableHttpClient = HttpClients.createDefault()
                val httpGet = HttpGet(rssUrl)
                val response: CloseableHttpResponse = httpClient.execute(httpGet)
                if (response.statusLine.statusCode == 200) {
                    val rssContent: String = EntityUtils.toString(response.entity, StandardCharsets.UTF_8)
                    val xml: Document = Jsoup.parse(rssContent,"", Parser.xmlParser())
                    val items: Elements = xml.select("item")

                    val rssHtmlContent = StringBuilder()
                    for (item : Element in items) {
                        val title: String = item.select("title").text()
                        val link : String = item.select("link").text()
                        val description: String = item.select("description").text()

                        rssHtmlContent.append("<h1>$title</h1><br/>");
                        rssHtmlContent.append("<a class='links' href='$link'>$link</a><br/>");
                        rssHtmlContent.append("<p class='description'>$description<hr/></p>")
                    }

                    // Mettez à jour la WebView avec le contenu HTML généré
                    webView.post {
                        val htmlTemplate = getHtmlTemplate(rssHtmlContent.toString())
                        webView.loadDataWithBaseURL(null, htmlTemplate, "text/html", "utf-8", null)
                    }
                } else {
                    println("Erreur lors de la requête HTTP : " + response.statusLine)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun getHtmlTemplate(content: String): String {
        // Générez votre modèle HTML ici
        return """
            <!DOCTYPE html>
            <html lang="Fr-fr">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    /* Votre CSS personnalisé ici */
                    div {
                            background-color : ultralightGrey;
                            max-width: 400px;
                            width: 80%;
                            padding: 1em;
                            text-align: left;
                            border: solid 1px black;
                            border-radius: 20px;
                            margin: 20px auto;
                        }
                    h1{
                    padding : 0;
                    text-align: left;
                    font-family: kanit;
                    font-weight: 600;
                    margin: 2px 0 0 0;
                    font-size : 1em;
                    }
                    h2{
                    padding : 0;
                    text-align: left;
                    font-family: kanit;
                    font-weight: 800;
                    margin: 0 0 0 0;
                    font-size : 1em;
                    }
                    a.links{
                        padding : 0;
                        text-align: left;
                        font-weight: 300;
                        font-family: kanit;
                        text-decoration : none;
                        margin: 0 0 5px 0;
                        font-size : 0.9em;
                    }
                    p.description{
                        text-align: center;
                        font-weight: 300;
                        margin: 10px 0 0 0;
                        font-size : 0.7em;
                    }
                </style>
            </head>
            <body>
            <div>
                $content
            </div>
            <section>
             <div>
                <h2>Conditions de circulation</h2><p>Pour vérifier l'état des routes du département Haut-Alpins : <a href='https://inforoute.hautes-alpes.fr/www/index.html'>Info Route 05</a></p>
             </div>
            </section>
            </body>
            </html>
        """.trimIndent()
    }
}
