package tn.uma.isamm.spring.tp1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.spring.tp1.entities.Categorie;
import tn.uma.isamm.spring.tp1.entities.Produit;

import java.util.List;

public interface CategorieDAO extends JpaRepository<Categorie, Long>{
    public List<Produit> findByNomCategContaining(String mc);
}
