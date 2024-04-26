package com.gourmet.perfume.dto.payload.perfume

import com.gourmet.perfume.entity.Perfume
import com.gourmet.perfume.enums.TypeEnums

data class PerfumePayload @JvmOverloads constructor(

        var id: String?,
        var name: String,
        var brand: String,
        var year: Int,
        var categoryIds: List<String>?,
        var type: TypeEnums,
        var content: String?,
        var description: String?,
        var thumbnailIds: List<String>?
){
    companion object{
        @JvmStatic
        fun convert(from: Perfume): PerfumePayload?{
            return PerfumePayload(
                    from.id,
                    from.name,
                    from.brand,
                    from.year,
                    from.categoryIds,
                    from.type,
                    from.content,
                    from.description,
                    from.thumbnailIds

            )
        }
    }
}
