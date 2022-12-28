package tn.uma.isamm.spring.tp1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tn.uma.isamm.spring.tp1.entities.Categorie;
import tn.uma.isamm.spring.tp1.entities.Produit;
import tn.uma.isamm.spring.tp1.entities.ProduitAlimentaire;
import tn.uma.isamm.spring.tp1.entities.ProduitCosmetique;
import tn.uma.isamm.spring.tp1.metier.MetierCategories;
import tn.uma.isamm.spring.tp1.metier.MetierVentes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class CategorieController {

    @Autowired
    private MetierCategories metierCategories;

    @Autowired
    private MetierVentes metierVentes;

    @RequestMapping("/user/categories")
    public String categories(Model model,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "3") int size,
        @RequestParam(name = "errorMessage", defaultValue = "") String errorMessage){
        Page<Categorie> listeCategories = metierCategories.getCategoriesPageable(page, size);
        model.addAttribute("activePage", page);
        model.addAttribute("size", size);
        int[] taillePagination = IntStream.range(0, listeCategories.getTotalPages()).toArray();
        model.addAttribute("taillePagination", taillePagination);
        model.addAttribute("nbPages", listeCategories.getTotalPages());
        model.addAttribute("nbElements", listeCategories.getTotalElements());
        model.addAttribute("listeCategories",  listeCategories);
        return "categories/categories";
    }

    @RequestMapping(value = "/user/rechercheCategorie", method = RequestMethod.POST)
    public String rechercherCategorie(long id, Model model){
        List<Categorie> listeCategories = new ArrayList<>();
        Categorie categorie = metierCategories.getCategorieById(id);
        listeCategories.add(categorie);
        model.addAttribute("listeCategories", listeCategories);
        return "categories/categories";
    }

    @RequestMapping("/admin/ajouterCategorie")
    public String ajouterCategorie(Model model){
        model.addAttribute("categorie", new Categorie());
        return "categories/ajouterCategorie";
    }

    @RequestMapping(value = "/admin/ajouterCategorie", method = RequestMethod.POST)
    public String ajouterCategorie(Categorie categorie){
        metierCategories.saveCategorie(categorie);
        return "redirect:/user/categories";
    }

    @RequestMapping("/admin/modifierCategorie")
    public String modifierCategorie(@RequestParam(name = "id") Long id,Model model){
        Categorie categorie = metierCategories.getCategorieById(id);
        model.addAttribute("categorie", categorie);
        return "categories/modifierCategorie";
    }

    @RequestMapping(value = "/admin/modifierCategorie", method = RequestMethod.POST)
    public String modifierCategorie(Categorie categorie){
        metierCategories.saveCategorie(categorie);
        return "redirect:/user/categories";
    }

    @RequestMapping("/admin/supprimerCategorie")
    public String supprimerCategorie(@RequestParam Long id,
                                     Long activePage,
                                     Long nbElements,
                                     Long size,
                                     RedirectAttributes redirectAttributes){
        List<Produit> produits = metierVentes.getProduitsByCategorie(metierCategories.getCategorieById(id));
        produits.forEach( produit -> produit.setCategorie(null));
        metierCategories.deleteCategorie(id);
        if(activePage>0 && ((nbElements-1)==(size * (activePage))))
            activePage--;
        redirectAttributes.addAttribute("page", activePage);
        return "redirect:/user/categories";
    }

    @RequestMapping("user/categorie")
    public String detailsCategorie(@RequestParam(name = "id") Long id, Model model){
        Categorie categorie = metierCategories.getCategorieById(id);
        List<Produit> produits = metierVentes.getProduitsByCategorie(categorie);
        model.addAttribute("categorie", categorie);
        model.addAttribute("nombreProduits", produits.size());
        model.addAttribute("id", categorie.getCodeCateg().toString());
        return "categories/categorie";
    }

    @RequestMapping("admin/categorie/ajouterProduitAlimentaire")
    public String ajouterProduitAlimentaire(@RequestParam(name = "id") Long id, Model model){
        model.addAttribute("produit", new ProduitAlimentaire());
        model.addAttribute("categorie", metierCategories.getCategorieById(id));
        return "categories/ajouterProduitAlimentaire";
    }

    @RequestMapping(value = "admin/categorie/ajouterProduitAlimentaire", method = RequestMethod.POST)
    public String ajouterProduitAlimentaire(@RequestParam(name="id") Long id,
                                            ProduitAlimentaire produitAlimentaire,
                                            RedirectAttributes redirectAttributes){
        produitAlimentaire.setCategorie(metierCategories.getCategorieById(id));
        metierVentes.saveProduit(produitAlimentaire);
        redirectAttributes.addAttribute("id",id);
        return "redirect:/user/categorie";
    }

    @RequestMapping("admin/categorie/ajouterProduitCosmetique")
    public String ajouterProduitCosmetique(@RequestParam(name = "id") Long id, Model model){
        model.addAttribute("produit", new ProduitCosmetique());
        model.addAttribute("categorie", metierCategories.getCategorieById(id));
        return "categories/ajouterProduitCosmetique";
    }

    @RequestMapping(value = "admin/categorie/ajouterProduitCosmetique", method = RequestMethod.POST)
    public String ajouterProduitCosmetique(@RequestParam(name="id") Long id,
                                           ProduitCosmetique produitCosmetique,
                                           RedirectAttributes redirectAttributes){
        produitCosmetique.setCategorie(metierCategories.getCategorieById(id));
        metierVentes.saveProduit(produitCosmetique);
        redirectAttributes.addAttribute("id",id);
        return "redirect:/user/categorie";
    }

}
