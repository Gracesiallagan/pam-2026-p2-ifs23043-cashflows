package org.delcom.helpers

import org.delcom.data.AppException
import org.delcom.data.CashFlowRequest

object ValidatorHelper {

    fun validate(request: CashFlowRequest) {

        if (request.type.isBlank())
            throw AppException("Type tidak boleh kosong")

        if (request.source.isBlank())
            throw AppException("Source tidak boleh kosong")

        if (request.label.isBlank())
            throw AppException("Label tidak boleh kosong")

        if (request.description.isBlank())
            throw AppException("Description tidak boleh kosong")

        if (request.amount <= 0)
            throw AppException("Amount harus lebih dari 0")
    }
}
