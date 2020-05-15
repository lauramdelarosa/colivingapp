package com.naez.colivingapp.di

import android.app.Application
import com.naez.colivingapp.R
import com.naez.colivingapp.data.PlayServicesLocationDataSource
import com.naez.colivingapp.data.database.SpaceDatabase
import com.naez.colivingapp.data.database.source.RoomDataSource
import com.naez.colivingapp.data.server.RetrofitBuild
import com.naez.colivingapp.data.server.endpoints.SpaceService
import com.naez.colivingapp.data.server.source.RemoteSpaceDataSource
import com.naez.colivingapp.ui.spacesDetail.DetailViewModel
import com.naez.colivingapp.ui.spaces.SpaceViewModel
import com.naez.colivingapp.ui.spaces.SpaceFragment
import com.naez.colivingapp.ui.spacesDetail.SpaceDetailFragment
import com.naez.colivingapp.utils.AndroidPermissionChecker
import com.naez.data.repository.SpaceRepository
import com.naez.data.source.LocalDataSource
import com.naez.data.source.LocationDataSource
import com.naez.data.source.PermissionChecker
import com.naez.data.source.RemoteDataSource
import com.naez.usecases.FindSpaceById
import com.naez.usecases.GetSpaces
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, presentationModule))
    }
}

private val presentationModule = module {
    scope(named<SpaceFragment>()) {
        viewModel { SpaceViewModel(getSpaces = get(), uiDispatcher = get()) }
        scoped { GetSpaces(spaceRepository = get()) }
    }

    scope(named<SpaceDetailFragment>()) {
        viewModel { (id: Int) -> DetailViewModel(id, get(), get()) }
        scoped { FindSpaceById(spaceRepository = get()) }
    }
}

val dataModule = module {

    factory { SpaceRepository(localDataSource = get(), remoteDataSource = get()) }
}

private val appModule = module {
    single { SpaceDatabase.build(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single { get<RetrofitBuild>().retrofit.create(SpaceService::class.java) }
    single { RetrofitBuild(baseUrl = androidContext().resources.getString(R.string.base_url)) }
    factory<LocalDataSource> { RoomDataSource(db = get()) }
    factory<RemoteDataSource> { RemoteSpaceDataSource(spaceService = get()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(application = get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(application = get()) }

}
