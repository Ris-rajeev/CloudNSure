package com.realnet.formio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.realnet.formio.entity.Board;
import com.realnet.formio.entity.Boardmicro;
import com.realnet.formio.entity.Boardmix;
import com.realnet.formio.entity.Column;
import com.realnet.formio.entity.NewBoard;

public interface BoardRepository extends JpaRepository<Board, Long> {

//	public List<Column> getColumnsOfBoard(Column column);

	@Query(value =
//			"select b.b_id b_id, b.b_name b_name,b.type type,c.id id,c.assigned_to assigned_to,c.attachment_dod  attachment_dod\r\n"
//			+ "from cards c,columns_table cl,board b where c.column_c_id=cl.c_id\r\n"
//			+ "and  cl.board_b_id=b.b_id\r\n"
//			+ "and b.b_id=?1 and c.assigned_to=?2",

	"select b.b_id b_id,  \n" + "b.b_name b_name,\n" + "b.type type,\n" + "cl.c_name c_name,\n" + "c.id id,\n"
			+ "c.assigned_to assigned_to,\n" + "c.attachment_dod  attachment_dod,\r\n"
			+ "c.attachment_taa attachment_taa,\r\n" + "c.builds_without_error builds_without_error,\r\n"
			+ "c.clock_status clock_status,\r\n" + "			c.closed_for_clocking closed_for_clocking,\r\n"
			+ "			c.code_quality_review_done  code_quality_review_done,\r\n"
			+ "			c.completion_date_time completion_date_time ,\r\n"
			+ "		c.configuration_changes configuration_changes,\r\n"
			+ "			c.current_status current_status, \r\n"
			+ "			c.data_expected_result data_expected_result,\r\n" + "			c.date date,\r\n"
			+ "			c.description description,\r\n" + "			c.dor_approval_status dor_approval_status,\r\n"
			+ "			c.elapsed_time elapsed_time,\r\n" + "			c.estimated_time estimated_time,\r\n"
			+ "			c.estimates estimates,\r\n"
			+ "			c.functional_area_passfail_details functional_area_passfail_details,\r\n"
			+ "			c.git_commited git_commited,\r\n" + "			c.image image,\r\n"
			+ "			c.issue_type issue_type,\r\n" + "			c.peer_revired peer_revired,\r\n"
			+ "			c.pinned_status pinned_status,\r\n" + "			c.present_column present_column,\r\n"
			+ "			c.priority_index priority_index,\r\n" + "			c.project project,\r\n"
			+ "			c.project_priority project_priority,\r\n"
			+ "			c.promoted_test_instance promoted_test_instance,\r\n"
			+ "			c.related_issue related_issue,\r\n" + "			c.repository repository,\r\n"
			+ "			c.scalibility scalibility,\r\n" + "			c.scenario_given_when scenario_given_when,\r\n"
			+ "			c.scheduled_date scheduled_date,\r\n" + "			c.sprint_test sprint_test,\r\n"
			+ "			c.start_date_time start_date_time,\r\n" + "			c.story_points story_points,\r\n"
			+ "			c.story_details story_details,\r\n" + "			c.tags tags,\r\n"
			+ "			c.test_case_demo_case test_case_demo_case,\r\n" + "			c.title title,\r\n"
			+ "			c.unit_test_details unit_test_details,\r\n"
			+ "			c.unit_test_details2 unit_test_details2,\r\n" + "			c.value_points value_points,\r\n"
			+ "			c.requested_by requested_by\r\n"
			+ "from cards c,columns_table cl,board b where c.column_c_id=cl.c_id\n" + "and  cl.board_b_id=b.b_id\n"
			+ "and b.b_id=?1 and c.assigned_to=?2",

			nativeQuery = true)

	public List<Boardmix> findAssigned(Long b_id, Long assigned_to);

