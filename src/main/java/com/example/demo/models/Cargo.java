package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Entity
public class Cargo {
    private Long id;
    private String first;
    private String content;
    private String dc;
    private Date dd;
    private String ac;
    private Date ad;

    public Cargo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getDc() {
        return dc;
    }
    public void setDc(String dc) { this.dc = dc; }

    public Date getDd() {return dd;}
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setDd(Date dd) {
        this.dd = dd;
    }

    public String getAc() {
        return ac;
    }
    public void setAc(String ac) { this.ac = ac; }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getAd() {return ad;}
    public void setAd(Date ad) { this.ad = ad; }


    @Override
    public String toString() {
        return "cargo [id=" + id + ", first=" + first +", content=" + content + ", dc=" + dc + ", dd=" + dd + ", ac=" + ac + ", ad=" + ad + "]";
    }
}
