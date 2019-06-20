package ua.dp.rename.dniprostreets

import ua.dp.rename.dniprostreets.entity.CityRegion
import ua.dp.rename.dniprostreets.entity.ObjectType
import ua.dp.rename.dniprostreets.entity.RenamedObject

val renamedObject1 = RenamedObject(
      type = ObjectType.AVENUE,
      oldName = "RenamedAvenue1_old",
      newName = "RenamedAvenue1_new",
      link = null,
      regionOldName = "",
      regionNewName = ""
)

val renamedObject2 = RenamedObject(
      type = ObjectType.STREET,
      oldName = "RenamedStreet1_old",
      newName = "RenamedStreet1_new",
      link = null,
      regionOldName = "",
      regionNewName = ""
)

val renamedObject3 = RenamedObject(
      type = ObjectType.AVENUE,
      oldName = "RenamedAvenue2_old",
      newName = "RenamedAvenue2_new",
      link = null,
      regionOldName = "",
      regionNewName = ""
)

val renamedObject4 = RenamedObject(
      type = ObjectType.STREET,
      oldName = "RenamedStreet2_old",
      newName = "RenamedStreet2_new",
      link = null,
      regionOldName = "",
      regionNewName = ""
)

val renamedRegion1 = CityRegion(
      id = "renamedRegion1",
      oldAreaName = "renamedRegion1_old",
      newAreaName = "renamedRegion1_new",
      objects = listOf(renamedObject1, renamedObject2)
)

val renamedRegion2 = CityRegion(
      id = "renamedRegion2",
      oldAreaName = "renamedRegion2_old",
      newAreaName = "renamedRegion2_new",
      objects = listOf(renamedObject3, renamedObject4)
)

val regionsList = listOf(renamedRegion1, renamedRegion2)

val allRenamedObjectsList: List<RenamedObject> = listOf(
      renamedObject1,
      renamedObject2,
      renamedObject3,
      renamedObject4
)
