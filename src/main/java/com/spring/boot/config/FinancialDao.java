package com.spring.boot.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialDao {

	@Autowired
	private IFinancialRepository repository;
	
	public List<FinancialModel> save(List<FinancialModel> models)
	{
		return (List<FinancialModel>) repository.save(models);
	}
	
	public long count()
	{
		return repository.count();
	}
	
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
