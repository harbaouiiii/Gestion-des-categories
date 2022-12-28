package tn.uma.isamm.spring.tp1.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.uma.isamm.spring.tp1.dao.AppRoleDAO;
import tn.uma.isamm.spring.tp1.dao.AppUserDAO;
import tn.uma.isamm.spring.tp1.dao.CategorieDAO;
import tn.uma.isamm.spring.tp1.dao.ProduitDAO;
import tn.uma.isamm.spring.tp1.entities.AppRole;
import tn.uma.isamm.spring.tp1.entities.AppUser;
import tn.uma.isamm.spring.tp1.entities.Categorie;
import tn.uma.isamm.spring.tp1.entities.Produit;

@Service
@Transactional
public class MetierVentesImpl implements MetierVentes {
	@Autowired
	ProduitDAO produitDAO;
	
	@Autowired
	CategorieDAO categorieDAO;
	
	@Autowired	
	AppUserDAO appUserDAO;
	
	@Autowired
	AppRoleDAO appRoleDAO;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public List<Produit> getProduits() {
		// TODO Auto-generated method stub
		return produitDAO.findAll();
	}

	@Override
	public void saveProduit(Produit p) {
		// TODO Auto-generated method stub
		produitDAO.save(p);

	}

	@Override
	public Page<Produit> getProduitsPageable(int page, int size) {
		// TODO Auto-generated method stub
		PageRequest pr = PageRequest.of(page, size);
		return produitDAO.findAll(pr);
	}

	@Override
	public List<Produit> getProduitsByCategorie(Categorie categorie) {
		return produitDAO.findByCategorie(categorie);
	}

	@Override
	public List<Produit> getAll(String critere) {
		return produitDAO.findAll(Sort.by(critere));
	}

//	@Override
//	public Page<Produit> getProduitsPageableByDesignation(int page, int size, String mc) {
//		// TODO Auto-generated method stub
//		return produitDAO.findByDesigProduitContaining(page, size, mc);
//	}

	@Override
	public Produit getProduitById(long id) {
		// TODO Auto-generated method stub
		Optional<Produit> p = produitDAO.findById(id);
		return p.orElse(null);
	}

	@Override
	public List<Categorie> getCategories() {
		// TODO Auto-generated method stub
		return categorieDAO.findAll();
	}

	@Override
	public void deleteProduit(Long id) {
		// TODO Auto-generated method stub
		produitDAO.deleteById(id);
	}

	@Override
	public AppUser saveAppUser(AppUser appUser) {
		// TODO Auto-generated method stub
		String mdp = appUser.getPassword();
		appUser.setPassword(passwordEncoder.encode(mdp));
		return appUserDAO.save(appUser);
	}

	@Override
	public AppRole saveAppRole(AppRole appRole) {
		// TODO Auto-generated method stub
		return appRoleDAO.save(appRole);
	}

	@Override
	public void addRoleToUser(String login, String nomRole) {
		// TODO Auto-generated method stub
		AppUser user =appUserDAO.findByUsername(login);
		AppRole role =appRoleDAO.findByRoleName(nomRole);
		if(user!=null && role!=null)
			user.getRoles().add(role);
		
		
	}

	@Override
	public AppUser getUserByLogin(String login) {
		// TODO Auto-generated method stub
		return appUserDAO.findByUsername(login);
	}

	@Override
	public List<AppUser> getAppUsers() {
		// TODO Auto-generated method stub
		return appUserDAO.findAll();
	}
	
	

}
