package kg.mega.kindergarten.services;

import kg.mega.kindergarten.models.dtos.BillResponseDto;

public interface BillService {

    BillResponseDto generateBill(Long childId);

    BillResponseDto generateBillForPeriod(Long childId, String period);

    BillResponseDto getBillStatus(Long billId);
}

