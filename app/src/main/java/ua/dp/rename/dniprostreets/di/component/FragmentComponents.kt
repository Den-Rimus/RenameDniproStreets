package ua.dp.rename.dniprostreets.di.component

import dagger.Component
import ua.dp.rename.dniprostreets.di.RegionDetailScope
import ua.dp.rename.dniprostreets.di.RegionsListScope
import ua.dp.rename.dniprostreets.presenter.RegionsListPresenterM
import ua.dp.rename.dniprostreets.presenter.RenamedObjectsListPresenterM

@RegionsListScope
@Component(dependencies = [AppComponent::class])
interface RegionsListComponent {

   fun inject(presenter: RegionsListPresenterM)
}

@RegionDetailScope
@Component(dependencies = [AppComponent::class])
interface RegionDetailComponent {

   fun inject(presenter: RenamedObjectsListPresenterM)
}
