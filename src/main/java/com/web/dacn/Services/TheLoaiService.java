package com.web.dacn.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.dacn.Models.TheLoai;
import com.web.dacn.Repositories.TheLoaiRepository;

@Service
@Transactional
public class TheLoaiService {
     @Autowired
     private TheLoaiRepository TheLoaiRepository;

     public List<TheLoai> listAll() {
          return TheLoaiRepository.findAll();
     }

     public TheLoai save(TheLoai theLoai) {
          return TheLoaiRepository.save(theLoai);
     }

     public TheLoai get(long id) {
          return TheLoaiRepository.findById(id).orElse(null);
     }

     public void delete(long id) {
          TheLoaiRepository.deleteById(id);
     }
}
