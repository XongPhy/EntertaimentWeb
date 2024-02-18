package com.web.dacn.Repositories;

import com.web.dacn.Models.BangTin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface BangTinRepository extends JpaRepository<BangTin, Long> {
     @Query("SELECT b FROM BangTin b WHERE b.isdeleted = false")
     Page<BangTin> findWithOutDelete(Pageable page);

     @Modifying
     @Query("UPDATE BangTin b SET b.isdeleted = true WHERE b.id = :id")
     void softDeleteBangTinById(@Param("id") long id);

     @Query("SELECT b FROM BangTin b WHERE CONCAT(b.tieude, ' ', b.tacgia, ' ', b.noidungbia, ' ', b.theloai, ' ', b.formattedCreatedAt) LIKE %:keyword% AND b.isdeleted = false")
     Page<BangTin> search(Pageable page, @Param("keyword") String keyword);
     
     Page<BangTin> findByTheloaiKieutheloai(Pageable pageable, String category);
     
}
