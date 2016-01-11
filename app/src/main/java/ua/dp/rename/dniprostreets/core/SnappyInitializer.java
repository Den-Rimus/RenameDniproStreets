package ua.dp.rename.dniprostreets.core;

import ua.dp.rename.dniprostreets.repo.SnappyMigrator;
import ua.dp.rename.dniprostreets.repo.SnappyRepository;

public class SnappyInitializer implements ComponentInitializer {

    SnappyRepository db;

    public SnappyInitializer(SnappyRepository db) {
        this.db = db;
    }

    @Override
    public void init() {
        new SnappyMigrator(db).tryMigration();
        db = null;
    }
}
