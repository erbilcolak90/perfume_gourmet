package com.gourmet.perfume.entity

import com.gourmet.perfume.enums.TypeEnums
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("perfumes")
data class Perfume @JvmOverloads constructor(

        @Id
        var id: String?,
        var name: String,
        var brand: String,
        var year: Int,
        var categoryIds: List<String>?,
        var type: TypeEnums,
        var content: String?,
        var description: String?,
        var thumbnailIds: List<String>?
): BaseEntity(){

        constructor() : this(null, "", "", 0, null, TypeEnums.EAU_DE_PERFUME, null, null, null)
}