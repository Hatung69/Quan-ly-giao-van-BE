package com.logistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logistics.models.HangHoa;

@Repository
public interface HangHoaRepository extends JpaRepository<HangHoa, Long>{
}
