-- This file allow to write SQL commands that will be emitted in test and dev.
-- the $$ has to be replaced with ' and the ' with '' to hibernate to properly pass the commands to pg

-- https://www.postgresql.org/docs/current/errcodes-appendix.html

do
'
declare
    seniority_jr uuid;
    seniority_sr uuid;
    system_user uuid;
    user_two uuid;
    account_one uuid;
    account_two uuid;
    default_project uuid;
    project_two uuid;
    default_kanban_board uuid;
    default_sprint_board uuid;
    default_retro_board uuid;
    default_sprint uuid;
    default_kanban_card uuid;
    default_sprint_card uuid;
    default_cover_image varchar;
    cre_upd timestamp;
    default_card_status uuid;
    default_card_type uuid;
    board_type_kanban uuid;
    board_type_sprint uuid;
    default_description varchar;
begin
cre_upd := current_timestamp;
default_description := ''default description'';

seniority_jr := ''e73b9f18-b57b-466a-b614-81301b6389ea'';
seniority_sr := ''fb408e5b-72f1-42fe-b5b9-4cb802e3daf9'';

system_user := ''7132ad6d-6c39-4ff0-bf7e-e7eb6091f50b'';
user_two := ''9c540951-b340-46cb-b1be-331f3ef9d8c5'';

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

default_cover_image := ''https://via.placeholder.com/80?text=BMC+Flow'';

default_card_status := ''da84184b-2f73-4c74-a2c4-78ec1a2a3fe0'';
default_card_type := ''2afb2efe-0a46-4787-ad29-f99f88e44809'';

board_type_kanban := ''10e1cd86-9382-40ea-bbde-76bf48807b62'';
board_type_sprint := ''a0a012b5-313b-4aa7-ad7a-8a664449bd36'';


-- first create the default user to use it as creator of the rest of the domain
insert into users(id, email, call_sign, password, avatar, seniority_id, created_at, updated_at, created_by_id) VALUES
    (system_user, ''admin@demo.com'', ''Demo User'', ''admin'', ''https://robohash.org/'' || system_user, null, cre_upd, cre_upd, system_user);

-- insert Catalog data
insert into seniority(id, name, level, description, is_system, created_at, updated_at, created_by_id) VALUES
    (gen_random_uuid(), ''placement'', 100, ''temporary training, none or minimal working experience'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''trainee'', 200, ''being trained on the job, requires full guidance'', true, cre_upd, cre_upd, system_user),
    (seniority_jr, ''junior'', 300, ''can achieve simple tasks, requires heavy guidance'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''semi-sr'', 400, ''can achieve medium complex tasks, requires some guidance'', true, cre_upd, cre_upd, system_user),
    (seniority_sr, ''senior'', 500, ''can achieve highly complex tasks, requires minimum to no guidance'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''architect'', 600, ''can achieve highly complex tasks, can design systems, requires no guidance'', true, cre_upd, cre_upd, system_user);

insert into label(id, color_hex, description, name, created_at, updated_at, created_by_id) VALUES
    (gen_random_uuid(), ''#0f5772'', ''my personal stuff'', ''personal'', cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''#0f5772'', ''this is a back-end label'', ''back-end'', cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''#bee4ff'', ''this is a front-end label'', ''front-end'', cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''#ffdba3'', ''this is a devops label'', ''devops'', cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''#c37900'', ''this is a qa label'', ''qa'', cre_upd, cre_upd, system_user);

insert into card_status(id, name, is_system, created_at, updated_at, created_by_id) values
    (default_card_status, ''new'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''in progress'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''completed'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''blocked'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''blocked-internal'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''blocked-external'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''paused'', true, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''testing'', true, cre_upd, cre_upd, system_user),
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
    (gen_random_uuid(), ''engineering'', '''', true, cre_upd, cre_upd, system_user),
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
insert into users(id, email, call_sign, avatar, seniority_id, password, created_at, updated_at, created_by_id) VALUES
    (user_two, ''maverick@demo.com'', ''maverick'', ''https://robohash.org/'' || user_two, seniority_sr, ''maverick'', cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''goose@demo.com'', ''goose'', ''https://robohash.org/'' || seniority_jr, seniority_jr, ''goose'', cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''iceman@demo.com'', ''iceman'', ''https://robohash.org/'' || seniority_sr, seniority_jr, ''iceman'', cre_upd, cre_upd, system_user);

insert into account(id, name, description, cover_image, created_at, updated_at, created_by_id) VALUES
    (account_one, ''default-account'', ''system default account'', ''https://robohash.org/'' || account_one, cre_upd, cre_upd, system_user),
    (account_two, ''account-two'', ''another demo pre created account'', ''https://robohash.org/'' || account_two, cre_upd, cre_upd, system_user);

insert into project(id, name, account_id, description, cover_image, created_at, updated_at, created_by_id) values
    (default_project, ''default-project'', account_one, default_description, ''https://robohash.org/'' || default_project, cre_upd, cre_upd, system_user),
    (project_two, ''bmc-site'', account_one, default_description, ''https://robohash.org/'' || default_project, cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''bmc-data-pipeline'', account_two, default_description, ''https://robohash.org/'' || gen_random_uuid(), cre_upd, cre_upd, system_user),
    (gen_random_uuid(), ''test-project'', account_two, default_description, ''https://robohash.org/'' || gen_random_uuid(), cre_upd, cre_upd, system_user);

insert into board(id, name, description, cover_image, board_type_id, project_id, created_at, updated_at, created_by_id) values
    (default_kanban_board, ''default-kanban-board'', default_description, default_cover_image, board_type_kanban, default_project, cre_upd, cre_upd, system_user),
    (default_sprint_board, ''default-sprint-board'', default_description, default_cover_image, board_type_sprint, default_project, cre_upd, cre_upd, system_user);

insert into retrospective(id, created_at, updated_at, created_by_id, project_id) VALUES
    (default_retro_board, cre_upd, cre_upd, system_user, default_project);

insert into sprint(id, created_at, updated_at, goal, name, from_date, to_date, created_by_id, board_id, project_id, retro_board_id) VALUES
    (default_sprint, cre_upd, cre_upd, ''this is a sprint Goal'', ''this is the sprint name'', make_timestamp(2023, 10, 01, 00, 00, 00), make_timestamp(2023, 10, 15, 00, 00, 00), system_user, default_sprint_board, default_project, default_retro_board);

insert into card(id, name, description, cover_image, due_date, board_id, card_status_id, card_type_id, created_at, updated_at, created_by_id) values
    (default_kanban_card, ''default-card'', default_description, default_cover_image, make_timestamp(2023, 10, 01, 00, 00, 00), default_kanban_board, default_card_status, default_card_type, cre_upd, cre_upd, system_user),
    (default_sprint_card, ''default-sprint-card'', default_description, default_cover_image, make_timestamp(2023, 10, 01, 00, 00, 00), default_sprint_board, default_card_status, default_card_type, cre_upd, cre_upd, system_user);

insert into project_users(user_id, project_id) VALUES
    (system_user, default_project),
    (system_user, project_two),
    (user_two, default_project);
end;
';