	@Query(value = "select b.b_id b_id,  \n" + "b.b_name b_name,\n" + "b.type type,\n" + "cl.c_name c_name,\n"

			+ "c.id id,\n" + "c.assigned_to assigned_to,\n" + "c.attachment_dod  attachment_dod,\r\n"
			+ "c.attachment_taa attachment_taa,\r\n" + "c.builds_without_error builds_without_error,\r\n"
			+ "c.clock_status clock_status,\r\n" + "			c.closed_for_clocking closed_for_clocking,\r\n"
			+ "			c.code_quality_review_done  code_quality_review_done,\r\n"
			+ "			c.completion_date_time completion_date_time ,\r\n"
			+ "		c.configuration_changes configuration_changes,\r\n"
			+ "			c.current_status current_status, \r\n"
			+ "			c.data_expected_result data_expected_result,\r\n" + "			c.date date,\r\n"
			+ "			c.description description,\r\n" + "			c.dor_approval_status dor_approval_status,\r\n"
			+ "			c.elapsed_time elapsed_time,\r\n" + "			c.estimated_time estimated_time,\r\n"
			+ "			c.estimates estimates,\r\n"
			+ "			c.functional_area_passfail_details functional_area_passfail_details,\r\n"
			+ "			c.git_commited git_commited,\r\n" + "			c.image image,\r\n"
			+ "			c.issue_type issue_type,\r\n" + "			c.peer_revired peer_revired,\r\n"
			+ "			c.pinned_status pinned_status,\r\n" + "			c.present_column present_column,\r\n"
			+ "			c.priority_index priority_index,\r\n" + "			c.project project,\r\n"
			+ "			c.project_priority project_priority,\r\n"
			+ "			c.promoted_test_instance promoted_test_instance,\r\n"
			+ "			c.related_issue related_issue,\r\n" + "			c.repository repository,\r\n"
			+ "			c.scalibility scalibility,\r\n" + "			c.scenario_given_when scenario_given_when,\r\n"
			+ "			c.scheduled_date scheduled_date,\r\n" + "			c.sprint_test sprint_test,\r\n"
			+ "			c.start_date_time start_date_time,\r\n" + "			c.story_points story_points,\r\n"
			+ "			c.story_details story_details,\r\n" + "			c.tags tags,\r\n"
			+ "			c.test_case_demo_case test_case_demo_case,\r\n" + "			c.title title,\r\n"
			+ "			c.unit_test_details unit_test_details,\r\n"
			+ "			c.unit_test_details2 unit_test_details2,\r\n" + "			c.value_points value_points,\r\n"
			+ "			c.requested_by requested_by\r\n"
			+ "from cards c,columns_table cl,board b where c.column_c_id=cl.c_id\n" + "and  cl.board_b_id=b.b_id\n"
			+ "and b.b_id=?1 and c.requested_by=?2",

//		+ "select * from board b,cards c where b.b_id=?1 and c.requested_by=?2", 

			nativeQuery = true)

	public List<Boardmix> findrequested_by(Long b_id, Long requested_by);

	@Query(value = "select * from board b,cards c where b.b_id=?1 and c.assigned_to=?2", nativeQuery = true)

	public Optional<Object[]> findAssigned1(Long b_id, Long assigned_to);

	@Query(value = "select * from cards c,columns_table cl,board b\r\n" + "where c.column_c_id=cl.c_id\r\n"
			+ "and  cl.board_b_id=b.b_id\r\n" + "and b.b_id=?1 and\r\n"
			+ "cl.c_name IN ('Backlog', 'Current Sprint', 'Under Development','Ready for Deployment')", nativeQuery = true)
	public List<Boardmix> findByC_name(Long b_id);

	@Query(value = "select * from " + "cards c," + "board b,columns_table cl"
			+ " where b.b_id=?1 and c.date=?2", nativeQuery = true)
	public List<Boardmix> findByDate(Long b_id, String date);

	@Query(value = "select * from " + "cards c," + "board b,columns_table cl" + " where b.b_id=?1", nativeQuery = true)
	public List<Boardmicro> findcurrentweek(Long b_id);

