package com.realnet.formio.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.realnet.formio.entity.Boardmix;
import com.realnet.formio.entity.Card;
import com.realnet.formio.entity.Column;
import com.realnet.formio.entity.GetGoal;
import com.realnet.formio.entity.GetIteration;
import com.realnet.formio.entity.Getmilestone;

public interface CardRepository extends JpaRepository<Card, Long> {

	public List<Card> getCardsByColumn(Column column);

//	@Query(value = "select goal from Card where id=?1 ")
//	Optional<Card> findById(Long id);

	@Query(value = " select * from cards where assigned_to=?1", nativeQuery = true)
	public List<Card> findByAssigned_to(Long assigned_to);

	@Query(value = " select * from cards where requested_by=?1", nativeQuery = true)
	public List<Card> findByrequested_by(Long requested_by);

	@Query(value = "SELECT * FROM board b, columns_table cl, cards c where c.date_created between :startDate and :endDate", nativeQuery = true)
	public List<Boardmix> findByDate_createdBetween(Date startDate, Date endDate);

	@Query(value = "SELECT * FROM board b, columns_table cl, cards c where b.b_id=?1", nativeQuery = true)
	public List<Card> getCards(Long b_id);

	@Query(value = "SELECT * FROM board b, columns_table cl, cards c where b.b_id=?1 and c.assigned_to=?2", nativeQuery = true)
	public List<Card> GetOwnedBy(Long b_id, Long assigned_to);

	@Query(value = "SELECT * FROM board b, columns_table cl, cards c where b.b_id=?1 and c.requested_by=?2", nativeQuery = true)
	public List<Card> GetRequestedBy(Long b_id, Long requested_by);

	@Query(value = "SELECT * FROM board b, columns_table cl, cards c "
			+ "where b.b_id=?1 and cl.c_name IN ('Backlog', 'Current Sprint', 'Under Development','Ready for Deployment')", nativeQuery = true)
	public List<Card> findByC_name(Long b_id);

	@Query(value = "SELECT * FROM board b, columns_table cl, cards c "
			+ "where b.b_id=?1 and c.date=?2 ", nativeQuery = true)
	public List<Card> findByDate(Long b_id, String date);

	@Query(value = "SELECT * FROM board b, columns_table cl, cards c  where c.column_c_id=cl.c_id\r\n"
			+ "and  cl.board_b_id=b.b_id\r\n"
			+ "and  c.date_created between :startDate and :endDate", nativeQuery = true)
	public List<Card> findByate_created(Date startDate, Date endDate);

	@Query(value = "SELECT *  FROM cards  where id =:id ", nativeQuery = true)
	GetGoal findforgoal(@Param(value = "id") Long id);
	
	@Query(value = "SELECT new com.realnet.formio.entity.Card(c.goal)  FROM Card c where c.id =:id ", nativeQuery = true)
	Card findfgoalbycard(@Param(value = "id") Long id);
	
	@Query(value = "SELECT *  FROM cards c where c.id =:id ", nativeQuery = true)
	Getmilestone findMilestone(@Param(value = "id") Long id);
	
	@Query(value = "SELECT *  FROM cards c where c.id =:id ", nativeQuery = true)
	GetIteration finditeration(@Param(value = "id") Long id);
	
	@Query(value = "SELECT *  FROM cards c where c.id =:id ", nativeQuery = true)
	GetGoal findIteration(@Param(value = "id") Long id);

	@Query(value = "SELECT * FROM realnet_CNSBE.cards;", nativeQuery = true)
	public List<Card> findGoal();

	@Query(value = "SELECT * FROM realnet_CNSBE.cards where goal != 'null';", nativeQuery = true)
	public List<Card> findByGoal();

	@Query(value = "SELECT * FROM realnet_CNSBE.cards where iteration != 'null';", nativeQuery = true)
	public List<Card> findBymilestone();

	@Query(value = "SELECT * FROM rLongealnet_CNSBE.cards where milestone != 'null';", nativeQuery = true)
	public List<Card> findByIteration();

	@Query(value = "SELECT * FROM realnet_CNSBE.cards c where c.goal != 'null' and c.column_c_id=?1 ;", nativeQuery = true)
	public List<Card> findgoalbycid(Long column_c_id);

	public List<Card> findByGoal(String goal);
	
	@Query(value = "	SELECT * FROM realnet_CNSBE.cards c where c.goal =?1 and c.column_c_id= ?2", nativeQuery = true)
	public List<Card> findByGoal(String goal, Long column_c_id);

}
