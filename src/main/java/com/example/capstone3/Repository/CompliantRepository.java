package com.example.capstone3.Repository;

import com.example.capstone3.Model.Compliant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompliantRepository extends JpaRepository<Compliant, Integer> {

    Compliant findCompliantById(int id);

//    Compliant findCompliantByCapId(int capId);
}