package edu.edgetech.sb2.repositories;

import edu.edgetech.sb2.domain.Product;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProdRepo extends Repository<Product, Integer> {
    List<Product> findProductByType(String type);
}