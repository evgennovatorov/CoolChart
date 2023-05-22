package com.evgenii.coolgraph.di

import com.evgenii.coolgraph.api.repositories.web.Repository
import com.evgenii.coolgraph.api.retrofit.ApiRequests
import com.evgenii.coolgraph.api.repositories.web.InteractiveRepository
import com.evgenii.coolgraph.api.okhttp.InteractiveOkHttpProvider
import com.evgenii.coolgraph.api.okhttp.OkhttpProvider
import com.evgenii.coolgraph.api.retrofit.InteractiveRetrofitProvider
import com.evgenii.coolgraph.api.retrofit.RetrofitProvider
import com.evgenii.coolgraph.api.retrofit.provide
import com.evgenii.coolgraph.business.GetPointUseCase
import com.evgenii.coolgraph.business.GetPointUseCaseImpl
import com.evgenii.coolgraph.common.AppLogger
import com.evgenii.coolgraph.common.ConsoleLogger
import com.evgenii.coolgraph.ui.start.StartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppDI {
    val appModule = module {
        factory<RetrofitProvider> { InteractiveRetrofitProvider(get()) }
        single<ApiRequests> { provide(get()) }
        single<Repository> { InteractiveRepository(get()) }
        factory<GetPointUseCase> { GetPointUseCaseImpl(get()) }
        single<AppLogger> { ConsoleLogger() }
        factory<OkhttpProvider> { InteractiveOkHttpProvider() }
    }

    val viewModelModule = module {
        viewModel { StartViewModel(get(), get()) }
    }
}