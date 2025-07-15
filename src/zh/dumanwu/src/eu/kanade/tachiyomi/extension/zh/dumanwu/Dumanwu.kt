package eu.kanade.tachiyomi.extension.zh.dumanwu

import eu.kanade.tachiyomi.multisrc.mmlook.MMLook
import eu.kanade.tachiyomi.network.POST
import eu.kanade.tachiyomi.source.model.FilterList
import eu.kanade.tachiyomi.source.model.MangasPage
import eu.kanade.tachiyomi.source.model.SManga
import eu.kanade.tachiyomi.util.asJsoup
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response

class Dumanwu : MMLook(
    "读漫屋",
    "http://m.dumanwu1.com",
    "http://www.dumanwu1.com",
    useLegacyMangaUrl = false,
) {
    override fun searchMangaRequest(page: Int, query: String, filters: FilterList): Request {
        if (query.isNotBlank()) {
            val form = FormBody.Builder()
                .add("k", query)
                .build()
            return POST("$baseUrl/s", body = form, headers = headers)
        }
        return super.searchMangaRequest(page, query, filters)
    }

    override fun searchMangaParse(response: Response): MangasPage {
        if (response.request.url.toString().contains("/s")) {
            val entries = response.asJsoup().select(".mh-item").map { element ->
                SManga.create().apply {
                    url = element.selectFirst("a")!!.attr("href")
                        .removeSurrounding("/comic/", "/")
                    title = element.selectFirst(".mh-item-detali > h2")!!.text()
                    author = element.selectFirst(".mh-item-detali > p")!!.ownText()
                        .removeSurrounding("作者：", "")
                    thumbnail_url = element.selectFirst("img")!!.attr("src")
                }
            }
            return MangasPage(entries, entries.size == 24)
        }
        return super.searchMangaParse(response)
    }

    private fun String.removeSurrounding(prefix: String, suffix: String): String {
        check(startsWith(prefix) && endsWith(suffix)) { "string doesn't match $prefix[...]$suffix" }
        return substring(prefix.length, length - suffix.length)
    }
}
