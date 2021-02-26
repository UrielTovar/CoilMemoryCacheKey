package dev.jaque.coil

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import coil.load
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import dev.jaque.coil.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.util.*

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
        // Init cache key with custom string
        val uuid = UUID.randomUUID().toString()
        // Whether online or offline, it's required to create a reference in memory with a cache key.
        val request = ImageRequest.Builder(this)
            .data("https://www.googleapis.com/download/storage/v1/b/gcs-provac-development/o/Home_app.png?alt=media")
            .memoryCacheKey(uuid)
            .build()

        // Execute request. No matter if was successful or failure, this signs the data with a memory cache key
        // and that data is available. Memory cache key is only a reference, not an image itself.
        // By itself cannot get an image, it's only a key.
        imageLoader.execute(request)
        Log.i("Cache", "${imageLoader.memoryCache[MemoryCache.Key(uuid)]}")
        // If you try to avoid `ImageRequest` this statement will always returns null reference.
        val bitmap: Bitmap? = imageLoader.memoryCache[MemoryCache.Key(uuid)]

        Log.i("Coil", "Bitmap: ${bitmap?.byteCount}")

        binding.ivLogo.load(bitmap)

    }

}