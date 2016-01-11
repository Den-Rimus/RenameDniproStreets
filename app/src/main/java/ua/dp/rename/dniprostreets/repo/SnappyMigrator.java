package ua.dp.rename.dniprostreets.repo;

public class SnappyMigrator {

    private SnappyRepository db;

    public SnappyMigrator(SnappyRepository db) {
        this.db = db;
    }

    public void tryMigration() {
        int currentVersion = db.getCacheVersion();
        if (currentVersion < SnappyRepository.CACHE_VERSION)
            migrate(SnappyRepository.CACHE_VERSION);
    }

    private void migrate(int newVersion) {
        if (newVersion == 1) {
            db.clearAll();
            db.setCacheVersion(1);
        }
        //
        if (newVersion == 2) {
            // etc
        }
    }
}
