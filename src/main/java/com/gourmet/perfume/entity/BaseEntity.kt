package com.gourmet.perfume.entity

import java.time.LocalDateTime

abstract class BaseEntity {

    var createDate: LocalDateTime = LocalDateTime.now()
    var updateDate: LocalDateTime = LocalDateTime.now()
    var isDeleted: Boolean = false
}