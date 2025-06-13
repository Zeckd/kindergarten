package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.repositories.ChildGroupHistoryRepo;
import kg.mega.kindergarten.services.ChildGroupHistoryService;
import org.springframework.stereotype.Service;

@Service

public class ChildGroupHistoryServiceImpl implements ChildGroupHistoryService {
    private final ChildGroupHistoryRepo childGroupHistoryRepo;

    public ChildGroupHistoryServiceImpl(ChildGroupHistoryRepo childGroupHistoryRepo) {
        this.childGroupHistoryRepo = childGroupHistoryRepo;

    }
}
