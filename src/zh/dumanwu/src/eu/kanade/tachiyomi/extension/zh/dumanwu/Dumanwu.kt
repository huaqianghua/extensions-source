package eu.kanade.tachiyomi.extension.zh.dumanwu

import eu.kanade.tachiyomi.multisrc.mmlook.MMLook
import okhttp3.Headers

class Dumanwu : MMLook(
    "读漫屋2",
    "http://m.dumanwu1.com",
    "http://www.dumanwu1.com",
    useLegacyMangaUrl = false,
) {
    override fun headersBuilder(): Headers.Builder = super.headersBuilder()
        .set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36")
        .set("Referer", baseUrl)
}
