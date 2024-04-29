package com.gourmet.perfume.entity

import com.gourmet.perfume.enums.GenderEnums
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(

        @Id
        var id: String?,
        var username: String,
        var password: String,
        var gender: GenderEnums?,
        var avatarId: String?
): BaseEntity()
