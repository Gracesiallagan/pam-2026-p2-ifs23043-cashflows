package org.delcom.data

import kotlinx.serialization.Serializable

@Serializable
open class AppException(
    val code: Int,
    override val message: String
) : RuntimeException(message)

class ValidationException(
    val errors: Map<String, String>
) : AppException(400, "Data yang dikirimkan tidak valid!")