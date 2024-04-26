package com.gourmet.perfume.dto.input.category

import com.gourmet.perfume.enums.GenderEnums

data class UpdateCategoryGenderInput(

        var id: String,
        var gender: GenderEnums
)
