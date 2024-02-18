package com.web.dacn.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.web.dacn.Models.BangTin;
import com.web.dacn.Repositories.BangTinRepository;

@Service
@Transactional
public class BangTinService {
     int pageSize = 4;
     @Autowired
     private BangTinRepository bangTinRepository;

     public Page<BangTin> listAll(int pageNum) {
          Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
          return bangTinRepository.findAll(pageable);
     }

     public Page<BangTin> listAllWithOutDelete(int pageNum, String sortField, String sortType, String keyword) {
          Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                    sortType.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
          System.out.println(keyword);
          if (keyword != null) {
               return bangTinRepository.search(pageable, keyword);
          }
          return bangTinRepository.findWithOutDelete(pageable);

     }

     public Page<BangTin> listAllWithCategory(int pageNum, String sortField, String sortType, String category) {
         Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                   sortType.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
         
         if (category != null) {
              return bangTinRepository.findByTheloaiKieutheloai(pageable, category);
         }

         // If no category is provided, you can return all articles.
         return bangTinRepository.findAll(pageable);
    }
     
     public BangTin save(BangTin bangTin) {
          bangTinRepository.save(bangTin);
          return bangTin;
     }

     public BangTin get(long id) {
          return bangTinRepository.findById(id).orElse(null);
     }

     public void delete(long id) {
          bangTinRepository.softDeleteBangTinById(id);
     }
}
