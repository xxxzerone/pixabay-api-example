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
                    tags = "chrysanthemum, garden chrysanthemums, beautiful flowers, flower, blossom, bloom, yellow, petals, yellow flowers, yellow flower, flower background, flora, floriculture, horticulture, botany, nature, flower wallpaper, up close",
                    previewUrl = "https://cdn.pixabay.com/photo/2020/10/19/19/58/chrysanthemum-5668882_150.jpg",
                    user = "mariya_m",
                ),
                Item(
                    id = 6162613,
                    tags = "yellow rose, rose, flower, cereal, flower wallpaper, yellow flower, garden, nature, closeup, plants, flora, beautiful flowers, fragrant, plant, floral, blossomed, light yellow, beautiful, flower background, flowers, rose flower, roses, soft",
                    previewUrl = "https://cdn.pixabay.com/photo/2021/04/08/18/59/yellow-rose-6162613_150.jpg",
                    user = "Nowaja",
                ),
                Item(
                    id = 4042853,
                    tags = "sulphur anemone, flowers, yellow flower, petals, beautiful flowers, yellow petals, blossom, flower background, flower wallpaper, bloom, flora, plant, nature",
                    previewUrl = "https://cdn.pixabay.com/photo/2019/03/08/17/43/sulphur-anemone-4042853_150.jpg",
                    user = "gsibergerin",
                ),
                Item(
                    id = 247409,
                    tags = "blossom, bloom, macro, garden, flowers, plant, flower background, flower, star, nature, summer, yellow, close up view yellow, bloom, petals, bright yellow, flora, beautiful flowers, sunny yellow, flower wallpaper, bright, flower macro",
                    previewUrl = "https://cdn.pixabay.com/photo/2014/01/18/11/09/blossom-247409_150.jpg",
                    user = "cocoparisienne",
                ),
                Item(
                    id = 5142952,
                    tags = "flower, flower background, flower wallpaper, beautiful flowers, yellow, water, nature",
                    previewUrl = "https://cdn.pixabay.com/photo/2020/05/07/19/49/flower-5142952_150.jpg",
                    user = "LuxuriousFox",
                )
            )
        )
    }
}
