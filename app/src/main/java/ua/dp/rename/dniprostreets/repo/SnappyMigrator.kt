package ua.dp.rename.dniprostreets.repo

class SnappyMigrator(private val db: SnappyRepository) {

   fun checkAndTryMigrate() {
      if (db.cacheVersion == SnappyRepository.CACHE_VERSION) return

      db.clearAll()
      db.cacheVersion = SnappyRepository.CACHE_VERSION
   }
}
