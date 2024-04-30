package com.gourmet.perfume.entity

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "favorites")
data class Favorite(

        @Id
        var id: String?,
        @Field(type = FieldType.Keyword)
        var userId: String,
        @Field(type = FieldType.Keyword)
        var perfumeId: String
): BaseEntity()
