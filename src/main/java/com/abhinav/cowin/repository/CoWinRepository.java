package com.abhinav.cowin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abhinav.cowin.pojo.Statistics;
import com.abhinav.cowin.pojo.StatisticsKey;

@Repository
public interface CoWinRepository extends CrudRepository<Statistics, StatisticsKey>{

}
