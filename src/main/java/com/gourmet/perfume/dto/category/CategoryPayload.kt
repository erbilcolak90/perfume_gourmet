package com.gourmet.perfume.dto.category

import com.gourmet.perfume.entity.Category
import com.gourmet.perfume.enums.GenderEnums

data class CategoryPayload @JvmOverloads constructor(
        var id: String,
        var name: String,
        var gender: GenderEnums
){
    companion object {
        @JvmStatic
        fun convert(from: Category): CategoryPayload?{
            return CategoryPayload(
                    from.id,
                    from.name,
                    from.gender
            )
        }
    }
}
