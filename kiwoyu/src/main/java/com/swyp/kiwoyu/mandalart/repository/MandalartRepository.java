package com.swyp.kiwoyu.mandalart.repository;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MandalartRepository extends JpaRepository<Mandalart, Long>, MandalartRepositoryCustom{
}