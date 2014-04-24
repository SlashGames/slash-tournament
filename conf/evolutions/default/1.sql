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

create table match (
  id                        bigint auto_increment not null,
  round                     integer,
  tournament_id             bigint,
  player1_email             varchar(255),
  player2_email             varchar(255),
  player1wins               integer,
  player2wins               integer,
  constraint pk_match primary key (id))
;

create table participation (
  id                        bigint auto_increment not null,
  tournament_id             bigint,
  participant_email         varchar(255),
  constraint pk_participation primary key (id))
;

create table tournament (
  id                        bigint auto_increment not null,
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
  is_admin                  tinyint(1) default 0,
  constraint pk_tournament_user primary key (email))
;

alter table hearthstone_deck add constraint fk_hearthstone_deck_heroClass_1 foreign key (hero_class_name) references hearthstone_class (name) on delete restrict on update restrict;
create index ix_hearthstone_deck_heroClass_1 on hearthstone_deck (hero_class_name);
alter table hearthstone_participation add constraint fk_hearthstone_participation_participation_2 foreign key (participation_id) references participation (id) on delete restrict on update restrict;
create index ix_hearthstone_participation_participation_2 on hearthstone_participation (participation_id);
alter table hearthstone_participation add constraint fk_hearthstone_participation_deck1_3 foreign key (deck1_id) references hearthstone_deck (id) on delete restrict on update restrict;
create index ix_hearthstone_participation_deck1_3 on hearthstone_participation (deck1_id);
alter table hearthstone_participation add constraint fk_hearthstone_participation_deck2_4 foreign key (deck2_id) references hearthstone_deck (id) on delete restrict on update restrict;
create index ix_hearthstone_participation_deck2_4 on hearthstone_participation (deck2_id);
alter table hearthstone_participation add constraint fk_hearthstone_participation_deck3_5 foreign key (deck3_id) references hearthstone_deck (id) on delete restrict on update restrict;
create index ix_hearthstone_participation_deck3_5 on hearthstone_participation (deck3_id);
alter table match add constraint fk_match_tournament_6 foreign key (tournament_id) references tournament (id) on delete restrict on update restrict;
create index ix_match_tournament_6 on match (tournament_id);
alter table match add constraint fk_match_player1_7 foreign key (player1_email) references tournament_user (email) on delete restrict on update restrict;
create index ix_match_player1_7 on match (player1_email);
alter table match add constraint fk_match_player2_8 foreign key (player2_email) references tournament_user (email) on delete restrict on update restrict;
create index ix_match_player2_8 on match (player2_email);
alter table participation add constraint fk_participation_tournament_9 foreign key (tournament_id) references tournament (id) on delete restrict on update restrict;
create index ix_participation_tournament_9 on participation (tournament_id);
alter table participation add constraint fk_participation_participant_10 foreign key (participant_email) references tournament_user (email) on delete restrict on update restrict;
create index ix_participation_participant_10 on participation (participant_email);
alter table tournament add constraint fk_tournament_game_11 foreign key (game_name) references game (name) on delete restrict on update restrict;
create index ix_tournament_game_11 on tournament (game_name);
alter table tournament add constraint fk_tournament_format_12 foreign key (format_name) references tournament_format (name) on delete restrict on update restrict;
create index ix_tournament_format_12 on tournament (format_name);
alter table tournament add constraint fk_tournament_mode_13 foreign key (mode_name) references tournament_mode (name) on delete restrict on update restrict;
create index ix_tournament_mode_13 on tournament (mode_name);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table game;

drop table hearthstone_class;

drop table hearthstone_deck;

drop table hearthstone_participation;

drop table match;

drop table participation;

drop table tournament;

drop table tournament_format;

drop table tournament_mode;

drop table tournament_user;

SET FOREIGN_KEY_CHECKS=1;

