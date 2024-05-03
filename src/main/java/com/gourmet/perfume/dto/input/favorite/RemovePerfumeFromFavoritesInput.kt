package com.gourmet.perfume.dto.input.favorite

data class RemovePerfumeFromFavoritesInput(

        var userId: String,
        var perfumeId: String,
)
