package com.petrotrade.inventory.service;

import com.petrotrade.inventory.model.Item;
import com.petrotrade.inventory.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Create Item
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    // Get All Items
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Get Item By ID
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    // Update Item
    public Item updateItem(Long id, Item updatedItem) {

        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        existingItem.setName(updatedItem.getName());
        existingItem.setSku(updatedItem.getSku());
        existingItem.setCategory(updatedItem.getCategory());
        existingItem.setQuantity(updatedItem.getQuantity());
        existingItem.setUnitPrice(updatedItem.getUnitPrice());
        existingItem.setReorderLevel(updatedItem.getReorderLevel());

        return itemRepository.save(existingItem);
    }

    // Delete Item
    public void deleteItem(Long id) {

        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item not found");
        }

        itemRepository.deleteById(id);
    }

    // Low Stock
    public List<Item> getLowStockItems() {

        List<Item> items = itemRepository.findAll();

        return items.stream()
                .filter(item -> item.getQuantity() <= item.getReorderLevel())
                .toList();
    }
}