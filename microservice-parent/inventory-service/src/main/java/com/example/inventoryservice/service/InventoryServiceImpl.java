package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public boolean isInStock(String skuCode, Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory with ID " + id + " not found"));
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        // Save new inventory item to the repository
        return inventoryRepository.save(inventory);
    }

    @Override
    public String updateInventory(Long id, Inventory inventoryRequest) {
        return inventoryRepository.findById(id)
                .map(existingInventory -> {
                    existingInventory.setSkuCode(inventoryRequest.getSkuCode());
                    existingInventory.setQuantity(inventoryRequest.getQuantity());
                    Inventory updatedInventory = inventoryRepository.save(existingInventory);
                    return "Updated inventory with ID: " + updatedInventory.getId();
                })
                .orElseThrow(() -> new RuntimeException("Inventory with ID " + id + " not found"));
    }

    @Override
    public void deleteInventory(Long id) {
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Inventory with ID " + id + " not found");
        }
    }
}
