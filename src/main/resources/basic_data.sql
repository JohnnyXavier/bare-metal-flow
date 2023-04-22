-- This file allow to write SQL commands that will be emitted in test and dev.
-- the $$ has to be replaced with ' and the ' with '' to hibernate to properly pass the commands to pg

-- https://www.postgresql.org/docs/current/errcodes-appendix.html

do
'
declare
    --     alpha user ("real" demo user)
    user_alpha uuid;
    --
    account_alpha_personal uuid;
    account_alpha_work uuid;
    account_alpha_university uuid;
    --
    project_alpha_college uuid;
    project_alpha_hobbies uuid;
    project_alpha_work_client_1 uuid;
    project_alpha_work_client_2 uuid;
    project_alpha_work_client_3 uuid;
    --
    alpha_kanban_board_work_1 uuid;
    alpha_kanban_board_college_1 uuid;
    alpha_kanban_board_college_2 uuid;
    --
    alpha_board_col_new uuid;
    alpha_board_col_in_pro uuid;
    alpha_board_col_testing uuid;
    alpha_board_col_done uuid;
    --
    card_trnx_01 uuid;
    card_trnx_02 uuid;
    card_trnx_03 uuid;
    card_trnx_04 uuid;
    card_trnx_05 uuid;
    card_trnx_06 uuid;
    card_trnx_07 uuid;
    card_trnx_08 uuid;
    card_trnx_09 uuid;
    card_trnx_10 uuid;
    --
    seniority_jr uuid;
    seniority_sr uuid;
    --
    system_user uuid;
    user_two uuid;
    user_three uuid;
    user_four uuid;
    --
    account_one uuid;
    account_two uuid;
    --
    default_project uuid;
    project_two uuid;
    --
    default_kanban_board uuid;
    --
    default_sprint_board uuid;
    --
    default_retro_board uuid;
    --
    default_sprint uuid;
    --
    default_kanban_card uuid;
    default_sprint_card uuid;
    default_cover_image_ varchar;
    --
    cre_upd timestamp;
    --
    card_status_new uuid;
    card_status_in_progress uuid;
    card_status_testing uuid;
    card_status_done uuid;
    --
    default_card_type uuid;
    --
    board_type_kanban uuid;
    board_type_sprint uuid;
    --
    department_eng uuid;
    --
    default_description varchar;
    robohash varchar;
    robohash_default varchar;
    --
    lorem_ipsum_10w varchar;
    lorem_ipsum_20w varchar;
    lorem_ipsum_1p varchar;
    lorem_ipsum_3s varchar;
    --
    label_pers uuid;
    label_be uuid;
    label_fe uuid;
    label_devops uuid;
    label_qa uuid;
    label_urgent uuid;
    label_important uuid;
begin

-- alpha
user_alpha := ''5fca2f0b-0318-4d05-8ee7-e7b9b48bf48d'';
--
account_alpha_personal := gen_random_uuid();
account_alpha_work := gen_random_uuid();
account_alpha_university := gen_random_uuid();
--
project_alpha_college := gen_random_uuid();
project_alpha_hobbies := gen_random_uuid();
project_alpha_work_client_1 := gen_random_uuid();
project_alpha_work_client_2 := gen_random_uuid();
project_alpha_work_client_3 := gen_random_uuid();
--
alpha_kanban_board_work_1 := gen_random_uuid();
alpha_kanban_board_college_1 := ''5fca2f0b-0318-4d05-8ee7-e7b9b48bf401'';
alpha_kanban_board_college_2 := gen_random_uuid();
--
alpha_board_col_new := gen_random_uuid();
alpha_board_col_in_pro := gen_random_uuid();
alpha_board_col_testing := gen_random_uuid();
alpha_board_col_done := gen_random_uuid();
--
card_trnx_01 := ''fb90e4cf-c725-426a-9066-07ae2577a3aa'';
card_trnx_02 := gen_random_uuid();
card_trnx_03 := gen_random_uuid();
card_trnx_04 := gen_random_uuid();
card_trnx_05 := gen_random_uuid();
card_trnx_06 := gen_random_uuid();
card_trnx_07 := gen_random_uuid();
card_trnx_08 := gen_random_uuid();
card_trnx_09 := gen_random_uuid();
card_trnx_10 := gen_random_uuid();
-- alpha END
cre_upd := current_timestamp;
default_description := ''default description'';

