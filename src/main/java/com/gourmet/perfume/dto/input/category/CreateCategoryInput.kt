package com.gourmet.perfume.dto.input.category

import com.gourmet.perfume.enums.GenderEnums
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateCategoryInput(

        @NotEmpty
        @NotBlank
        @NotNull
        @Size(min = 3, max = 50, message = "Category name length must be between 3-50")
        var name: String,
        @NotNull
        @NotEmpty
        var gender: GenderEnums
)
