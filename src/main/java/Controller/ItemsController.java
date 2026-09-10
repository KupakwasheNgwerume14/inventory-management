package com.petrotrade.inventory.controller;

import com.petrotrade.inventory.dto.ItemRequest;
import com.petrotrade.inventory.model.Item;
import com.petrotrade.inventory.service.ItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/item")
@Slf4j
public class ItemsController {

    private final ItemService itemService;

    public ItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Create Item
    @PostMapping
    public ResponseEntity<Item> createItem(@Valid @RequestBody ItemRequest request) {

        Item item = new Item();

        item.setName(request.getName());
        item.setSku(request.getSku());
        item.setCategory(request.getCategory());
        item.setQuantity(request.getQuantity());
        item.setUnitPrice(request.getUnitPrice());
        item.setReorderLevel(request.getReorderLevel());

        Item savedItem = itemService.saveItem(item);

        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    // Get All Items
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    // Get Item By ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemService.getItemById(id);

        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update Item
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(
            @PathVariable Long id,
            @RequestBody Item item) {

        log.info("Updating item: {}", item);

        return ResponseEntity.ok(itemService.updateItem(id, item));
    }

    // Delete Item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    // Low Stock Items
    @GetMapping("/low-stock")
    public ResponseEntity<List<Item>> getLowStockItems() {
        return ResponseEntity.ok(itemService.getLowStockItems());
    }
}