package dev.jaque.coil

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import coil.load
import coil.memory.MemoryCache
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import dev.jaque.coil.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    /* */
    private val binding: ActivityMainBinding
            by lazy { ActivityMainBinding.inflate(layoutInflater) }

    /** */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpViews()
    }

    /** */
    private fun setUpViews() {
        lifecycleScope.launch {
            getMemoryCacheKey()
        }
    }

    /** */
    private suspend fun getMemoryCacheKey() {
        val request = ImageRequest.Builder(this)
            .data("https://www.googleapis.com/download/storage/v1/b/gcs-provac-development/o/Home_app.png?alt=media")
            .build()

       val result = imageLoader.execute(request) as SuccessResult

        val memoryCacheKey = result.metadata.memoryCacheKey

        val bitmap: Bitmap? = imageLoader.memoryCache[memoryCacheKey!!]

        Log.i("Coil", "Bitmap: ${bitmap?.byteCount}")

        binding.ivLogo.load(bitmap)

    }

}