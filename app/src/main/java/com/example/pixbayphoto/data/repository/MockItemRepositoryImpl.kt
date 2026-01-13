package com.example.pixbayphoto.data.repository

import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MockItemRepositoryImpl : ItemRepository {

    private val _items = MutableStateFlow<List<Item>>(
        listOf(
            Item(
                id = 6162613,
                pageUrl = "https://pixabay.com/photos/yellow-rose-rose-flower-cereal-6162613/",
                type = "photo",
                tags = "yellow rose, rose, flower, cereal, yellow flower, garden, nature, beautiful flowers, flower background, closeup, plants, flora, fragrant, flower wallpaper, plant, floral, blossomed, light yellow, beautiful, flowers, rose flower, roses, soft",
                previewUrl = "https://cdn.pixabay.com/photo/2021/04/08/18/59/yellow-rose-6162613_150.jpg"
            ),
            Item(
                id = 5668882,
                pageUrl = "https://pixabay.com/photos/chrysanthemum-garden-chrysanthemums-5668882/",
                type = "photo",
                tags = "chrysanthemum, garden chrysanthemums, beautiful flowers, flower, blossom, bloom, yellow, petals, yellow flowers, yellow flower, flower background, flora, floriculture, horticulture, botany, nature, flower wallpaper, up close",
                previewUrl = "https://cdn.pixabay.com/photo/2020/10/19/19/58/chrysanthemum-5668882_150.jpg"
            ),
            Item(
                id = 4042853,
                pageUrl = "https://pixabay.com/photos/sulphur-anemone-flowers-4042853/",
                type = "photo",
                tags = "sulphur anemone, flowers, yellow flower, petals, flower wallpaper, flower background, yellow petals, beautiful flowers, blossom, bloom, flora, plant, nature",
                previewUrl = "https://cdn.pixabay.com/photo/2019/03/08/17/43/sulphur-anemone-4042853_150.jpg"
            ),
            Item(
                id = 247409,
                pageUrl = "https://pixabay.com/photos/blossom-bloom-macro-garden-flowers-247409/",
                type = "photo",
                tags = "blossom, bloom, macro, garden, flower wallpaper, flowers, plant, flower, flower background, star, nature, beautiful flowers, summer, yellow, close up view yellow, petals, bright yellow, flora, sunny yellow, bright, flower macro",
                previewUrl = "https://cdn.pixabay.com/photo/2014/01/18/11/09/blossom-247409_150.jpg"
            ),
            Item(
                id = 5142952,
                pageUrl = "https://pixabay.com/photos/flower-yellow-water-nature-5142952/",
                type = "photo",
                tags = "flower, beautiful flowers, flower wallpaper, yellow, water, flower background, nature",
                previewUrl = "https://cdn.pixabay.com/photo/2020/05/07/19/49/flower-5142952_150.jpg"
            ),
            Item(
                id = 6999568,
                pageUrl = "https://pixabay.com/photos/hibiscus-yellow-hibiscus-6999568/",
                type = "photo",
                tags = "hibiscus, yellow hibiscus, yellow flower, nature, macro",
                previewUrl = "https://cdn.pixabay.com/photo/2022/02/07/14/58/hibiscus-6999568_150.jpg"
            ),
            Item(
                id = 6480762,
                pageUrl = "https://pixabay.com/photos/flower-yellow-flower-withered-6480762/",
                type = "photo",
                tags = "flower, yellow flower, withered, petals, yellow petals, bloom, blossom, beautiful flowers, flora, nature, plant, flower background, flower wallpaper, close up, macro",
                previewUrl = "https://cdn.pixabay.com/photo/2021/07/20/13/29/flower-6480762_150.jpg"
            ),
            Item(
                id = 8252992,
                pageUrl = "https://pixabay.com/photos/rose-yellow-rose-blossom-bloom-8252992/",
                type = "photo",
                tags = "rose, yellow rose, beautiful flowers, flower background, blossom, bloom, flower, nature, yellow, rose flower, yellow flower, flower wallpaper",
                previewUrl = "https://cdn.pixabay.com/photo/2023/09/14/13/22/rose-8252992_150.jpg"
            ),
            Item(
                id = 6353123,
                pageUrl = "https://pixabay.com/photos/poppy-flower-yellow-poppy-6353123/",
                type = "photo",
                tags = "poppy, flower, yellow poppy, beautiful flowers, yellow flowers, petals, blossom, bloom, flower wallpaper, flower background, flora, nature, up close",
                previewUrl = "https://cdn.pixabay.com/photo/2021/06/21/09/10/poppy-6353123_150.jpg"
            ),
            Item(
                id = 8114428,
                pageUrl = "https://pixabay.com/photos/brush-beetle-insect-yellow-rose-8114428/",
                type = "photo",
                tags = "brush beetle, insect, yellow rose, pollination, rose flower, rose, yellow flower, blossom, bloom, garden, nature, close up, macro",
                previewUrl = "https://cdn.pixabay.com/photo/2023/07/08/12/22/brush-beetle-8114428_150.jpg"
            )
        )
    )

    override fun getItemsSortedById(): Flow<List<Item>> =
        _items
            .map { items ->
                items.sortedByDescending { it.id }
            }
            .distinctUntilChanged()

    override fun getItemById(id: Long): Flow<Item?> =
        _items
            .map { items ->
                items.firstOrNull { it.id == id }
            }
            .distinctUntilChanged()
}