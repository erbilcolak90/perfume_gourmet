package com.gourmet.perfume.dto.input.user

import com.gourmet.perfume.enums.GenderEnums

data class CreateUserInput(

        var username: String,
        var password: String,
        var gender: GenderEnums?,

)
