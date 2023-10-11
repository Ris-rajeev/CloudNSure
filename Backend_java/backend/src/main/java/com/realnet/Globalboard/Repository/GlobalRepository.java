package com.realnet.Globalboard.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.realnet.Globalboard.Entity.Rn_board_rule_t;

@Repository
public interface GlobalRepository extends CrudRepository<Rn_board_rule_t, Integer> {

	Rn_board_rule_t findById(int id);
}
