package fr.ecrinsdespatule.skirandobriancon.adapter

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.chrisbanes.photoview.PhotoView
import fr.ecrinsdespatule.skirandobriancon.R

class MeteoFullScreenImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_full_meteo)
        setupCloseButton()

        val imageUrl = intent.getStringExtra("imageUrl")
        val photoView: PhotoView = findViewById(R.id.fullScreenImageView)

        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)

        try {
            Glide.with(this)
                .load(Uri.parse(imageUrl))
                .apply(requestOptions)
                .into(photoView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.ic_close).setOnClickListener {
            finish()
        }
    }
}

