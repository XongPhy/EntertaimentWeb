package com.web.dacn.Controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.web.dacn.Models.BangTin;
import com.web.dacn.Services.BangTinService;
import com.web.dacn.Services.TheLoaiService;
import com.web.dacn.Utils.FileUploadUtil;
@Controller
@RequestMapping("/mobiles")
public class MobileController {
	 @Autowired
     private BangTinService bangTinService;
     @Autowired
     private TheLoaiService theLoaiService;

     @GetMapping
     public String viewHomePage(Model model) {
          return viewAllBook(model, 1, "id", "asc", "");
     }
     
     

     @GetMapping("/page/{pageNum}")
     public String viewAllBook(Model model, @PathVariable(name = "pageNum") int pageNum,
               @Param("sortField") String sortField, @Param("sortType") String sortType,
               @Param("keyword") String keyword) {
         
          sortField = sortField == null ? "id" : sortField;
          sortType = sortType == null ? "asc" : sortType;
          Page<BangTin> page = bangTinService.listAllWithCategory(pageNum, sortField, sortType, "Game Mobile");
          List<BangTin> listBangTin = page.getContent();
          model.addAttribute("currentPage", pageNum);
          model.addAttribute("totalPages", page.getTotalPages());
          model.addAttribute("totalItems", page.getTotalElements());
          model.addAttribute("sortField", sortField);
          model.addAttribute("sortType", sortType);
          model.addAttribute("reverseSortType", sortType.equals("asc") ? "desc" : "asc");
          model.addAttribute("keyword", keyword);
          model.addAttribute("bangtins", listBangTin);
          return "bangtin/mobile";
     }

     @GetMapping("/new")
     public String showNewBookPage(Model model) {
          BangTin bangTin = new BangTin();
          model.addAttribute("bangtin", bangTin);
          model.addAttribute("theloais", theLoaiService.listAll());
          return "bangtin/new_bangtin";
     }

     @PostMapping("/save")
     public String saveProduct(@ModelAttribute("bangtin") BangTin bangTin,
               @RequestParam("image") MultipartFile imageFile) throws IOException {
          String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
          bangTin.setPhotourl(fileName);
          BangTin saveProduct = bangTinService.save(bangTin);
          if (!imageFile.getOriginalFilename().isBlank()) {
               String uploadDir = "photos/" + saveProduct.getId();
               FileUploadUtil.saveFile(uploadDir, fileName, imageFile);
          }

          return "redirect:/mobiles";
     }

     @GetMapping("/edit/{id}")
     public String showEditBookPage(@PathVariable("id") Long id, Model model) {
          BangTin bangTin = bangTinService.get(id);

          if (bangTin == null) {
               return "notfound";

          } else {
               model.addAttribute("theloais", theLoaiService.listAll());
               model.addAttribute("bangtin", bangTin);
               return "bangtin/edit";
          }
     }

     @GetMapping("/delete/{id}")
     public String deletebook(@PathVariable("id") Long id) {
          BangTin bangTin = bangTinService.get(id);
          if (bangTin == null) {
               return "notfound";
          } else {
               bangTinService.delete(id);
               return "redirect:/mobiles";
          }
     }

     @GetMapping("/export/{pageNum}")
     public void exportToCSV(HttpServletResponse response, @PathVariable(name = "pageNum") int pageNum)
               throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
          response.setContentType("text/csv");
          DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
          String currentDateTime = dateFormatter.format(new Date());

          String headerKey = "Content-Disposition";
          String headerValue = "attachment; filename=books_" + currentDateTime + ".csv";
          response.setHeader(headerKey, headerValue);

          List<BangTin> bangTins = bangTinService.listAll(pageNum).getContent();

          StatefulBeanToCsvBuilder<BangTin> builder = new StatefulBeanToCsvBuilder<BangTin>(response.getWriter())
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withOrderedResults(false);

          Arrays.stream(BangTin.class.getDeclaredFields())
                    .filter(field -> !("id".equals(field.getName()) || "tieude".equals(field.getName())
                              || "tacgia".equals(field.getName()) || "noidungbia".equals(field.getName())))
                    .forEach(field -> builder.withIgnoreField(BangTin.class, field));

          StatefulBeanToCsv<BangTin> writer = builder.build();

          // write all users to csv file
          writer.write(bangTins);
     }

     @GetMapping("/detail/{id}")
     public String viewBookDetail(@PathVariable("id") Long id, Model model) {
          BangTin bangTin = bangTinService.get(id);

          if (bangTin == null) {
               return "notfound";
          } else {
               model.addAttribute("bangtin", bangTin);
               return "bangtin/detail";
          }
     }

   
}
