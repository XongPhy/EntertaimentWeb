package com.web.dacn.Models;

import java.beans.Transient;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "bangtin")
public class BangTin {
     @Id
     @Column(name = "bangtin_id")
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(name = "tieude", nullable = false, length = 255)
     private String tieude;

     @Column(name = "tacgia", nullable = false, length = 255)
     private String tacgia;

     @Column(name = "noidungbia", nullable = true, length = 255)
     private String noidungbia;

     @Lob
     @Column(name = "noidungchinh", nullable = true, columnDefinition = "TEXT")
     private String noidungchinh;

     @Column(name = "isdeleted", columnDefinition = "boolean default false")
     private boolean isdeleted;

     @ManyToOne
     @JoinColumn(name = "theloai_id", nullable = false)
     private TheLoai theloai;

     @Column(nullable = true, length = 255)
     private String photourl;

     @Column(name = "created_at", nullable = false)
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date createdat;

     private String formattedCreatedAt;

     @Transient
     public String getPhotosImagePath() {
          if (photourl == null || id == null)
               return null;

          return "/photos/" + id + "/" + photourl;
     }

     public Long getId() {
          return id;
     }

     public Date getCreatedat() {
          return createdat;
     }

     public void setCreatedat(Date createdat) {
          this.createdat = createdat;
          SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
          this.formattedCreatedAt = dateFormat.format(createdat);
     }

     public String getFormattedCreatedAt() {
          return formattedCreatedAt;
     }

     public void setFormattedCreatedAt(String formattedCreatedAt) {
          this.formattedCreatedAt = formattedCreatedAt;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public String getNoidungchinh() {
          return noidungchinh;
     }

     public void setNoidungchinh(String noidungchinh) {
          this.noidungchinh = noidungchinh;
     }

     public String getPhotourl() {
          return photourl;
     }

     public void setPhotourl(String photourl) {
          this.photourl = photourl;
     }

     public String getTieude() {
          return tieude;
     }

     public void setTieude(String tieude) {
          this.tieude = tieude;
     }

     public String getTacgia() {
          return tacgia;
     }

     public void setTacgia(String tacgia) {
          this.tacgia = tacgia;
     }

     public String getNoidungbia() {
          return noidungbia;
     }

     public void setNoidungbia(String noidungbia) {
          this.noidungbia = noidungbia;
     }

     public boolean isIsdeleted() {
          return isdeleted;
     }

     public void setIsdeleted(boolean isdeleted) {
          this.isdeleted = isdeleted;
     }

     public TheLoai getTheloai() {
          return theloai;
     }

     public void setTheloai(TheLoai theloai) {
          this.theloai = theloai;
     }
}
