package com.naez.colivingapp.data.database.source

import com.naez.colivingapp.data.database.SpaceDatabase
import com.naez.data.source.LocalDataSource
import com.naez.domain.Space
import com.naez.colivingapp.data.mappers.toDomainSpace
import com.naez.colivingapp.data.mappers.toRoomSpace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: SpaceDatabase) : LocalDataSource {

    private val spaceDao = db.spaceDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { spaceDao.spaceCount() <= 0 }

    override suspend fun saveSpaces(spaces: List<Space>) {

        withContext(Dispatchers.IO) { spaceDao.insertSpace(spaces.map { it.toRoomSpace() }) }
    }

    override suspend fun getSpaces(): List<Space> = withContext(Dispatchers.IO) {
        spaceDao.getAll().map { it.toDomainSpace() }
    }

    override suspend fun findById(id: Int): Space = withContext(Dispatchers.IO) {
        spaceDao.findById(id).toDomainSpace()
    }

    override suspend fun update(space: Space) {
        withContext(Dispatchers.IO) { spaceDao.updateSpace(space.toRoomSpace()) }
    }
}