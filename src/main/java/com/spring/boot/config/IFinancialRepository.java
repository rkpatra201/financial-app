package com.spring.boot.config;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IFinancialRepository extends CrudRepository<FinancialModel, Long> {
	
}