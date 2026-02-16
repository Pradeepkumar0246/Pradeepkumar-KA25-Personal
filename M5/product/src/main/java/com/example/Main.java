package com.example;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        ProductRepository repository = new ProductRepository();
        ProductService service = new ProductService();

        List<Product> products = repository.getAllProducts();

        System.out.println("All Products:");
        products.forEach(System.out::println);

        // Filter
        System.out.println("\nElectronics Products:");
        service.filterByCategory(products, "Electronics")
                .forEach(System.out::println);

        // Group
        System.out.println("\nProducts Grouped By Category:");
        Map<String, List<Product>> grouped = service.groupByCategory(products);
        grouped.forEach((category, list) -> {
            System.out.println(category + " -> " + list);
        });

        // Most expensive using Optional
        System.out.println("\nMost Expensive Product:");
        Optional<Product> expensive = service.getMostExpensiveProduct(products);
        expensive.ifPresent(System.out::println);

        // Stream vs Loop
        System.out.println("\nProduct Names Using Stream:");
        System.out.println(service.getProductNamesUsingStream(products));

        System.out.println("\nProduct Names Using Loop:");
        System.out.println(service.getProductNamesUsingLoop(products));

        // Set
        System.out.println("\nUnique Categories:");
        Set<String> categories = service.getUniqueCategories(products);
        System.out.println(categories);
    }
}
