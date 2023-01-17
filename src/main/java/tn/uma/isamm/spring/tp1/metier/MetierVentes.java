package tn.uma.isamm.spring.tp1.metier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import tn.uma.isamm.spring.tp1.entities.AppRole;
import tn.uma.isamm.spring.tp1.entities.AppUser;
import tn.uma.isamm.spring.tp1.entities.Categorie;
import tn.uma.isamm.spring.tp1.entities.Produit;

public interface MetierVentes {
	public List<Produit> getProduits();
	public void saveProduit(Produit p);
	public Produit getProduitById(long id);
	public Page<Produit> getProduitsPageable(int page, int size);
	public Page<Produit> getProduitsByCategorie(Categorie categorie, int page, int size);
	public List<Produit> getAll(String critere);
	public List<Categorie> getCategories();
	public void deleteProduit(Long id);
	public AppUser saveAppUser(AppUser appUser);
	public AppRole saveAppRole(AppRole appRole);
	public void addRoleToUser(String login, String nomRole);
	public AppUser getUserByLogin(String login);
	public List<AppUser> getAppUsers();

	public Page<Produit> sortByDesigProduit(int page, int size);

	public Page<Produit> sortByPrixProduitAsc(int page, int size);

	public Page<Produit> sortByPrixProduitDesc(int page, int size);
	
	
	//Page<Produit> getProduitsPageableByDesignation(int page, int size, String mc);
	

	

}
