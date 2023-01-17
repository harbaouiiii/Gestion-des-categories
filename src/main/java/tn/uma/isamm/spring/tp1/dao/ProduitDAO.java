package tn.uma.isamm.spring.tp1.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import tn.uma.isamm.spring.tp1.entities.Categorie;
import tn.uma.isamm.spring.tp1.entities.Produit;

public interface ProduitDAO extends JpaRepository<Produit, Long> {
	public List<Produit> findByDesigProduitContaining(String mc);
	public Page<Produit> findByCategorie(Categorie categorie, Pageable pageable);

	public Page<Produit> findAllByOrderByDesigProduit(Pageable pageable);

	public Page<Produit> findAllByOrderByPrixProduitAsc(Pageable pageable);

	public Page<Produit> findAllByOrderByPrixProduitDesc(Pageable pageable);
}
