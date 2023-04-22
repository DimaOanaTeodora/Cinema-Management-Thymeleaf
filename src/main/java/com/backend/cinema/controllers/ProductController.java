package com.backend.cinema.controllers;

import com.backend.cinema.domain.Category;
import com.backend.cinema.domain.Product;
import com.backend.cinema.exceptions.ResourceNotFoundException;
import com.backend.cinema.services.CategoryService;
import com.backend.cinema.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.validation.Valid;


@Controller
@RequestMapping("/products")
public class ProductController {
    ProductService productService;

    @Autowired
    CategoryService categoryService;

   
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /*
    @RequestMapping("")
    public String productList(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products",products);
        return "productList";
    }*/


    @RequestMapping("")
    public ModelAndView products(){
        ModelAndView modelAndView = new ModelAndView("productList");
        List<Product> products = productService.findAll();
        modelAndView.addObject("products",products);
        return modelAndView;
    }


    @GetMapping("/{id}")
    public String getById(@PathVariable String id, Model model){

        Product product = productService.findById(Long.valueOf(id));
        model.addAttribute("product",
                product);
        return "productDetails";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable String id){
        productService.deleteById(Long.valueOf(id));
        return "redirect:/products";
    }


    @RequestMapping("/form")
    public String productForm(Model model) {
        model.addAttribute("product", new Product());
        List<Category> categoriesAll = categoryService.findAll();
        model.addAttribute("categoriesAll", categoriesAll );
        return "productform";
    }


    @PostMapping("")
    public String saveOrUpdate(@Valid @ModelAttribute Product product,
                               BindingResult bindingResult,
                               @RequestParam("imagefile") MultipartFile file
    ){
        if (bindingResult.hasErrors()){
            return "productForm";
        }

        Product savedProduct;
        if (file.isEmpty())
            savedProduct = productService.save(product);
       

        return "redirect:/products" ;
    }


    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("product",
                productService.findById(Long.valueOf(id)));

        List<Category> categoriesAll = categoryService.findAll();
        model.addAttribute("categoriesAll", categoriesAll );

        return "productForm";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handlerNotFoundException(Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception",exception);
        modelAndView.setViewName("notFoundException");
        return modelAndView;
    }

}