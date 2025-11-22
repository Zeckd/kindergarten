package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.mappers.ChildGroupHistoryMapper;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.ChildGroupHistory;
import kg.mega.kindergarten.models.Group;
import kg.mega.kindergarten.models.Payment;
import kg.mega.kindergarten.models.dtos.ChildGroupHistorySaveDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDebtDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDto;
import kg.mega.kindergarten.repositories.ChildGroupHistoryRepo;
import kg.mega.kindergarten.services.ChildGroupHistoryService;
import kg.mega.kindergarten.services.ChildService;
import kg.mega.kindergarten.services.GroupService;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service

public class ChildGroupHistoryServiceImpl implements ChildGroupHistoryService {
    private final ChildGroupHistoryRepo childGroupHistoryRepo;
    private final ChildService childService;
    private final PaymentService paymentService;
    private final GroupService groupService;


    public ChildGroupHistoryServiceImpl(ChildGroupHistoryRepo childGroupHistoryRepo, ChildService childService, PaymentService paymentService, GroupService groupService) {
        this.childGroupHistoryRepo = childGroupHistoryRepo;
        this.childService = childService;
        this.paymentService = paymentService;
        this.groupService = groupService;
    }
    @Override
    public ChildGroupHistoryDto create(ChildGroupHistorySaveDto childGroupHistoryCreateDto) {
        Child child = childService.findById(childGroupHistoryCreateDto.childId());
        if (child == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ребенок не найден");
        }

        Group group = groupService.findById(childGroupHistoryCreateDto.groupId());
        if (group == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Группа не найдена");
        }

        if (group.getAgeGroup() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У группы не указана возрастная группа");
        }

        Payment payment = paymentService.findByChildId(child.getId());
        if (payment == null || payment.getPaymentDate() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "У ребенка нет платежей");
        }

        ChildGroupHistory childGroupHistory = ChildGroupHistoryMapper.INSTANCE.childGroupHistorySaveDtoToChildGroupHistory(childGroupHistoryCreateDto);
        LocalDateTime paymentDate = payment.getPaymentDate();
        LocalDate paymentLocalDate = paymentDate.toLocalDate();

        // Начало периода - первый день месяца платежа
        LocalDate startDate = paymentLocalDate.withDayOfMonth(1);
        childGroupHistory.setStartDate(startDate.atStartOfDay());

        // Конец периода - последний день месяца платежа
        LocalDate endDate = paymentLocalDate.withDayOfMonth(paymentLocalDate.lengthOfMonth());
        childGroupHistory.setEndDate(endDate.atTime(23, 59, 59));

        childGroupHistory.setGroup(group);
        childGroupHistory.setChild(child);
        childGroupHistory.setPrice(group.getAgeGroup().getPrice());

        childGroupHistory = childGroupHistoryRepo.save(childGroupHistory);

