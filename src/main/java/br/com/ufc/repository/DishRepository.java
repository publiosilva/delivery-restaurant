package br.com.ufc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufc.model.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

}
