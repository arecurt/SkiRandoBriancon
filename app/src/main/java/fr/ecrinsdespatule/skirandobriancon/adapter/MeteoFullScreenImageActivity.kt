package fr.ecrinsdespatule.skirandobriancon.adapter

import android.net.Uri
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import fr.ecrinsdespatule.skirandobriancon.R

class MeteoFullScreenImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_full_meteo)
        setupCloseButton()

        val imageUrl = intent.getStringExtra("imageUrl")
        val imageView: ImageView = findViewById(R.id.fullScreenImageView)

        Glide.with(this)
            .load(Uri.parse(imageUrl))
            .into(imageView)

        setupHorizontalScroll()
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.ic_close).setOnClickListener {
            finish()
        }
    }

    private fun setupHorizontalScroll() {
        val horizontalScrollView: HorizontalScrollView = findViewById(R.id.horizontalScrollView)
    }
}
