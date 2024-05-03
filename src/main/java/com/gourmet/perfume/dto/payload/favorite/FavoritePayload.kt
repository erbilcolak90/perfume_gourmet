package com.gourmet.perfume.dto.payload.favorite

import com.gourmet.perfume.entity.Favorite

data class FavoritePayload(

        var id: String?,
        var userId: String,
        var perfumeId: String
){
    companion object{
        @JvmStatic
        fun convert(from: Favorite): FavoritePayload?{
            return FavoritePayload(
                    from.id,
                    from.userId,
                    from.perfumeId
            )
        }
    }
}
