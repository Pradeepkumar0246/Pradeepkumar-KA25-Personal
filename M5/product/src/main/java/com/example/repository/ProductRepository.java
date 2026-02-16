package com.example.repository;

import com.example.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(1, "Laptop", "Electronics", 65000));
        products.add(new Product(2, "Mouse", "Electronics", 1200));
        products.add(new Product(3, "Desk", "Furniture", 8500));
        products.add(new Product(4, "Chair", "Furniture", 4500));
        products.add(new Product(5, "Pen", "Stationery", 50));
        products.add(new Product(6, "Notebook", "Stationery", 120));

        return products;
    }
}
