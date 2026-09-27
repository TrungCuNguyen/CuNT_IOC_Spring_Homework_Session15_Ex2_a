package api.ex_ss15_ioc.repository;

import api.ex_ss15_ioc.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> { //[cite: 2]
}
