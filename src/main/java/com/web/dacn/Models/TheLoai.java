package com.web.dacn.Models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "theloai")
public class TheLoai {

     @Id
     @Column(name = "theloai_id")
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(name = "kieutheloai", nullable = false, length = 255)
     private String kieutheloai;

     @Column(name = "bangtin", nullable = true, length = 255)
     private String bangtin;

     @JsonIgnore
     @OneToMany(mappedBy = "theloai")
     private List<BangTin> bangTins;

     @Column(name = "isdeleted", columnDefinition = "boolean default false")
     private boolean isdeleted;

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public String getKieutheloai() {
          return kieutheloai;
     }

     public void setKieutheloai(String kieutheloai) {
          this.kieutheloai = kieutheloai;
     }

     public String getBangtin() {
          return bangtin;
     }

     public void setBangtin(String bangtin) {
          this.bangtin = bangtin;
     }

     public List<BangTin> getBangTins() {
          return bangTins;
     }

     public void setBangTins(List<BangTin> bangTins) {
          this.bangTins = bangTins;
     }

     public boolean isIsdeleted() {
          return isdeleted;
     }

     public void setIsdeleted(boolean isdeleted) {
          this.isdeleted = isdeleted;
     }

     public TheLoai() {
          super();
          // TODO Auto-generated constructor stub
     }

     public TheLoai(Long id, String kieutheloai, String bangtin, List<BangTin> bangTins, boolean isdeleted) {
          super();
          this.id = id;
          this.kieutheloai = kieutheloai;
          this.bangtin = bangtin;
          this.bangTins = bangTins;
          this.isdeleted = isdeleted;
     }
}
