package fr.ecrinsdespatule.skirandobriancon


import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewFragment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.ecrinsdespatule.skirandobriancon.fragments.BeraFragment
import fr.ecrinsdespatule.skirandobriancon.fragments.HomeFragment
import fr.ecrinsdespatule.skirandobriancon.fragments.IgnFragment
import fr.ecrinsdespatule.skirandobriancon.fragments.RssFragment
import fr.ecrinsdespatule.skirandobriancon.fragments.WebcamFragment

class MainActivity : AppCompatActivity() {

    private lateinit var pageTitleTextView: TextView // Ajoutez cette ligne

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pageTitleTextView = findViewById(R.id.page_title) // Associez la vue avec l'ID page_title

        //importer la Bottomnavigationview
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this))
                    pageTitleTextView.text = getString(R.string.home_page_title) // Mettez à jour le titre ici
                    return@setOnItemSelectedListener true
                }

                R.id.collection_page -> {
                    loadFragment(WebcamFragment(this))
                    pageTitleTextView.text = getString(R.string.page_webcam_title) // Mettez à jour le titre ici
                    return@setOnItemSelectedListener true
                }

                R.id.bera_page -> {
                    loadFragment(BeraFragment(this))
                    pageTitleTextView.text = getString(R.string.page_bera_title) // Mettez à jour le titre ici
                    return@setOnItemSelectedListener true
                }

                R.id.RSS_page -> {
                    loadFragment(RssFragment(this))
                    pageTitleTextView.text = getString(R.string.page_RSS_title) // Mettez à jour le titre ici
                    return@setOnItemSelectedListener true
                }

                R.id.IGN_page -> {
                    loadFragment(IgnFragment(this))
                    pageTitleTextView.text = getString(R.string.page_IGN_title) // Mettez à jour le titre ici
                    return@setOnItemSelectedListener true
                }

                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
            //charger le Meteo repository
            val repo = MeteoRepository()

            //mettre à jour la liste des météos
            repo.updateData {
                // Injecter le fragment dans notre boite (fragment_container)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
                transaction.addToBackStack(null)
                val commit = transaction.commit()
            }
        }
}