        return ChildGroupHistoryMapper.INSTANCE.childGroupHistoryToChildGroupHistoryDto(childGroupHistory);
    }

    @Override
    public ChildGroupHistoryDto update(Long id, ChildGroupHistorySaveDto childGroupHistorySaveDto, Delete delete) {
        ChildGroupHistory existingHistory = childGroupHistoryRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Запись истории не найдена"));

        Child child = childService.findById(childGroupHistorySaveDto.childId());
        if (child == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ребенок не найден");
        }

        Group group = groupService.findById(childGroupHistorySaveDto.groupId());
        if (group == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Группа не найдена");
        }

        if (group.getAgeGroup() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У группы не указана возрастная группа");
        }

        // Обновляем только необходимые поля, сохраняя существующие даты если они есть
        existingHistory.setGroup(group);
        existingHistory.setChild(child);
        existingHistory.setPrice(group.getAgeGroup().getPrice());
        existingHistory.setDelete(delete);

        ChildGroupHistory updatedHistory = childGroupHistoryRepo.save(existingHistory);

        return ChildGroupHistoryMapper.INSTANCE.childGroupHistoryToChildGroupHistoryDto(updatedHistory);
    }

    @Override
    public List<ChildGroupHistory> findAllList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return childGroupHistoryRepo.findAllList(pageable);

    }
    @Transactional
    @Override
    public ChildGroupHistoryDto delete(Long id) {
        ChildGroupHistory childGroupHistory = childGroupHistoryRepo.findByIdChildGroupHistory(id);
        if (childGroupHistory == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Запись истории не найдена");
        }
        childGroupHistoryRepo.deleteById(id);
        return ChildGroupHistoryMapper.INSTANCE.childGroupHistoryToChildGroupHistoryDto(childGroupHistory);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ребенок не найден");
        }

        ChildGroupHistory childGroupHistory = childGroupHistoryRepo.findTopByChildIdOrderByEndDateDesc(child.getId());
        if (childGroupHistory == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "История группы для ребенка не найдена");
        }

        if (childGroupHistory.getStartDate() == null || childGroupHistory.getEndDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У записи истории отсутствуют даты");
        }

        LocalDate startDate = childGroupHistory.getStartDate().toLocalDate();
        LocalDate endDate = childGroupHistory.getEndDate().toLocalDate();
        double price = childGroupHistory.getPrice();

        // Вычисляем рабочие дни в периоде
        int totalWorkingDays = countWorkingDays(startDate, endDate);
        if (totalWorkingDays == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "В периоде нет рабочих дней");
        }

        // Получаем сумму платежей за период
        int startMonth = startDate.getMonthValue();
        int startYear = startDate.getYear();
        int endMonth = endDate.getMonthValue();
        int endYear = endDate.getYear();

        double paymentSum = 0.0;
        if (startYear == endYear && startMonth == endMonth) {
            // Период в одном месяце
            paymentSum = paymentService.sumPaymentsByChildIdAndMonth(childId, startMonth, startYear);
        } else {
            // Период охватывает несколько месяцев - суммируем платежи за все месяцы
            LocalDate current = startDate.withDayOfMonth(1);
            while (!current.isAfter(endDate)) {
                paymentSum += paymentService.sumPaymentsByChildIdAndMonth(childId, current.getMonthValue(), current.getYear());
                current = current.plusMonths(1);
            }
        }

        // Вычисляем долг: цена за период минус сумма платежей
        double debt = Math.round((price - paymentSum) * 100.0) / 100.0;

        ChildGroupHistoryDebtDto dto = new ChildGroupHistoryDebtDto();
        dto.setChildId(child.getId());
        dto.setDebtAmount(Math.max(0, debt));

        return dto;
    }


    private int countWorkingDays(LocalDate from, LocalDate to) {
        return (int) Stream.iterate(from, d -> !d.isAfter(to), d -> d.plusDays(1))
                .filter(d -> d.getDayOfWeek() != DayOfWeek.SATURDAY &&
                        d.getDayOfWeek() != DayOfWeek.SUNDAY)
                .count();
    }

    @Override
    public Double getPriceForPeriod(Long childId, String period) {
        String[] parts = period.split("\\.");
        if (parts.length != 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid period format. Use MM.yyyy");
        }
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);
        LocalDate date = LocalDate.of(year, month, 1);

        List<ChildGroupHistory> histories = childGroupHistoryRepo.findAllByChildId(childId);

        for (ChildGroupHistory history : histories) {
            LocalDate start = history.getStartDate().toLocalDate();
            LocalDate end = history.getEndDate() != null ? history.getEndDate().toLocalDate() : LocalDate.MAX;

            if ((date.isEqual(start) || date.isAfter(start)) && (date.isEqual(end) || date.isBefore(end))) {
                return history.getPrice();
            }
        }

        Child child = childService.findById(childId);
        if (child != null && child.getGroup() != null && child.getGroup().getAgeGroup() != null) {
            return child.getGroup().getAgeGroup().getPrice();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Price not found for period");
    }
}
