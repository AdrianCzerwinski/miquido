package com.example.miquido.data.repository

import com.example.miquido.domain.model.Photo
import com.example.miquido.domain.model.Response
import com.example.miquido.domain.repository.Paginator

class PaginatorImpl<Key, Item>(
    private val initialPage: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextPage: Key) -> Response<List<Photo>>,
    private inline val getNextPage: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
): Paginator<Key, Item> {

    private var currentPage = initialPage
    private var isMakingRequest = false

    override suspend fun loadNextPage() {
        TODO("Not yet implemented")
    }
}