package com.gourmet.perfume.dto.payload.user

import com.gourmet.perfume.entity.User

data class UserPayload(

        var id: String?,
        var username: String,
        var avatarId: String?
){
    companion object{
        @JvmStatic
        fun convert(from: User): UserPayload?{
            return UserPayload(
                    from.id,
                    from.username,
                    from.avatarId
            )
        }
    }
}
