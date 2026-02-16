package com.example.service;

import com.example.model.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductService {

    // Filter by category
    public List<Product> filterByCategory(List<Product> products, String category) {
        return products.stream()
                .filter(p -> p.category().equalsIgnoreCase(category))
                .toList();
    }

    // Group by category (Map)
    public Map<String, List<Product>> groupByCategory(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::category));
    }

    // Find most expensive product (Optional)
    public Optional<Product> getMostExpensiveProduct(List<Product> products) {
        return products.stream()
                .max(Comparator.comparingDouble(Product::price));
    }

    // Stream vs Loop (must match output)
    public List<String> getProductNamesUsingStream(List<Product> products) {
        return products.stream()
                .map(Product::name)
                .toList();
    }

    public List<String> getProductNamesUsingLoop(List<Product> products) {
        List<String> names = new ArrayList<>();
        for (Product p : products) {
            names.add(p.name());
        }
        return names;
    }

    // Set (unique categories)
    public Set<String> getUniqueCategories(List<Product> products) {
        return products.stream()
                .map(Product::category)
                .collect(Collectors.toSet());
    }
}
