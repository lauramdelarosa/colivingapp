package com.naez.colivingapp

import com.naez.colivingapp.di.dataModule
import com.naez.data.ResultData
import com.naez.data.source.LocalDataSource
import com.naez.data.source.LocationDataSource
import com.naez.data.source.PermissionChecker
import com.naez.data.source.RemoteDataSource
import com.naez.domain.Space
import com.naez.testshared.mockedSpace
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single<LocalDataSource> { FakeLocalDataSource() }
    single<RemoteDataSource> { FakeRemoteDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

val defaultFakeSpaces = listOf(
    mockedSpace.copy(1),
    mockedSpace.copy(2),
    mockedSpace.copy(3),
    mockedSpace.copy(4)
)

class FakeLocalDataSource : LocalDataSource {

    var spaces: List<Space> = emptyList()

    override suspend fun isEmpty() = spaces.isEmpty()

    override suspend fun saveSpaces(spaces: List<Space>) {
        this.spaces = spaces
    }

    override suspend fun getSpaces(): List<Space> = spaces

    override suspend fun findById(id: Int): Space = spaces.first { it.id == id }

    override suspend fun update(space: Space) {
        spaces = spaces.filterNot { it.id == space.id } + space
    }
}

class FakeRemoteDataSource : RemoteDataSource {

    var spaces = defaultFakeSpaces

    override suspend fun getColivingList(): ResultData<List<Space>> =ResultData.Success(spaces)
}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String? = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}