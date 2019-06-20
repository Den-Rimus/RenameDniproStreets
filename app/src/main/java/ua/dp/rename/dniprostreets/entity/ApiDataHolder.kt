package ua.dp.rename.dniprostreets.entity

data class ApiDataHolder(
      private val r61: CityRegion,
      private val r62: CityRegion,
      private val r63: CityRegion,
      private val r64: CityRegion,
      private val r65: CityRegion,
      private val r66: CityRegion,
      private val r67: CityRegion,
      private val r68: CityRegion
) {

   fun getRegionsAsList(): List<CityRegion> {
      return listOf(
            r61.copy(id = "r61"),
            r62.copy(id = "r62"),
            r63.copy(id = "r63"),
            r64.copy(id = "r64"),
            r65.copy(id = "r65"),
            r66.copy(id = "r66"),
            r67.copy(id = "r67"),
            r68.copy(id = "r68")
      )
   }
}
