package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryService {

    boolean isInStock(String skuCode, Integer quantity);

    List<Inventory> getAllInventory();

    Inventory getInventoryById(Long id);

    Inventory createInventory(Inventory inventory); // New method for creating inventory

    String updateInventory(Long id, Inventory inventory);

    void deleteInventory(Long id);
}
