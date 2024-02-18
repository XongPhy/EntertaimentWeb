package com.web.dacn.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.dacn.Models.TheLoai;
import com.web.dacn.Services.TheLoaiService;

@Controller
@RequestMapping("/theloais")
public class TheLoaiController {
     @Autowired
     private TheLoaiService theLoaiService;

     @GetMapping
     public String viewAllCategories(Model model) {
          List<TheLoai> listTheLoais = theLoaiService.listAll();
          model.addAttribute("theloais", listTheLoais);
          return "theloai/index";
     }

     @GetMapping("/new")
     public String showNewCategoryPage(Model model) {
          TheLoai theLoai = new TheLoai();
          model.addAttribute("theloai", theLoai);
          return "theloai/new_theloai";
     }

     @PostMapping("/save")
     public String saveTheLoai(@ModelAttribute("theloai") TheLoai theLoai) {
          theLoaiService.save(theLoai);
          return "redirect:/theloais";
     }

     @GetMapping("/edit/{id}")
     public String showEditTheLoaiPage(@PathVariable("id") Long id, Model model) {
          TheLoai theLoai = theLoaiService.get(id);

          if (theLoai == null) {
               return "notfound";

          } else {
               model.addAttribute("theloai", theLoai);
               return "theLoai/edit";
          }
     }

     @GetMapping("/delete/{id}")
     public String deleteTheLoai(@PathVariable("id") Long id) {
          TheLoai theLoai = theLoaiService.get(id);
          if (theLoai == null) {
               return "notfound";
          } else {
               theLoaiService.delete(id);
               return "redirect:/bangtins";
          }
     }
}
