package com.example.miquido.domain.repository

interface Paginator<Key, Item> {
    suspend fun loadNextPage()
}