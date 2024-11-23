package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // Check if product is in stock
    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        return inventoryService.isInStock(skuCode, quantity);
    }

    // Get all inventory
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    // Get inventory by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inventory getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    // Create new inventory
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventoryRequest) {
        Inventory createdInventory = inventoryService.createInventory(inventoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInventory);
    }

    // Update inventory by ID
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryRequest) {
        String message = inventoryService.updateInventory(id, inventoryRequest);
        return ResponseEntity.ok(message);
    }

    // Delete inventory by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
    }
}
