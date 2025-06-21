package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.Payment;
import kg.mega.kindergarten.models.dtos.PaymentCreateDto;
import kg.mega.kindergarten.models.dtos.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);
    @Mapping(source = "childId", target = "child.id")
    Payment paymentCreateDtoToPayment(PaymentCreateDto paymentCreateDto);
    PaymentDto paymentToPaymentDto(Payment payment);
    Payment paymentDtoToPayment(PaymentDto paymentDto);
    List<PaymentDto> paymentToPaymentDto(List<Payment> payment);

}
