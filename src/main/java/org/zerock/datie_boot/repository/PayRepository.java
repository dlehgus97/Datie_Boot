package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.datie_boot.entity.Pay;

public interface PayRepository extends JpaRepository
<Pay, Long>{
}
