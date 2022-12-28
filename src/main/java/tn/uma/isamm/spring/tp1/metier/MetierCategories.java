package tn.uma.isamm.spring.tp1.metier;

import org.springframework.data.domain.Page;
import tn.uma.isamm.spring.tp1.entities.Categorie;

import java.util.List;

public interface MetierCategories {

    public List<Categorie> getCategories();
    public Categorie getCategorieById(Long id);
    public Page<Categorie> getCategoriesPageable(int page, int size);
    public void saveCategorie(Categorie c);
    public void deleteCategorie(Long id);

}
