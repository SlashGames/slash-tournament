# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table game (
  name                      varchar(255) not null,
  rules                     varchar(32768),
  icon                      varchar(255),
  constraint pk_game primary key (name))
;

create table hearthstone_class (
  name                      varchar(255) not null,
  constraint pk_hearthstone_class primary key (name))
;

create table hearthstone_deck (
  id                        bigint not null,
  deck_link                 varchar(255),
  hero_class_name           varchar(255),
  constraint pk_hearthstone_deck primary key (id))
;

create table hearthstone_participation (
  id                        bigint not null,
  participation_id          bigint,
  battle_tag                varchar(255),
  deck1_id                  bigint,
  deck2_id                  bigint,
  deck3_id                  bigint,
  seed_rank                 integer,
  constraint pk_hearthstone_participation primary key (id))
;

create table match (
  id                        bigint not null,
  round                     integer,
  tournament_id             bigint,
  player1_email             varchar(255),
  player2_email             varchar(255),
  player1wins               integer,
  player2wins               integer,
  constraint pk_match primary key (id))
;

create table participation (
  id                        bigint not null,
  tournament_id             bigint,
  participant_email         varchar(255),
  constraint pk_participation primary key (id))
;

create table tournament (
  id                        bigint not null,
  name                      varchar(255),
  date                      varchar(255),
  location                  varchar(255),
  judge                     varchar(255),
  game_name                 varchar(255),
  format_name               varchar(255),
  mode_name                 varchar(255),
  best_of                   integer,
  rules                     varchar(32768),
  status                    varchar(8),
  current_round             integer,
  constraint ck_tournament_status check (status in ('SIGNUP','RUNNING','FINISHED')),
  constraint pk_tournament primary key (id))
;

create table tournament_format (
  name                      varchar(255) not null,
  rules                     varchar(32768),
  constraint pk_tournament_format primary key (name))
;

create table tournament_mode (
  name                      varchar(255) not null,
  constraint pk_tournament_mode primary key (name))
;

create table tournament_user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  is_admin                  boolean,
  constraint pk_tournament_user primary key (email))
;

create sequence game_seq;

create sequence hearthstone_class_seq;

create sequence hearthstone_deck_seq;

create sequence hearthstone_participation_seq;

create sequence match_seq;

create sequence participation_seq;

create sequence tournament_seq;

create sequence tournament_format_seq;

create sequence tournament_mode_seq;

create sequence tournament_user_seq;

alter table hearthstone_deck add constraint fk_hearthstone_deck_heroClass_1 foreign key (hero_class_name) references hearthstone_class (name);
create index ix_hearthstone_deck_heroClass_1 on hearthstone_deck (hero_class_name);
alter table hearthstone_participation add constraint fk_hearthstone_participation_p_2 foreign key (participation_id) references participation (id);
create index ix_hearthstone_participation_p_2 on hearthstone_participation (participation_id);
alter table hearthstone_participation add constraint fk_hearthstone_participation_d_3 foreign key (deck1_id) references hearthstone_deck (id);
create index ix_hearthstone_participation_d_3 on hearthstone_participation (deck1_id);
alter table hearthstone_participation add constraint fk_hearthstone_participation_d_4 foreign key (deck2_id) references hearthstone_deck (id);
create index ix_hearthstone_participation_d_4 on hearthstone_participation (deck2_id);
alter table hearthstone_participation add constraint fk_hearthstone_participation_d_5 foreign key (deck3_id) references hearthstone_deck (id);
create index ix_hearthstone_participation_d_5 on hearthstone_participation (deck3_id);
alter table match add constraint fk_match_tournament_6 foreign key (tournament_id) references tournament (id);
create index ix_match_tournament_6 on match (tournament_id);
alter table match add constraint fk_match_player1_7 foreign key (player1_email) references tournament_user (email);
create index ix_match_player1_7 on match (player1_email);
alter table match add constraint fk_match_player2_8 foreign key (player2_email) references tournament_user (email);
create index ix_match_player2_8 on match (player2_email);
alter table participation add constraint fk_participation_tournament_9 foreign key (tournament_id) references tournament (id);
create index ix_participation_tournament_9 on participation (tournament_id);
alter table participation add constraint fk_participation_participant_10 foreign key (participant_email) references tournament_user (email);
create index ix_participation_participant_10 on participation (participant_email);
alter table tournament add constraint fk_tournament_game_11 foreign key (game_name) references game (name);
create index ix_tournament_game_11 on tournament (game_name);
alter table tournament add constraint fk_tournament_format_12 foreign key (format_name) references tournament_format (name);
create index ix_tournament_format_12 on tournament (format_name);
alter table tournament add constraint fk_tournament_mode_13 foreign key (mode_name) references tournament_mode (name);
create index ix_tournament_mode_13 on tournament (mode_name);



# --- !Downs

drop table if exists game cascade;

drop table if exists hearthstone_class cascade;

drop table if exists hearthstone_deck cascade;

drop table if exists hearthstone_participation cascade;

drop table if exists match cascade;

drop table if exists participation cascade;

drop table if exists tournament cascade;

drop table if exists tournament_format cascade;

drop table if exists tournament_mode cascade;

drop table if exists tournament_user cascade;

drop sequence if exists game_seq;

drop sequence if exists hearthstone_class_seq;

drop sequence if exists hearthstone_deck_seq;

drop sequence if exists hearthstone_participation_seq;

drop sequence if exists match_seq;

drop sequence if exists participation_seq;

drop sequence if exists tournament_seq;

drop sequence if exists tournament_format_seq;

drop sequence if exists tournament_mode_seq;

drop sequence if exists tournament_user_seq;

