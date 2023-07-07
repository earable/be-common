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
# done on production------------------------------------------------------------
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















