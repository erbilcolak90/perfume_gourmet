package com.gourmet.perfume.dto.input.favorite

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class GetAllFavoritesByUserIdInput(
        var userId: String,
        var size: Int,
        var number: Int,
        var sortBy: String? = null,
        var sortOrder: Sort.Direction = Sort.Direction.ASC
) {
    fun toPageable(): Pageable {
        val sort: Sort = if (sortBy != null) {
            Sort.by(sortOrder, sortBy)
        } else {
            Sort.unsorted()
        }
        return PageRequest.of(number, size, sort)
    }
}