seniority_jr := ''e73b9f18-b57b-466a-b614-81301b6389ea'';
seniority_sr := ''fb408e5b-72f1-42fe-b5b9-4cb802e3daf9'';

system_user := ''7132ad6d-6c39-4ff0-bf7e-e7eb6091f50b'';
user_two := ''9c540951-b340-46cb-b1be-331f3ef9d8c5'';
user_three := gen_random_uuid();
user_four := gen_random_uuid();

account_one := ''a652ad6d-b99a-4ff0-1f7e-e7eb6091f50b'';
account_two := ''da2836a2-576f-4d8b-8a35-79a15f0b8c88'';

default_project := ''2ad49ee4-c629-4bb5-9bd4-a59de16a8c72'';
project_two := ''1da6480d-f183-4ea5-9890-039a133c15e9'';

default_kanban_board := ''7b8e9944-5a60-43a8-a964-d21c22f0674a'';
default_sprint_board := ''38bca428-928c-475e-bcf0-3c1e9943932d'';
default_retro_board := ''4280ad11-a525-4901-a2cc-df0507aaf283'';

default_kanban_card := ''fb90e4cf-c725-426a-9066-07ae2577a3bb'';
default_sprint_card := ''fb408e5b-72f1-42fe-b5b9-4cb802e3daf9'';

default_sprint := ''fb408e5b-72f1-42fe-b5b9-4cb802e3daf9'';

default_cover_image_ := ''https://via.placeholder.com/80?text=BMC+Flow'';
robohash := ''https://robohash.org/'';
robohash_default := ''https://robohash.org/default'';

card_status_new := gen_random_uuid();
card_status_in_progress := gen_random_uuid();
card_status_testing := gen_random_uuid();
card_status_done := gen_random_uuid();
default_card_type := ''2afb2efe-0a46-4787-ad29-f99f88e44809'';

board_type_kanban := ''10e1cd86-9382-40ea-bbde-76bf48807b62'';
board_type_sprint := ''a0a012b5-313b-4aa7-ad7a-8a664449bd36'';

department_eng := ''a1f012b5-313b-4aa7-ad7a-8a664449bdd4'';

lorem_ipsum_10w := ''Lorem ipsum dolor sit amet, consectetur adipiscing elit...'';
lorem_ipsum_20w := ''Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'';
lorem_ipsum_3s := ''Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Pellentesque habitant morbi tristique senectus et netus. Non arcu risus quis varius quam quisque id diam vel.'';
lorem_ipsum_1p := ''Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Pellentesque elit ullamcorper dignissim cras. Posuere sollicitudin aliquam ultrices sagittis orci a scelerisque purus semper. Libero enim sed faucibus turpis in eu. Et pharetra pharetra massa massa. Sit amet massa vitae tortor condimentum lacinia quis vel. Ultrices eros in cursus turpis massa. Cras semper auctor neque vitae tempus quam pellentesque. Nisl tincidunt eget nullam non. Quam elementum pulvinar etiam non quam. Semper auctor neque vitae tempus. Quis eleifend quam adipiscing vitae. Turpis egestas integer eget aliquet nibh praesent tristique. At tempor commodo ullamcorper a lacus vestibulum sed arcu. Vivamus at augue eget arcu. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus. Enim ut tellus elementum sagittis. Euismod in pellentesque massa placerat duis ultricies lacus. Augue eget arcu dictum varius duis at. Neque egestas congue quisque egestas.'';

