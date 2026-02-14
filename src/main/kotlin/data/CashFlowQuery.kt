package org.delcom.data

data class CashFlowQuery(
    val type: String? = null,
    val source: String? = null,
    val labels: List<String>? = null,
    val gteAmount: Int? = null,
    val lteAmount: Int? = null,
    val search: String? = null,
    val startDate: String? = null,
    val endDate: String? = null
)
