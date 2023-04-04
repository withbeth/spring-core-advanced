package hello.spring.core.advanced.aop.practical;

import org.springframework.stereotype.Service;

@Service
public class SimpleItemService {
    private final UnstableItemRepository itemRepository;

    public SimpleItemService(UnstableItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public String create(String itemId) {
        return itemRepository.save(itemId);
    }
}
