# Release note

#- 2023/05/05 add this sql script
   alter table user
   add roles list<text>;


#- 2023/05/09 drop table action_log by run this sql script
drop table action_log;

#- 2023/05/10 change table playlist by run this sql script

alter table song
add languages List<text>;

alter table playlist
add languages List<text>;

#- 2023/05/12 change table user by run this sql script
alter table user
add user_agent text;

alter table user
add last_signed_in timestamp;
#- 2023/05/16 change table user_profile by run this sql script
alter table user_profile
add start_using_band_time timestamp;

#- 2023/05/30 change table earable_news by run this sql script
alter table earable_news
add language text;
# need to run on production------------------------------------------------------------
#- 2023/06/16 change table earable_news by run this sql script

alter table user_profile
add gender_1 text;

alter table user_profile
add country text;

#- 2023/06/21 change table user_profile by run this sql script

alter table user_profile
add tutorial_status int;

#- 2023/07/07 change table version_info by run this sql script
alter table version_info
add no_of_supported_versions int;

#- 2023/07/10 change table song by run this sql script
alter table song
add volume double;

#- 2023/07/20 change table song by run this sql script
alter table song
add is_preset_list boolean;

#- 2023/08/01 change table sessions by run this sql script

alter table sessions
add location text;

alter table sessions
add locationlatitude double;

alter table sessions
add locationlongitude double;

#- 2023/08/10 change table profiling_qa_2 by run this sql script

select * from profiling_qa_2 where id in (8bc24d50-f2d3-11ec-b939-0242ac120002
,8bc24d52-f2d3-11ec-b939-0242ac120014
,8bc24d52-f2d3-11ec-b939-0242ac120015
)


select * from profiling_qa_2 where origin_question_id = 8bc24d50-f2d3-11ec-b939-0242ac120002 allow filtering
select * from profiling_qa_2 where origin_question_id = 8bc24d52-f2d3-11ec-b939-0242ac120014 allow filtering
select * from profiling_qa_2 where origin_question_id = 8bc24d52-f2d3-11ec-b939-0242ac120015 allow filtering

#- 2023/08/18 add table session_1 by run this sql script


create table staging.session_1
(
userid            text,
profileid         text,
featurename       text,
startedtime       bigint,
clienttimestamp   bigint,
createddate       timestamp,
deviceid          text,
endedtime         bigint,
location          text,
locationlatitude  double,
locationlongitude double,
metadata          map<text, text>,
mode              text,
sessionid         text,
sessionsettingid  text,
timezone          text,
primary key (userid, profileid, featurename, startedtime, sessionid)
)

create index session_1_sessionid_idx
on staging.session_1 (sessionid);

#- 2023/08/18 add table user_insight by run this sql script

create table user_insight
(
user_id             uuid primary key,
created_at          timestamp,
focus_session_count int,
last_session_at     timestamp,
nap_session_count   int,
sleep_session_count int,
total_boosts        int,
updated_at          timestamp
);

#- 2023/09/11 add table song by run this sql script

alter table song
add versioning text;

alter table song
add system_admin_score int;

#- 2023/09/26 add table song by run this sql script

alter table song
add audio_versioning text;

alter table song
add purposes list<text>;

alter table playlist
add purposes list<text>;

















