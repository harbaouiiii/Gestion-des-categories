package tn.uma.isamm.spring.tp1.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tn.uma.isamm.spring.tp1.dao.CategorieDAO;
import tn.uma.isamm.spring.tp1.entities.Categorie;

import java.util.List;
import java.util.Optional;

@Service
public class MetierCategoriesImpl implements MetierCategories {

    @Autowired
    private CategorieDAO categorieDAO;

    @Override
    public List<Categorie> getCategories() {
        return categorieDAO.findAll();
    }

    @Override
    public Categorie getCategorieById(Long id) {
        Optional<Categorie> c = categorieDAO.findById(id);
        return c.orElse(null);
    }

    @Override
    public Page<Categorie> getCategoriesPageable(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return categorieDAO.findAll(pr);
    }

    @Override
    public void saveCategorie(Categorie c) {
        categorieDAO.save(c);
    }

    @Override
    public void deleteCategorie(Long id) {
        categorieDAO.deleteById(id);
    }
}