label_pers := gen_random_uuid();
label_be := gen_random_uuid();
label_fe := gen_random_uuid();
label_devops := gen_random_uuid();
label_qa := gen_random_uuid();
label_urgent := gen_random_uuid();
label_important := gen_random_uuid();

-- first create the default user to use it as creator of the rest of the domain
insert into users(id, email, call_sign, password, avatar, seniority_id, created_at, updated_at, created_by_id) VALUES
    (system_user, ''admin@demo.com'', ''admin user'', ''admin'', robohash || system_user || ''?set=set3'', null, cre_upd, cre_upd, system_user);

-- insert Catalog data
insert into seniority(id, name, level, description, is_system, created_at, updated_at, created_by_id) VALUES
    (gen_random_uuid(), ''placement'', 100, ''temporary training, none or minimal working experience'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''trainee'', 200, ''being trained on the job, requires full guidance'', true, cre_upd, cre_upd, system_user),
    (seniority_jr, ''junior'', 300, ''can achieve simple tasks, requires heavy guidance'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''semi-sr'', 400, ''can achieve medium complex tasks, requires some guidance'', true, cre_upd, cre_upd, system_user),
    (seniority_sr, ''senior'', 500, ''can achieve highly complex tasks, requires minimum to no guidance'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''architect'', 600, ''can achieve highly complex tasks, can design systems, requires no guidance'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''guru front end'', 600, ''front end houdini, go to expert when all hopes are lost'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''guru back end'', 600, ''back end houdini, go to expert when all hopes are lost'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''guru devops end'', 600, ''devops end houdini, go to expert when all hopes are lost'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''guru admin end'', 600, ''admin/ops houdini, go to expert when all hopes are lost'', true, cre_upd, cre_upd, system_user);

insert into label(id, color_hex, description, name, created_at, updated_at, created_by_id, is_system) VALUES
    (label_pers, ''#576CBC'', ''my personal stuff'', ''personal'', cre_upd, cre_upd, system_user, true),
    (label_be, ''#B2A4FF'', ''this is a back-end label'', ''back-end'', cre_upd, cre_upd, system_user, true),
    (label_fe, ''#655DBB'', ''this is a front-end label'', ''front-end'', cre_upd, cre_upd, system_user, true),
    (label_devops, ''#0E8388'', ''this is a devops label'', ''devops'', cre_upd, cre_upd, system_user, true),
    (label_qa, ''#C8B6A6'', ''this is a qa label'', ''qa'', cre_upd, cre_upd, system_user, true),
    (label_urgent, ''#CC3636'', ''this is an URGENT label'', ''URGENT'', cre_upd, cre_upd, system_user, true),
    (label_important, ''#ECA869'', ''this is an important label'', ''important'', cre_upd, cre_upd, system_user, true);

insert into card_status(id, name, is_system, created_at, updated_at, created_by_id) values
    (card_status_new, ''new'', true, cre_upd, cre_upd, system_user),
    (card_status_in_progress, ''in progress'', true, cre_upd, cre_upd, system_user),
    (card_status_testing, ''testing'', true, cre_upd, cre_upd, system_user),
    (card_status_done, ''done'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''blocked'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''blocked-internal'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''blocked-external'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''paused'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''testing-internal'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''testing-client'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''backlog'', true, cre_upd, cre_upd, system_user);

insert into card_type(id, name, is_system, created_at, updated_at, created_by_id) values
    (default_card_type, ''story'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''bug'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''epic'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''task'', true, cre_upd, cre_upd, system_user);

insert into card_difficulty(id, name, level, is_system, created_at, updated_at, created_by_id) values
    (gen_random_uuid(), ''trivial'', 100, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''easy'', 200, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''medium'', 300, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''hard'', 400, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''very hard'', 500, true, cre_upd, cre_upd, system_user);

insert into board_type(id, name, description, is_system, created_at, updated_at, created_by_id) values
    (board_type_kanban, ''kanban'', ''pick from a backlog working style'', true, cre_upd, cre_upd, system_user),
    (board_type_sprint, ''sprint'', ''short iterations in time working style'', true, cre_upd, cre_upd, system_user);

insert into department(id, name, description, is_system, created_at, updated_at, created_by_id) VALUES
    (department_eng, ''engineering'', '''', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''quality'', '''', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''operations'', '''', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''finance'', '''', true, cre_upd, cre_upd, system_user);

insert into shrinkage(id, name, duration_in_min, percentage, is_system, created_at, updated_at, created_by_id) VALUES
    (gen_random_uuid(), ''general-05-%'', null, 5, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''general-10-%'', null, 10, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''general-15-%'', null, 15, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''general-20-%'', null, 20, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''general-25-%'', null, 25, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''general-30-%'', null, 30, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''general-35-%'', null, 35, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''general-40-%'', null, 40, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''general-55-%'', null, 45, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''general-50-%'', null, 50, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''coffee-break-15-min'', 15, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''coffee-break-10-min'', 10, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''personal-break-5-min'', 5, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''personal-break-10-min'', 10, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''personal-break-15-min'', 15, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''agile-standUp-10-min'', 10, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''agile-standUp-15-min'', 15, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''agile-standUp-20-min'', 20, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''agile-sprint-planning-30-min'', 30, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''agile-sprint-planning-45-min'', 45, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''agile-sprint-planning-60-min'', 60, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''agile-sprint-retro-30-min'', 30, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''agile-sprint-retro-45-min'', 45, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''agile-sprint-retro-60-min'', 60, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''meetings-client-15-min'', 15, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''meetings-client-30-min'', 30, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''meetings-client-45-min'', 45, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''meetings-client-60-min'', 60, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''meetings-internal-15-min'', 15, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''meetings-internal-30-min'', 30, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''meetings-internal-45-min'', 45, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''meetings-internal-60-min'', 60, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''meetings-external-15-min'', 15, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''meetings-external-30-min'', 30, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''meetings-external-45-min'', 45, null, true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''meetings-external-60-min'', 60, null, true, cre_upd, cre_upd, system_user);

-- insert demo data

update users set seniority_id = (select id from seniority where name = ''architect''), department_id = department_eng
where id = system_user;

insert into users(id, email, call_sign, avatar, department_id, seniority_id, password, created_at, updated_at, created_by_id) VALUES
    (user_two, ''maverick@demo.com'', ''maverick'', robohash || user_two || ''?set=set2'', department_eng, seniority_sr, ''maverick'', cre_upd, cre_upd, system_user),
    (user_three, ''goose@demo.com'', ''goose'', robohash || seniority_jr || ''?set=set2'', department_eng, seniority_jr, ''goose'', cre_upd, cre_upd, system_user),
    (user_four, ''iceman@demo.com'', ''iceman'', robohash || seniority_sr || ''?set=set2'', department_eng, seniority_jr, ''iceman'', cre_upd, cre_upd, system_user);

insert into account(id, name, description, cover_image, created_at, updated_at, created_by_id) VALUES
    (account_one, ''default-account'', ''system default account'', robohash || account_one, cre_upd, cre_upd, system_user),
    (account_two, ''account-two'', ''another demo pre created account'', robohash || account_two, cre_upd, cre_upd, system_user);


insert into project(id, name, account_id, description, cover_image, created_at, updated_at, created_by_id) values
    (default_project, ''default-project'', account_one, default_description, robohash || default_project, cre_upd, cre_upd, system_user),
    (project_two, ''bmc-site'', account_one, default_description, robohash || default_project, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''bmc-data-pipeline'', account_two, default_description, robohash || gen_random_uuid(), cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''test-project'', account_two, default_description, robohash || gen_random_uuid(), cre_upd, cre_upd, system_user);

insert into board(id, name, description, cover_image, board_type_id, project_id, created_at, updated_at, created_by_id) values
    (default_kanban_board, ''default-kanban-board'', default_description, robohash_default, board_type_kanban, default_project, cre_upd, cre_upd, system_user),
    (default_sprint_board, ''default-sprint-board'', default_description, robohash_default, board_type_sprint, default_project, cre_upd, cre_upd, system_user);


insert into retrospective(id, created_at, updated_at, created_by_id, project_id) VALUES
    (default_retro_board, cre_upd, cre_upd, system_user, default_project);

insert into sprint(id, created_at, updated_at, goal, name, from_date, to_date, created_by_id, board_id, project_id, retro_board_id) VALUES
    (default_sprint, cre_upd, cre_upd, ''this is a sprint Goal'', ''this is the sprint name'', make_timestamp(2023, 10, 01, 00, 00, 00), make_timestamp(2023, 10, 15, 00, 00, 00), system_user, default_sprint_board, default_project, default_retro_board);

insert into card(id, name, description, cover_image, due_date, board_id, card_status_id, card_type_id, created_at, updated_at, created_by_id) values
    (default_kanban_card, ''default-card'', default_description, robohash_default, make_timestamp(2023, 10, 01, 00, 00, 00), default_kanban_board, card_status_new, default_card_type, cre_upd, cre_upd, system_user),
    (default_sprint_card, ''default-sprint-card'', default_description, robohash_default, make_timestamp(2023, 10, 01, 00, 00, 00), default_sprint_board, card_status_new, default_card_type, cre_upd, cre_upd, system_user);

insert into project_users(user_id, project_id) VALUES
    (system_user, default_project),
    (system_user, project_two),
    (user_two, default_project);

-- Alpha data
insert into users(id, email, call_sign, avatar, department_id, seniority_id, password, created_at, updated_at, created_by_id) VALUES
    (user_alpha, ''alpha@demo.com'', ''alpha'', ''https://robohash.org/alpha?set=set2'', department_eng, seniority_sr, ''alpha'', cre_upd, cre_upd, user_alpha);

insert into account(id, name, description, cover_image, created_at, updated_at, created_by_id) VALUES
    (account_alpha_personal, ''personal account'', ''another demo pre created account'', robohash || account_alpha_personal, cre_upd, cre_upd, user_alpha),
    (account_alpha_university, ''university'', ''another demo pre created account'', robohash || account_alpha_university, cre_upd, cre_upd, user_alpha),
    (account_alpha_work, ''work'', ''another demo pre created account'', robohash || account_alpha_work, cre_upd, cre_upd, user_alpha);

insert into project(id, name, account_id, description, cover_image, created_at, updated_at, created_by_id) values
    (project_alpha_college, ''final projects'', account_alpha_university, ''End of year projects'', robohash || project_alpha_college, cre_upd, cre_upd, user_alpha),
    (gen_random_uuid(), ''summer projects'', account_alpha_university, ''Summer courses'', robohash || ''summer-projects'', cre_upd, cre_upd, user_alpha),
    (project_alpha_hobbies, ''hobbies'', account_alpha_personal, ''hobbies'', robohash || project_alpha_hobbies, cre_upd, cre_upd, user_alpha),
    (project_alpha_work_client_1, ''the burger joint'', account_alpha_work, ''burger joint projects'', robohash || project_alpha_work_client_1, cre_upd, cre_upd, user_alpha),
    (project_alpha_work_client_2, ''android client'', account_alpha_work, ''mobile client projects'', robohash || project_alpha_work_client_2, cre_upd, cre_upd, user_alpha),
    (project_alpha_work_client_3, ''running & co'', account_alpha_work, ''running & co projects'', robohash || project_alpha_work_client_3, cre_upd, cre_upd, user_alpha);

insert into board(id, name, is_favorite, description, cover_image, board_type_id, project_id, created_at, updated_at, created_by_id) values
    (alpha_kanban_board_work_1, ''burger shop online'', true, default_description, robohash_default, board_type_kanban, project_alpha_work_client_1, cre_upd, cre_upd, user_alpha),
    (gen_random_uuid(), ''payment points'', false, default_description, robohash_default, board_type_kanban, project_alpha_work_client_1, cre_upd, cre_upd, user_alpha),
    (gen_random_uuid(), ''world cup 2023'', true, default_description, robohash_default, board_type_kanban, project_alpha_work_client_2, cre_upd, cre_upd, user_alpha),
    (gen_random_uuid(), ''marathon 2023'', false, default_description, robohash_default, board_type_kanban, project_alpha_work_client_3, cre_upd, cre_upd, user_alpha),
    (gen_random_uuid(), ''arduino'', false, default_description, robohash_default, board_type_kanban, project_alpha_hobbies, cre_upd, cre_upd, user_alpha),
    (alpha_kanban_board_college_1, ''electronics'', true, default_description, robohash_default, board_type_kanban, project_alpha_college, cre_upd, cre_upd, user_alpha),
    (alpha_kanban_board_college_2, ''mechanics'', false, default_description, robohash_default, board_type_kanban, project_alpha_college, cre_upd, cre_upd, user_alpha);

insert into board_column(id, created_at, updated_at, created_by_id, account_id, board_id, project_id, status_id) values
    (alpha_board_col_new, cre_upd, cre_upd, user_alpha, account_alpha_university, alpha_kanban_board_college_1, project_alpha_college, card_status_new),
    (alpha_board_col_in_pro, cre_upd, cre_upd, user_alpha, account_alpha_university, alpha_kanban_board_college_1, project_alpha_college, card_status_in_progress),
    (alpha_board_col_testing, cre_upd, cre_upd, user_alpha, account_alpha_university, alpha_kanban_board_college_1, project_alpha_college, card_status_testing),
    (alpha_board_col_done, cre_upd, cre_upd, user_alpha, account_alpha_university, alpha_kanban_board_college_1, project_alpha_college, card_status_done);


insert into card(id, name, description, cover_image, due_date, board_id, board_column_id, card_status_id, card_type_id, created_at, updated_at, created_by_id) values
    (card_trnx_01, ''1st-card'', default_description, ''https://loremflickr.com/g/800/600/paris?lock=1'', make_timestamp(2023, 10, 01, 00, 00, 00), alpha_kanban_board_college_1, alpha_board_col_new, card_status_new, default_card_type, cre_upd, cre_upd, user_alpha),
    (card_trnx_02, lorem_ipsum_3s, lorem_ipsum_10w, ''https://loremflickr.com/g/800/600/car?lock=2'', make_timestamp(2023, 10, 01, 00, 00, 00), alpha_kanban_board_college_1, alpha_board_col_new, card_status_new, default_card_type, cre_upd, cre_upd, user_alpha),
    (card_trnx_03, ''3rd-card'', default_description, ''https://loremflickr.com/g/800/600/forest?lock=1'', make_timestamp(2023, 10, 01, 00, 00, 00), alpha_kanban_board_college_1, alpha_board_col_new, card_status_new, default_card_type, cre_upd, cre_upd, user_alpha),
    (card_trnx_04, ''This is a fine nice demonstration card name'', ''the description of this card is a bit longer than the default'', ''https://loremflickr.com/g/320/700/paris?lock=3'', make_timestamp(2023, 10, 01, 00, 00, 00), alpha_kanban_board_college_1, alpha_board_col_new, card_status_new, default_card_type, cre_upd, cre_upd, user_alpha),
    (card_trnx_05, lorem_ipsum_20w, lorem_ipsum_3s, ''https://loremflickr.com/g/600/700/paris?lock=8'', make_timestamp(2023, 10, 01, 00, 00, 00), alpha_kanban_board_college_1, alpha_board_col_in_pro, card_status_in_progress, default_card_type, cre_upd, cre_upd, user_alpha),
    (card_trnx_06, ''6th-card'', default_description, ''https://loremflickr.com/g/500/700/car?lock=3'', make_timestamp(2023, 10, 01, 00, 00, 00), alpha_kanban_board_college_1, alpha_board_col_in_pro, card_status_in_progress, default_card_type, cre_upd, cre_upd, user_alpha),
    (card_trnx_07, ''7th-card'', default_description, ''https://loremflickr.com/g/640/640/dog?lock=3'', make_timestamp(2023, 10, 01, 00, 00, 00), alpha_kanban_board_college_1, alpha_board_col_testing, card_status_testing, default_card_type, cre_upd, cre_upd, user_alpha),
    (card_trnx_08, ''8th-card'', default_description, ''https://loremflickr.com/g/200/200/tree?lock=3'', make_timestamp(2023, 10, 01, 00, 00, 00), alpha_kanban_board_college_1, alpha_board_col_testing, card_status_testing, default_card_type, cre_upd, cre_upd, user_alpha),
    (card_trnx_09, ''9th-card'', default_description, ''https://loremflickr.com/g/800/400/computer?lock=3'', make_timestamp(2023, 10, 01, 00, 00, 00), alpha_kanban_board_college_1, alpha_board_col_done, card_status_done, default_card_type, cre_upd, cre_upd, user_alpha),
    (card_trnx_10, ''10th-card'', default_description, ''https://loremflickr.com/g/200/400/paris?lock=30'', make_timestamp(2023, 10, 01, 00, 00, 00), alpha_kanban_board_college_1, alpha_board_col_done, card_status_done, default_card_type, cre_upd, cre_upd, user_alpha);

insert into card_label(card_id, label_id, board_id, created_at, created_by_id) VALUES
    (card_trnx_01, label_be, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_01, label_pers, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_01, label_fe, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_02, label_fe, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_02, label_pers, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_03, label_qa, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_03, label_devops, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_03, label_be, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_04, label_pers, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_05, label_be, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_05, label_pers, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_05, label_fe, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_05, label_urgent, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_08, label_devops, alpha_kanban_board_college_1, cre_upd, user_alpha),
    (card_trnx_08, label_important, alpha_kanban_board_college_1, cre_upd, user_alpha);

insert into project_users(user_id, project_id) VALUES
    (user_alpha, project_alpha_work_client_1),
    (user_alpha, project_alpha_work_client_2),
    (user_alpha, project_alpha_work_client_3),
    (user_alpha, project_alpha_college);


insert into board_users(board_id, user_id) VALUES
    (alpha_kanban_board_work_1, user_alpha),
    (alpha_kanban_board_work_1, user_two),
    (alpha_kanban_board_work_1, user_three),
    (alpha_kanban_board_work_1, user_four),
    (alpha_kanban_board_college_1, user_alpha),
    (alpha_kanban_board_college_1, system_user),
    (alpha_kanban_board_college_1, user_four),
    (alpha_kanban_board_college_1, user_two),
    (alpha_kanban_board_college_1, user_three),
    (alpha_kanban_board_college_2, user_two),
    (alpha_kanban_board_college_2, user_alpha);

insert into comment(id, created_at, updated_at, comment, created_by_id, card_id) values
    (gen_random_uuid(), cre_upd, cre_upd, ''a comment'', user_alpha, card_trnx_01),
    (gen_random_uuid(), cre_upd, cre_upd, ''another comment'', user_alpha, card_trnx_01),
    (gen_random_uuid(), cre_upd, cre_upd, ''yet another one comment'', user_alpha, card_trnx_01),
    (gen_random_uuid(), cre_upd, cre_upd, ''how come this is a comment'', user_alpha, card_trnx_01),
    (gen_random_uuid(), cre_upd, cre_upd, ''we did not see it comment!'', user_alpha, card_trnx_01);
end;
';
