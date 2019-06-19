package ua.dp.rename.dniprostreets.di

import javax.inject.Qualifier
import javax.inject.Scope

@Qualifier
@Retention
annotation class AppContext

@Scope
@Retention
annotation class RegionsListScope

@Scope
@Retention
annotation class RegionDetailScope
