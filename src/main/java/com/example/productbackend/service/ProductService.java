package com.example.productbackend.service;

import com.example.productbackend.model.Product;
import com.example.productbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getProducts(String search, String category) {
        if (search != null && search.equals("")) {
            search = null;
        }
        if (category != null && category.equals("")) {
            category = null;
        }
        boolean hasSearch = search != null;
        boolean hasCategory = category != null;
        if (!hasSearch && !hasCategory) {
            return repository.findAll();
        }
        if (hasSearch && !hasCategory) {
            List<Product> byTitle =
                    repository.findByTitleContainingIgnoreCase(search);

            List<Product> byBrand =
                    repository.findByBrandContainingIgnoreCase(search);

            return mergeWithoutDuplicates(byTitle, byBrand);
        }


        if (!hasSearch) {
            return repository.findByCategory(category);
        }


        List<Product> byTitle =
                repository.findByTitleContainingIgnoreCaseAndCategory(search, category);

        List<Product> byBrand =
                repository.findByBrandContainingIgnoreCaseAndCategory(search, category);

        return mergeWithoutDuplicates(byTitle, byBrand);
    }


    private List<Product> mergeWithoutDuplicates(
            List<Product> list1, List<Product> list2) {

        Map<Long, Product> map = new HashMap<>();

        for (Product p : list1) {
            map.put(p.getId(), p);
        }

        for (Product p : list2) {
            map.put(p.getId(), p);
        }

        return new ArrayList<>(map.values());
    }
    public Product addProduct(Product product)
    {
        return repository.save(product);
    }

}
