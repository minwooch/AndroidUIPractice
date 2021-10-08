package com.applsh.androiduipractice.base

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream


@GlideModule
class GlideWithHeader: AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.replace(String::class.java, InputStream::class.java, HeaderedLoader.Factory())
    }
}

class HeaderedLoader(concreteLoader: ModelLoader<GlideUrl, InputStream>) :
    BaseGlideUrlLoader<String>(concreteLoader) {
    override fun handles(model: String): Boolean {
        return true
    }

    override fun getUrl(model: String, width: Int, height: Int, options: Options?): String {
        return model
    }

    override fun getHeaders(model: String?, width: Int, height: Int, options: Options?): Headers {
        return HEADERS
    }

    class Factory : ModelLoaderFactory<String, InputStream> {
        override fun build(
            multiFactory: MultiModelLoaderFactory
        ): ModelLoader<String, InputStream> {
            return HeaderedLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java))
        }

        override fun teardown() { /* nothing to free */
        }
    }

    companion object {
        private const val USER_AGENT = "Mozilla/5.0 (Linux; Android 11) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.181 Mobile Safari/537.36"
        val HEADERS: Headers = LazyHeaders.Builder().addHeader("User-Agent", USER_AGENT).build()
    }
}