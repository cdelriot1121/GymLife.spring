package com.gymcj.gimnasio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gymcj.gimnasio.entity.ClasesModel;

@Repository
public interface ClasesRepository extends JpaRepository<ClasesModel, Long>{
    

}
