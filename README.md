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

#- 2023/09/11 update table song by run this sql script

alter table song
add versioning text;

alter table song
add system_admin_score int;

#- 2023/09/26 update table song by run this sql script

alter table song
add audio_versioning text;

alter table song
add purposes list<text>;

alter table playlist
add purposes list<text>;

#- 2023/10/11 update table song by run this sql script

alter table playlist
add is_library boolean;

update song set is_offline=true where id in 
(6d18c496-e70a-4d24-a877-ed07a81eb90b,
f2d62876-9087-48fc-ac4c-c11eebc7615f,
afcf814a-8a1f-490e-bcf1-b2d49a1d0c6c,
c56cdbea-f85d-475b-bc0a-514c9265c551,
cb991680-c8c7-4c41-89d1-e57dc1bbd45a,
33133ebe-59e5-496a-b8cf-597211cdff53,
98090f94-ab62-4bf0-8e19-8317f4e2c381,
d63acd6f-b16b-4f3d-af0a-ef86e3e11386,
2778a8ec-3833-48a8-8ae6-792d0c493012,
ab292e48-817c-48ab-81e1-edd3f74c5673,
6f1ccb2e-f27c-40ac-8237-67eabc25f266,
b38f8c4f-c1b5-4d1f-bf9e-d1e17a5effdf,
347bbaf9-980d-4756-843a-d9da1f1a7c08,
b7eef4e8-e9be-40b4-a7e6-f96860672447,
528f62e6-3031-4cac-a9ef-72a9166859c6,
e73413e8-86c5-4950-9820-3346cf7079d5,
ecb611f8-8dd5-417c-bb61-f75785120ccc,
b61d839c-fc20-47be-a820-2c110becf698,
565de793-4cfd-4b5a-abea-2abf1ba678ba,
664c6933-c976-40a5-84a1-73919fec6c11)

#- 2023/10/18 update table user by run this sql script

alter table user
add last_changed_username_at timestamp;

alter table profiling_qa_3
add title text;

alter table profiling_qa_3
add image_url text;

#- 2023/10/19 change question list of Deleting Account

INSERT INTO staging.profiling_qa (id, answer, created_at, language, question, question_type, type, updated_at) VALUES (b20528c2-70df-4032-adb1-da15a12662a9, [I don't have a FRENZ band, I don't want to use FRENZ anymore, I found better applications, The band does not work the way I expected it to], '2022-07-15 09:08:14.000', 'EN', 'Question 1', 'SINGLE_CHOICE', 'DELETE_ACCOUNT', '2022-07-12 16:20:47.000');
INSERT INTO staging.profiling_qa (id, answer, created_at, language, question, question_type, type, updated_at) VALUES (b20528c2-70df-4032-adb1-da15a12662a3, [Tôi không có vòng đeo FRENZ, Tôi không muốn tiếp tục sử dụng FRENZ, Tôi tìm thấy các ứng dụng tốt hơn, Thông báo không liên quan, Vòng đeo hoạt động không như mong đợi], '2022-07-15 09:08:14.000', 'VN', 'Câu hỏi 1', 'SINGLE_CHOICE', 'DELETE_ACCOUNT', '2022-07-12 16:20:47.000');

Move all data of table profiling_qa_3 to Production

#- 2023/11/03 change user_profile table

alter table user_profile
add survey_type int;

alter table user_profile
add survey_reminder_count int;

alter table user_profile
add survey_reminder_disabled boolean;

alter table user_profile
add voice_coach_reminder_count int;

alter table user_profile
add voice_coach_reminder_disabled boolean;

alter table user_profile
add session_reminder_disabled boolean;

#- 2023/11/13 change session_1 table

alter table session_1
add type text;

























