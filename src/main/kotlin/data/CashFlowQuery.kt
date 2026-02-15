package org.delcom.data

data class CashFlowQuery(
    val type: String? = null,
    val source: String? = null,
    val labels: String? = null,
    val search: String? = null,
    val gteAmount: Int? = null,
    val lteAmount: Int? = null,
    val startDate: String? = null,
    val endDate: String? = null
)
