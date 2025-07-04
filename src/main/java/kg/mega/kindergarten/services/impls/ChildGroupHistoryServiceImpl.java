package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.mappers.ChildGroupHistoryMapper;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.ChildGroupHistory;
import kg.mega.kindergarten.models.Payment;
import kg.mega.kindergarten.models.dtos.ChildGroupHistorySaveDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDebtDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDto;
import kg.mega.kindergarten.repositories.ChildGroupHistoryRepo;
import kg.mega.kindergarten.services.ChildGroupHistoryService;
import kg.mega.kindergarten.services.ChildService;
import kg.mega.kindergarten.services.PaymentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service

public class ChildGroupHistoryServiceImpl implements ChildGroupHistoryService {
    private final ChildGroupHistoryRepo childGroupHistoryRepo;
    private final ChildService childService;
    private final PaymentService paymentService;


    public ChildGroupHistoryServiceImpl(ChildGroupHistoryRepo childGroupHistoryRepo, ChildService childService, PaymentService paymentService) {
        this.childGroupHistoryRepo = childGroupHistoryRepo;

        this.childService = childService;
        this.paymentService = paymentService;
    }
    @Override
    public ChildGroupHistoryDto create(ChildGroupHistorySaveDto childGroupHistoryCreateDto) {
        Child child = childService.findById(childGroupHistoryCreateDto.childId());
        if (child == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Payment payment = paymentService.findByChildId(child.getId());
        if (payment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        ChildGroupHistory childGroupHistory = ChildGroupHistoryMapper.INSTANCE.childGroupHistorySaveDtoToChildGroupHistory(childGroupHistoryCreateDto);
        childGroupHistory.setStartDate(payment.getPaymentDate());
        if (payment.getPaymentDate() == null) {
            throw new RuntimeException("У ребенка нет платежей");
        }
        childGroupHistory.setEndDate(payment.getPaymentDate()
                .toLocalDate()
                .plusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay());
        childGroupHistory.setGroup(child.getGroup());
        childGroupHistory.setChild(child);
        childGroupHistory.setPrice(child.getGroup().getAgeGroup().getPrice());


        childGroupHistory = childGroupHistoryRepo.save(childGroupHistory);

        return ChildGroupHistoryMapper.INSTANCE.childGroupHistoryToChildGroupHistoryDto(childGroupHistory);
    }

    @Override
    public ChildGroupHistoryDto update(Long id, ChildGroupHistorySaveDto childGroupHistorySaveDto, Delete delete) {
        ChildGroupHistory childGroupHistoryId = childGroupHistoryRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        ChildGroupHistory childGroupHistory = ChildGroupHistoryMapper.INSTANCE.childGroupHistorySaveDtoToChildGroupHistory(childGroupHistorySaveDto);
        childGroupHistory.setId(id);
        childGroupHistory.setDelete(delete);
        childGroupHistory = childGroupHistoryRepo.save(childGroupHistory);


        return ChildGroupHistoryMapper.INSTANCE.childGroupHistoryToChildGroupHistoryDto(childGroupHistory);
    }

    @Override
    public List<ChildGroupHistory> findAllList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return childGroupHistoryRepo.findAllList(pageable);

    }
    @Transactional

    @Override
    public ChildGroupHistoryDto delete(Long id) {
        ChildGroupHistory childGroupHistory = childGroupHistoryRepo.findById(id).orElseThrow();
        childGroupHistoryRepo.deleteById(id);
        return ChildGroupHistoryMapper.INSTANCE.childGroupHistoryToChildGroupHistoryDto(childGroupHistory) ;

    }


    @Override
    public ChildGroupHistory findById(Long id) {
        ChildGroupHistory childGroupHistory = childGroupHistoryRepo.findByIdChildGroupHistory(id);
        if (childGroupHistory == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
        }
        return childGroupHistory;

    }

    @Override
    public ChildGroupHistoryDebtDto findDebtByChildId(Long childId) {
        Child child = childService.findById(childId);
        if (child == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        ChildGroupHistory childGroupHistory = childGroupHistoryRepo.findTopByChildIdOrderByEndDateDesc(child.getId());
        LocalDate startDate = childGroupHistory.getStartDate().toLocalDate();
        double price = childGroupHistory.getPrice();
        LocalDate lastDayOfMonth = startDate.withDayOfMonth(startDate.lengthOfMonth());
        int totalWorkingDays = countWorkingDays(startDate.withDayOfMonth(1), lastDayOfMonth);
        int actualWorkingDays = countWorkingDays(startDate, lastDayOfMonth);
        int month = startDate.getMonthValue();
        int year = startDate.getYear();
        double paymentSum = paymentService.sumPaymentsByChildIdAndMonth(childId, month, year);

        double dailyRate = price / totalWorkingDays;
        double count = dailyRate * actualWorkingDays;
        double debt = Math.round((count - paymentSum));
        ChildGroupHistoryDebtDto dto = new ChildGroupHistoryDebtDto();
        dto.setChildId(child.getId());
        if (debt <= 0) {

            dto.setDebtAmount(0);
        } else {
            dto.setDebtAmount(debt);
        }
        return dto;
    }


    private int countWorkingDays(LocalDate from, LocalDate to) {
        return (int) Stream.iterate(from, d -> !d.isAfter(to), d -> d.plusDays(1))
                .filter(d -> d.getDayOfWeek() != DayOfWeek.SATURDAY &&
                        d.getDayOfWeek() != DayOfWeek.SUNDAY)
                .count();
    }
}
