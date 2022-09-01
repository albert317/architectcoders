package com.app.featureplaces.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}