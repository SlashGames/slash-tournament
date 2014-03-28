# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table game (
  name                      varchar(255) not null,
  rules                     varchar(255),
  constraint pk_game primary key (name))
;

create table tournament (
  id                        bigint not null,
  name                      varchar(255),
  date                      varchar(255),
  location                  varchar(255),
  judge                     varchar(255),
  constraint pk_tournament primary key (id))
;

create sequence game_seq;

create sequence tournament_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists game;

drop table if exists tournament;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists game_seq;

drop sequence if exists tournament_seq;

