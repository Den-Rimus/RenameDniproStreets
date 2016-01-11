package ua.dp.rename.dniprostreets.core;

import timber.log.Timber;
import ua.dp.rename.dniprostreets.BuildConfig;

public class LogInitializer implements ComponentInitializer {

    @Override
    public void init() {
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }
}
