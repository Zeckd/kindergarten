package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.Payment;
import kg.mega.kindergarten.models.dtos.PaymentSaveDto;
import kg.mega.kindergarten.models.dtos.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);
    @Mapping(source = "childId", target = "child.id")
    Payment paymentSaveDtoToPayment(PaymentSaveDto paymentSaveDto);
    PaymentDto paymentToPaymentDto(Payment payment);

}
