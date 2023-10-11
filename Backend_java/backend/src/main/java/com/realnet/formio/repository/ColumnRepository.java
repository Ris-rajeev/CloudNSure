package com.realnet.formio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.realnet.formio.entity.Board;
import com.realnet.formio.entity.Column;

public interface ColumnRepository extends JpaRepository<Column, Long> {
	
	public List<Column> getColumnByBoard(Board board);

	@Query(value="SELECT * from columns_table cl where cl.board_b_id=?1",nativeQuery = true)
	public List<Column> findAllById( Long b_id);
	
	@Query(value="SELECT * from columns_table cl where cl.c_id=?1",nativeQuery = true)
	public List<Column> findAllColumnById( Long c_id);
	
//	@Query(value="SELECT * from columns_table cl where cl.c_id =?1 and cl.board_b_id= ?2",nativeQuery = true)
//	public List<Column> findAllById(Long c_id, Long b_id);
}
