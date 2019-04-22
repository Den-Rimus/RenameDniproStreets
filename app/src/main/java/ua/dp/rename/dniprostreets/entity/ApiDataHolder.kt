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
      r61.id = "r61"
      r62.id = "r62"
      r63.id = "r63"
      r64.id = "r64"
      r65.id = "r65"
      r66.id = "r66"
      r67.id = "r67"
      r68.id = "r68"

      return listOf(r61, r62, r63, r64, r65, r66, r67, r68)
   }

   companion object {

      fun getRegionsAsMap(regions: List<CityRegion>): Map<String, CityRegion> {
         val asMap = mutableMapOf<String, CityRegion>()
         regions.forEach { asMap[it.id] = it }
         return asMap
      }
   }
}
