package ua.dp.rename.dniprostreets.core

import ua.dp.rename.dniprostreets.repo.SnappyMigrator
import ua.dp.rename.dniprostreets.repo.SnappyRepository

class SnappyInitializer(
      private var db: SnappyRepository?
) : ComponentInitializer {

   override fun init() {
      SnappyMigrator(db).tryMigration()
      db = null
   }
}
