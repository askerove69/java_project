package com.example.demo;

import com.example.demo.models.Cargo;
import com.example.demo.repo.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepository repo;

    public List<Cargo> listAll(String keyword) {
        List<Cargo> cargos;

        if (keyword != null) {
            cargos = repo.search(keyword);
        } else {
            cargos = repo.findAll();
        }

        // Сортировка по дате поставки
        Collections.sort(cargos, Comparator.comparing(Cargo::getDd));

        return cargos;
    }


    public void save(Cargo cargo) {
        if (cargo.getFirst() == null || cargo.getFirst().isEmpty()) {
            throw new IllegalArgumentException("Название груза не может быть пустым");
        }
        if (cargo.getContent() == null || cargo.getContent().isEmpty()) {
            throw new IllegalArgumentException("Содержимое груза не может быть пустым");
        }
        if (cargo.getDc() == null || cargo.getDc().isEmpty()) {
            throw new IllegalArgumentException("Город отправки груза не может быть пустым");
        }
        if (cargo.getDd() == null) {
            throw new IllegalArgumentException("Дата отправки груза не может быть пустой");
        }
        if (cargo.getAc() == null || cargo.getAc().isEmpty()) {
            throw new IllegalArgumentException("Город прибытия груза не может быть пустым");
        }
        if (cargo.getAd() == null) {
            throw new IllegalArgumentException("Дата прибытия груза не может быть пустой");
        }
        repo.save(cargo);
    }

    public Cargo get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id){
        repo.deleteById(id);
    }


}