	@Query(value = "select b.b_id b_id, b.b_name b_name,b.type type,c.id id,c.assigned_to assigned_to,"
			+ "c.attachment_dod  attachment_dod\r\n"
			+ "from cards c,columns_table cl,board b where c.column_c_id=cl.c_id\r\n" + "and  cl.board_b_id=b.b_id\r\n"
			+ "and b.b_id=?1 and c.assigned_to=?2",
//			+ "select * from cards c,columns_table cl,board b where c.column_c_id=cl.c_id"
//			+ "			+ \"and  cl.board_b_id=b.b_id \r\n"
//			+ "			+ \"and b.b_id=?1 and c.assigned_to=?2\r\n",
//	@Query(value=
//				
//			"select b.b_id b_id,  \n"
////			+ "b.b_name b_name,\n"
////			+ "b.type type,\n"
//			+ "cl.c_name c_name,\n"
//			+ "c.id id,\n"
//			+ "c.assigned_to assigned_to,\n"
//			+ "c.attachment_dod  attachment_dod,\r\n"
//			+ "c.attachment_taa attachment_taa,\r\n"
//			+ "c.builds_without_error builds_without_error,\r\n"
//			+ "c.clock_status clock_status,\r\n"
//			+ "			c.closed_for_clocking closed_for_clocking,\r\n"
//			+ "			c.code_quality_review_done  code_quality_review_done,\r\n"
//			+ "			c.completion_date_time completion_date_time ,\r\n"
//			+ "		c.configuration_changes configuration_changes,\r\n"
//			+ "			c.current_status current_status, \r\n"
////			+ "			c.data_expected_result data_expected_result,\r\n"
//			+ "			c.date date,\r\n"
//			+ "			c.description description,\r\n"
//			+ "			c.dor_approval_status dor_approval_status,\r\n"
//			+ "			c.elapsed_time elapsed_time,\r\n"
//			+ "			c.estimated_time estimated_time,\r\n"
//			+ "			c.estimates estimates,\r\n"
//			+ "			c.functional_area_passfail_details functional_area_passfail_details,\r\n"
//			+ "			c.git_commited git_commited,\r\n"
//			+ "			c.image image,\r\n"
//			+ "			c.issue_type issue_type,\r\n"
//			+ "			c.peer_revired peer_revired,\r\n"
//			+ "			c.pinned_status pinned_status,\r\n"
//			+ "			c.present_column present_column,\r\n"
//			+ "			c.priority_index priority_index,\r\n"
//			+ "			c.project project,\r\n"
//			+ "			c.project_priority project_priority,\r\n"
//			+ "			c.promoted_test_instance promoted_test_instance,\r\n"
//			+ "			c.related_issue related_issue,\r\n"
//			+ "			c.repository repository,\r\n"
//			+ "			c.scalibility scalibility,\r\n"
//			+ "			c.scenario_given_when scenario_given_when,\r\n"
//			+ "			c.scheduled_date scheduled_date,\r\n"
//			+ "			c.sprint_test sprint_test,\r\n"
//			+ "			c.start_date_time start_date_time,\r\n"
//			+ "			c.story_points story_points,\r\n"
//			+ "			c.story_details story_details,\r\n"
//			+ "			c.tags tags,\r\n"
//			+ "			c.test_case_demo_case test_case_demo_case,\r\n"
//			+ "			c.title title,\r\n"
//			+ "			c.unit_test_details unit_test_details,\r\n"
//			+ "			c.unit_test_details2 unit_test_details2,\r\n"
//			+ "			c.value_points value_points,\r\n"
//			+ "			c.requested_by requested_by\r\n"
//			+ "from cards c,columns_table cl,board b where c.column_c_id=cl.c_id\n"
//			+ "and  cl.board_b_id=b.b_id\n"
//			+ "and b.b_id=?1 and c.assigned_to=?2",

			nativeQuery = true)
	public List<Object> findownedby(Long b_id, Long assigned_to);

	@Query(value="SELECT * from board b, columns_table cl, cards c where c.goal != 'null'",nativeQuery = true)
	List<Boardmix> findByGoal();

//	@Query("SELECT new com.realnet.formio.entity.NewBoard(b.bId, b.bName,b.type, b.project_id,c.cId,c.cName) "
//			+ "FROM Board b, Column c")
//	List<NewBoard> findByGoal();

//	@Query(value = "SELECT  new com.realnet.formio.entity.Boardmicro(b.bId, b.bName,b.type, b.project_id,cl.cId,cl.cName,"
//			+ "c.id,c.assigned_to,c.title,c.date,c.image,c.presentColumn,c.description,c.tags,c.storyPoints,c.valuePoints,c.issueType,"
//			+ "c.scheduledDate,c.attachmentTaA,c.story_details,c.scenario_given_when,c.data_expected_result,c.functional_area_passfail_details,"
//			+ "c.scalibility,c.test_case_demo_case,c.estimates,c.dor_approval_status,c.gitCommited,c.code_quality_review_done,peer_revired,"
//			+ "c.builds_without_error,c.unit_test_details,c.promoted_test_instance,c.configuration_changes,c.unit_test_details2,"
//			+ "c.closed_for_clocking,c.attachmentDoD,c.priority_index,c.related_issue,c.estimated_time,c.elapsed_time,c.start_date_time,c.completion_date_time,"
//			+ "c.project,c.repository,c.sprint_test,c.current_status,c.clock_status,c.project_priority,c.pinned_status,c.requested_by,"
//			+ "c.milestone,c.iteration,c.goal) "
//			+ "from Board b , Column cl,Card c"
////			+ " where c.goal != 'null'"
//			)
//	 List<Boardmicro> findByGoal();

}
