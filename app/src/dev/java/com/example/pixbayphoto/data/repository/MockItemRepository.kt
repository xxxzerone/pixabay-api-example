package com.example.pixbayphoto.data.repository

import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class MockItemRepository @Inject constructor() : ItemRepository {
    override fun getItemsSortedById(query: String): Flow<List<Item>> {
        return MOCK_ITEMS.map { items ->
            items.asSequence() // 시퀀스로 변환 (Lazy evaluation) 대규모 데이터 처리 시 유용
                .filter { item ->
                    query.isBlank() || item.tags.split(",").any { tag ->
                        tag.trim().contains(query, ignoreCase = true)
                    }
                }
                .sortedByDescending { it.id }
                .toList()
        }
            .onStart { delay(1000) }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    override fun getItemById(id: Long): Flow<Item?> {
        return MOCK_ITEMS.map { items ->
            items.firstOrNull { it.id == id }
        }
            .onStart { delay(1000) }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    companion object {
        val MOCK_ITEMS = MutableStateFlow(
            listOf(
                Item(
                    id = 5668882,
                    pageUrl = "https://pixabay.com/photos/chrysanthemum-garden-chrysanthemums-5668882/",
                    type = "photo",
                    tags = "chrysanthemum, garden chrysanthemums, beautiful flowers, flower, blossom, bloom, yellow, petals, yellow flowers, yellow flower, flower background, flora, floriculture, horticulture, botany, nature, flower wallpaper, up close",
                    previewUrl = "https://cdn.pixabay.com/photo/2020/10/19/19/58/chrysanthemum-5668882_150.jpg",
                    previewWidth = 150,
                    previewHeight = 100,
                    webFormatUrl = "https://pixabay.com/get/gf2a44b03e6974bd909037b350c1bfe3fc693823538dac025f14d65e867e3134e99a3c0f015f6fad7c5a02080488f21cee8d46037b2f73cbdda12872444d971c5_640.jpg",
                    webFormatWidth = 640,
                    webFormatHeight = 427,
                    largeImageUrl = "https://pixabay.com/get/ga4833ade842e0c142e9e680287bf8f9cbca6bac32aea88cc4d480365aeed86caf5e60f8b14dbe9cbbbae1ba46f79c2a214290b8f6a72ff9fffb5ae6d10f75079_1280.jpg",
                    imageWidth = 3864,
                    imageHeight = 2576,
                    imageSize = 1293394,
                    views = 20170,
                    downloads = 13742,
                    collections = 144,
                    likes = 200,
                    comments = 100,
                    userId = 12752456,
                    user = "mariya_m",
                    userImageUrl = "https://cdn.pixabay.com/user/2025/12/16/06-16-12-556_250x250.jpeg",
                    noAiTraining = true,
                    isAiGenerated = false,
                    isGRated = true,
                    isLowQuality = false,
                    userUrl = "https://pixabay.com/users/12752456/"
                ),
                Item(
                    id = 6162613,
                    pageUrl = "https://pixabay.com/photos/yellow-rose-rose-flower-cereal-6162613/",
                    type = "photo",
                    tags = "yellow rose, rose, flower, cereal, flower wallpaper, yellow flower, garden, nature, closeup, plants, flora, beautiful flowers, fragrant, plant, floral, blossomed, light yellow, beautiful, flower background, flowers, rose flower, roses, soft",
                    previewUrl = "https://cdn.pixabay.com/photo/2021/04/08/18/59/yellow-rose-6162613_150.jpg",
                    previewWidth = 150,
                    previewHeight = 100,
                    webFormatUrl = "https://pixabay.com/get/g508e61b52525459fc728ea8246b23a12508bdd8c714671e6949d1e6719f1c8c9e0dca895eb4d724c9478335eaaf2b962f50b85f9528967cef8b3154afc6389f0_640.jpg",
                    webFormatWidth = 640,
                    webFormatHeight = 427,
                    largeImageUrl = "https://pixabay.com/get/ge62c491106d01fc7b53fd97110b447ee3c5bddaa3a12f2ce003f533db28783cc8270846ac5047acb4bdd933d2b10dedfdf85d4aff0102738c8fbc312e8e99b97_1280.jpg",
                    imageWidth = 4240,
                    imageHeight = 2832,
                    imageSize = 2389361,
                    views = 68136,
                    downloads = 51105,
                    collections = 155,
                    likes = 308,
                    comments = 204,
                    userId = 9363663,
                    user = "Nowaja",
                    userImageUrl = "https://cdn.pixabay.com/user/2020/09/15/15-16-12-52_250x250.jpg",
                    noAiTraining = false,
                    isAiGenerated = false,
                    isGRated = true,
                    isLowQuality = false,
                    userUrl = "https://pixabay.com/users/9363663/"
                ),
                Item(
                    id = 4042853,
                    pageUrl = "https://pixabay.com/photos/sulphur-anemone-flowers-4042853/",
                    type = "photo",
                    tags = "sulphur anemone, flowers, yellow flower, petals, beautiful flowers, yellow petals, blossom, flower background, flower wallpaper, bloom, flora, plant, nature",
                    previewUrl = "https://cdn.pixabay.com/photo/2019/03/08/17/43/sulphur-anemone-4042853_150.jpg",
                    previewWidth = 150,
                    previewHeight = 99,
                    webFormatUrl = "https://pixabay.com/get/gecc3359d0f2580e3ad9f6719823a12e61d1589110228451f183dcfe52c11363c2d92d5b2f43c7e6d85ccf00d4b2adea0fc18658ffcd2ae8f291ecf737600b142_640.jpg",
                    webFormatWidth = 640,
                    webFormatHeight = 426,
                    largeImageUrl = "https://pixabay.com/get/g05dd67974f943d43b44d64c8c854cff6ca8b89f721481f98ed7db12e96d56a3e244969b2d13170ce46aa6714902874b0a316d6ce3c201710f6359c557adc1284_1280.jpg",
                    imageWidth = 5394,
                    imageHeight = 3593,
                    imageSize = 7268701,
                    views = 25925,
                    downloads = 19486,
                    collections = 520,
                    likes = 104,
                    comments = 20,
                    userId = 6482,
                    user = "gsibergerin",
                    userImageUrl = "https://cdn.pixabay.com/user/2019/03/09/16-09-22-778_250x250.jpg",
                    noAiTraining = false,
                    isAiGenerated = false,
                    isGRated = true,
                    isLowQuality = false,
                    userUrl = "https://pixabay.com/users/6482/"
                ),
                Item(
                    id = 247409,
                    pageUrl = "https://pixabay.com/photos/blossom-bloom-macro-garden-flowers-247409/",
                    type = "photo",
                    tags = "blossom, bloom, macro, garden, flowers, plant, flower background, flower, star, nature, summer, yellow, close up view yellow, bloom, petals, bright yellow, flora, beautiful flowers, sunny yellow, flower wallpaper, bright, flower macro",
                    previewUrl = "https://cdn.pixabay.com/photo/2014/01/18/11/09/blossom-247409_150.jpg",
                    previewWidth = 150,
                    previewHeight = 99,
                    webFormatUrl = "https://pixabay.com/get/g3c162394cf6f3b8a9aa007b01b9720b2b752aee6cb44e31171cc0f5f37510c5593d2c83d73e29113172a36d89de7577_640.jpg",
                    webFormatWidth = 640,
                    webFormatHeight = 426,
                    largeImageUrl = "https://pixabay.com/get/g02110840d0cd66e0e901922dda3603bbfe4ff15c18979ede10f1bed43abcb6a92cad63a8bd12568fe05a0c3aa7fbac3207ff496997c2d3818d9f111280872abc_1280.jpg",
                    imageWidth = 4752,
                    imageHeight = 3168,
                    imageSize = 1763748,
                    views = 43901,
                    downloads = 20750,
                    collections = 208,
                    likes = 175,
                    comments = 23,
                    userId = 127419,
                    user = "cocoparisienne",
                    userImageUrl = "https://cdn.pixabay.com/user/2023/10/15/14-40-46-737_250x250.jpeg",
                    noAiTraining = true,
                    isAiGenerated = false,
                    isGRated = true,
                    isLowQuality = false,
                    userUrl = "https://pixabay.com/users/127419/"
                ),
                Item(
                    id = 5142952,
                    pageUrl = "https://pixabay.com/photos/flower-yellow-water-nature-5142952/",
                    type = "photo",
                    tags = "flower, flower background, flower wallpaper, beautiful flowers, yellow, water, nature",
                    previewUrl = "https://cdn.pixabay.com/photo/2020/05/07/19/49/flower-5142952_150.jpg",
                    previewWidth = 150,
                    previewHeight = 100,
                    webFormatUrl = "https://pixabay.com/get/g3feb7c6b6cd17b43e6b8ab61ee593aa888fc9f302949566430bafb5d28808b31a863c9d07923b5e3f9e5c31d1d5a3ef06847f8e6ebbf8a7cf7a24318ecc7ee04_640.jpg",
                    webFormatWidth = 640,
                    webFormatHeight = 427,
                    largeImageUrl = "https://pixabay.com/get/ga869fc0570cc5525096794fc6e81bc27ecb9087cae7e6a3871e27c787252bb36051c85cce04bf1e7219399f4642d5bac2c0193d4c746bde32c355c99fc98ea11_1280.jpg",
                    imageWidth = 4898,
                    imageHeight = 3265,
                    imageSize = 1256141,
                    views = 24397,
                    downloads = 19628,
                    collections = 401,
                    likes = 60,
                    comments = 7,
                    userId = 16209753,
                    user = "LuxuriousFox",
                    userImageUrl = "https://cdn.pixabay.com/user/2020/04/24/12-44-16-868_250x250.jpeg",
                    noAiTraining = false,
                    isAiGenerated = false,
                    isGRated = true,
                    isLowQuality = false,
                    userUrl = "https://pixabay.com/users/16209753/"
                )
            )
        )
    }
}
