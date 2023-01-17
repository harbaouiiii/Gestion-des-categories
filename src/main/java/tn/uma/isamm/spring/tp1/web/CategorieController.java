package tn.uma.isamm.spring.tp1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        @RequestParam(name = "size", defaultValue = "3") int size){
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
    public String rechercherCategorie(String mc, Model model){
        List<Categorie> listeCategories = metierCategories.getCategorieByName(mc);
        model.addAttribute("listeCategories", listeCategories);
        return "categories/categories";
    }

    @GetMapping("/admin/ajouterCategorie")
    public String ajouterCategorie(Model model){
        model.addAttribute("categorie", new Categorie());
        return "categories/ajouterCategorie";
    }

    @PostMapping("/admin/ajouterCategorie")
    public String ajouterCategorie(Categorie categorie){
        metierCategories.saveCategorie(categorie);
        return "redirect:/user/categories";
    }

    @GetMapping("/admin/modifierCategorie")
    public String modifierCategorie(@RequestParam(name = "id") Long id,Model model){
        Categorie categorie = metierCategories.getCategorieById(id);
        model.addAttribute("categorie", categorie);
        return "categories/modifierCategorie";
    }

    @PostMapping(value = "/admin/modifierCategorie")
    public String modifierCategorie(Categorie categorie){
        metierCategories.saveCategorie(categorie);
        return "redirect:/user/categories";
    }

    @RequestMapping("/admin/supprimerCategorie")
    public String supprimerCategorie(@RequestParam Long id,
                                     int activePage,
                                     int nbElements,
                                     @RequestParam(defaultValue = "0") int page,
                                     int size,
                                     RedirectAttributes redirectAttributes){
        Page<Produit> produits = metierVentes.getProduitsByCategorie(metierCategories.getCategorieById(id), page,size);
        produits.forEach( produit -> produit.setCategorie(null));
        metierCategories.deleteCategorie(id);
        if(activePage>0 && ((nbElements-1)==(size * (activePage))))
            activePage--;
        redirectAttributes.addAttribute("page", activePage);
        return "redirect:/user/categories";
    }

    @RequestMapping("user/categorie")
    public String detailsCategorie(@RequestParam(name = "id") Long id, Model model, @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "3") int size){
        Categorie categorie = metierCategories.getCategorieById(id);
        Page<Produit> produits = metierVentes.getProduitsByCategorie(categorie, page,size);
        model.addAttribute("categorie", categorie);
        model.addAttribute("nombreProduits", produits.getContent().size());
        model.addAttribute("id", categorie.getCodeCateg());
        System.out.println(categorie.getProduits().size());
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
