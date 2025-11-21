package kg.mega.kindergarten.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.mega.kindergarten.enums.BillStatus;

public record BillResponseDto(
        @Schema(description = "ID счета", example = "1")
        Long billId,

        @Schema(description = "ID ребенка", example = "1")
        Long childId,

        @Schema(description = "Сумма к оплате", example = "5000.00")
        double amount,

        @Schema(description = "Ссылка на оплату", example = "https://payment.example.com/pay/abc123")
        String paymentLink,

        @Schema(description = "QR код для оплаты (base64 или ссылка)", example = "data:image/png;base64,iVBORw0KG...")
        String qrCode,

        @Schema(description = "Статус платежа", example = "PENDING")
        BillStatus status
) {
}

