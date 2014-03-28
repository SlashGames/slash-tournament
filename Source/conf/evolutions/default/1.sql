# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table game (
  name                      varchar(255) not null,
  rules                     varchar(32768),
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
  tournament_id             bigint,
  participant_email         varchar(255),
  battle_tag                varchar(255),
  deck1_id                  bigint,
  deck2_id                  bigint,
  deck3_id                  bigint,
  seed_rank                 integer,
  constraint pk_hearthstone_participation primary key (id))
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

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  is_admin                  boolean,
  constraint pk_user primary key (email))
;

create sequence game_seq;

create sequence hearthstone_class_seq;

create sequence hearthstone_deck_seq;

create sequence hearthstone_participation_seq;

create sequence tournament_seq;

create sequence tournament_format_seq;

create sequence tournament_mode_seq;

create sequence user_seq;

alter table hearthstone_deck add constraint fk_hearthstone_deck_heroClass_1 foreign key (hero_class_name) references hearthstone_class (name) on delete restrict on update restrict;
create index ix_hearthstone_deck_heroClass_1 on hearthstone_deck (hero_class_name);
alter table hearthstone_participation add constraint fk_hearthstone_participation_t_2 foreign key (tournament_id) references tournament (id) on delete restrict on update restrict;
create index ix_hearthstone_participation_t_2 on hearthstone_participation (tournament_id);
alter table hearthstone_participation add constraint fk_hearthstone_participation_p_3 foreign key (participant_email) references user (email) on delete restrict on update restrict;
create index ix_hearthstone_participation_p_3 on hearthstone_participation (participant_email);
alter table hearthstone_participation add constraint fk_hearthstone_participation_d_4 foreign key (deck1_id) references hearthstone_deck (id) on delete restrict on update restrict;
create index ix_hearthstone_participation_d_4 on hearthstone_participation (deck1_id);
alter table hearthstone_participation add constraint fk_hearthstone_participation_d_5 foreign key (deck2_id) references hearthstone_deck (id) on delete restrict on update restrict;
create index ix_hearthstone_participation_d_5 on hearthstone_participation (deck2_id);
alter table hearthstone_participation add constraint fk_hearthstone_participation_d_6 foreign key (deck3_id) references hearthstone_deck (id) on delete restrict on update restrict;
create index ix_hearthstone_participation_d_6 on hearthstone_participation (deck3_id);
alter table tournament add constraint fk_tournament_game_7 foreign key (game_name) references game (name) on delete restrict on update restrict;
create index ix_tournament_game_7 on tournament (game_name);
alter table tournament add constraint fk_tournament_format_8 foreign key (format_name) references tournament_format (name) on delete restrict on update restrict;
create index ix_tournament_format_8 on tournament (format_name);
alter table tournament add constraint fk_tournament_mode_9 foreign key (mode_name) references tournament_mode (name) on delete restrict on update restrict;
create index ix_tournament_mode_9 on tournament (mode_name);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists game;

drop table if exists hearthstone_class;

drop table if exists hearthstone_deck;

drop table if exists hearthstone_participation;

drop table if exists tournament;

drop table if exists tournament_format;

drop table if exists tournament_mode;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists game_seq;

drop sequence if exists hearthstone_class_seq;

drop sequence if exists hearthstone_deck_seq;

drop sequence if exists hearthstone_participation_seq;

drop sequence if exists tournament_seq;

drop sequence if exists tournament_format_seq;

drop sequence if exists tournament_mode_seq;

drop sequence if exists user_seq;

