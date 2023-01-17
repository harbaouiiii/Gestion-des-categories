package tn.uma.isamm.spring.tp1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.spring.tp1.entities.Categorie;

import java.util.List;

public interface CategorieDAO extends JpaRepository<Categorie, Long>{
    public List<Categorie> findByNomCategContaining(String mc);
}
