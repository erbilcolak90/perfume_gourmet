package com.gourmet.perfume.dto.input.perfume

import com.gourmet.perfume.enums.TypeEnums

data class AddPerfumeInput @JvmOverloads constructor(

        var name: String,
        var brand: String,
        var year: Int,
        var categoryIds: List<String>?,
        var type: TypeEnums,
        var content: String?,
        var description: String?,
        var thumbnailIds: List<String>?
)
