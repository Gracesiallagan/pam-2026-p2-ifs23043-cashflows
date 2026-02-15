package org.delcom.data

import kotlinx.serialization.Serializable

@Serializable
data class CashFlowRequest(
    val type: String,
    val source: String,
    val label: String,
    val amount: Double,
    val description: String
)
