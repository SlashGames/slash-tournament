# --- !Ups

create table game (
  name                      varchar(255) not null,
  rules                     varchar(10000),
  icon                      varchar(255),
  constraint pk_game primary key (name))
;

create table game_rules (
  id                        bigint auto_increment not null,
  rules                     varchar(10000),
  revision                  bigint,
  game_name                 varchar(255),
  constraint pk_game_rules primary key (id))
;

create table hearthstone_class (
  name                      varchar(255) not null,
  constraint pk_hearthstone_class primary key (name))
;

create table hearthstone_deck (
  id                        bigint auto_increment not null,
  deck_link                 varchar(255),
  hero_class_name           varchar(255),
  constraint pk_hearthstone_deck primary key (id))
;

create table hearthstone_participation (
  id                        bigint auto_increment not null,
  participation_id          bigint,
  battle_tag                varchar(255),
  deck1_id                  bigint,
  deck2_id                  bigint,
  deck3_id                  bigint,
  seed_rank                 integer,
  constraint pk_hearthstone_participation primary key (id))
;

create table participation (
  id                        bigint auto_increment not null,
  tournament_id             bigint,
  participant_email         varchar(255),
  participation_status      varchar(10),
  constraint ck_participation_participation_status check (participation_status in ('SIGNED_UP','CHECKED_IN')),
  constraint pk_participation primary key (id))
;

create table token (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  code                      varchar(255),
  type                      varchar(14),
  expires                   datetime,
  constraint ck_token_type check (type in ('PASSWORD_RESET')),
  constraint pk_token primary key (id))
;

create table tournament (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  date                      datetime,
  location                  varchar(255),
  judge                     varchar(255),
  game_name                 varchar(255),
  game_rules_id             bigint,
  format_name               varchar(255),
  mode_name                 varchar(255),
  best_of                   integer,
  rules                     varchar(10000),
  status                    varchar(8),
  current_round             integer,
  constraint ck_tournament_status check (status in ('SIGNUP','RUNNING','FINISHED')),
  constraint pk_tournament primary key (id))
;

create table tournament_format (
  name                      varchar(255) not null,
  rules                     varchar(10000),
  constraint pk_tournament_format primary key (name))
;

create table tournament_match (
  id                        bigint auto_increment not null,
  round                     integer,
  tournament_id             bigint,
  player1_email             varchar(255),
  player2_email             varchar(255),
  player1wins               integer,
  player2wins               integer,
  constraint pk_tournament_match primary key (id))
;

create table tournament_mode (
  name                      varchar(255) not null,
  constraint pk_tournament_mode primary key (name))
;

create table tournament_user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  is_admin                  tinyint(1) default 0,
  constraint pk_tournament_user primary key (email))
;

alter table game_rules add constraint fk_game_rules_game_1 foreign key (game_name) references game (name) on delete restrict on update restrict;
create index ix_game_rules_game_1 on game_rules (game_name);
alter table hearthstone_deck add constraint fk_hearthstone_deck_heroClass_2 foreign key (hero_class_name) references hearthstone_class (name) on delete restrict on update restrict;
create index ix_hearthstone_deck_heroClass_2 on hearthstone_deck (hero_class_name);
alter table hearthstone_participation add constraint fk_hearthstone_participation_participation_3 foreign key (participation_id) references participation (id) on delete restrict on update restrict;
create index ix_hearthstone_participation_participation_3 on hearthstone_participation (participation_id);
alter table hearthstone_participation add constraint fk_hearthstone_participation_deck1_4 foreign key (deck1_id) references hearthstone_deck (id) on delete restrict on update restrict;
create index ix_hearthstone_participation_deck1_4 on hearthstone_participation (deck1_id);
alter table hearthstone_participation add constraint fk_hearthstone_participation_deck2_5 foreign key (deck2_id) references hearthstone_deck (id) on delete restrict on update restrict;
create index ix_hearthstone_participation_deck2_5 on hearthstone_participation (deck2_id);
alter table hearthstone_participation add constraint fk_hearthstone_participation_deck3_6 foreign key (deck3_id) references hearthstone_deck (id) on delete restrict on update restrict;
create index ix_hearthstone_participation_deck3_6 on hearthstone_participation (deck3_id);
alter table participation add constraint fk_participation_tournament_7 foreign key (tournament_id) references tournament (id) on delete restrict on update restrict;
create index ix_participation_tournament_7 on participation (tournament_id);
alter table participation add constraint fk_participation_participant_8 foreign key (participant_email) references tournament_user (email) on delete restrict on update restrict;
create index ix_participation_participant_8 on participation (participant_email);
alter table tournament add constraint fk_tournament_game_9 foreign key (game_name) references game (name) on delete restrict on update restrict;
create index ix_tournament_game_9 on tournament (game_name);
alter table tournament add constraint fk_tournament_gameRules_10 foreign key (game_rules_id) references game_rules (id) on delete restrict on update restrict;
create index ix_tournament_gameRules_10 on tournament (game_rules_id);
alter table tournament add constraint fk_tournament_format_11 foreign key (format_name) references tournament_format (name) on delete restrict on update restrict;
create index ix_tournament_format_11 on tournament (format_name);
alter table tournament add constraint fk_tournament_mode_12 foreign key (mode_name) references tournament_mode (name) on delete restrict on update restrict;
create index ix_tournament_mode_12 on tournament (mode_name);
alter table tournament_match add constraint fk_tournament_match_tournament_13 foreign key (tournament_id) references tournament (id) on delete restrict on update restrict;
create index ix_tournament_match_tournament_13 on tournament_match (tournament_id);
alter table tournament_match add constraint fk_tournament_match_player1_14 foreign key (player1_email) references tournament_user (email) on delete restrict on update restrict;
create index ix_tournament_match_player1_14 on tournament_match (player1_email);
alter table tournament_match add constraint fk_tournament_match_player2_15 foreign key (player2_email) references tournament_user (email) on delete restrict on update restrict;
create index ix_tournament_match_player2_15 on tournament_match (player2_email);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table game;

drop table game_rules;

drop table hearthstone_class;

drop table hearthstone_deck;

drop table hearthstone_participation;

drop table participation;

drop table token;

drop table tournament;

drop table tournament_format;

drop table tournament_match;

drop table tournament_mode;

drop table tournament_user;

SET FOREIGN_KEY_CHECKS=1;

