package com.gourmet.perfume.entity

import com.gourmet.perfume.enums.GenderEnums
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("categories")
data class Category @JvmOverloads constructor(

        @Id
        var id: String?,
        var name: String,
        var gender: GenderEnums

): BaseEntity()
