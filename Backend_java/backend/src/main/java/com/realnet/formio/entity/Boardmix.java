package com.realnet.formio.entity;

import java.util.Date;

public interface Boardmix {

	String getb_id();

	String getb_name();

	String getc_name();

	String gettype();

	String getid();

	String getassigned_to();

	String getattachment_dod();

	String getattachment_taa();

	String getbuilds_without_error();

	String getclock_status();

	String getclosed_for_clocking();

	String getcode_quality_review_done();

	String getcompletion_date_time();

	String getconfiguration_changes();

	String getcurrent_status();

	String getdata_expected_result();

	String getdate();

	String getdescription();

	String getdor_approval_status();

	String getelapsed_time();

	String getestimated_time();

	String getestimates();

	String getfunctional_area_passfail_details();

	String getgit_commited();

	String getimage();

	String getissue_type();

	String getpeer_revired();

	String getpinned_status();

	String getpresent_column();

	String getpriority_index();

	String getproject();

	String getproject_priority();

	String getpromoted_test_instance();

	String getrelated_issue();

	String getrepository();

	String getscalibility();

	String getscenario_given_when();

	String getscheduled_date();

	String getsprint_test();

	String getstart_date_time();

	String getstory_points();

	String getstory_details();

	String gettags();

	String gettest_case_demo_case();

	String gettitle();

	String getunit_test_details();

	String getunit_test_details2();

	String getvalue_points();

	String getrequested_by();

	String getgoal();

	String getmilestone();

	String getiteration();

	Date getdate_created();
}
