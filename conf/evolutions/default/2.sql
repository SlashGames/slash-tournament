# --- !Ups

alter table tournament add column game_rules_id bigint;

create table game_rules (
  id                        bigint auto_increment not null,
  rules                     varchar(10000),
  game_name                 varchar(255),
  revision					bigint,
  constraint pk_game_rules primary key (id))
;

alter table game_rules add constraint fk_game_rules_game_1 foreign key (game_name) references game (name) on delete restrict on update restrict;
create index ix_game_rules_game_1 on game_rules (game_name);

alter table tournament add constraint fk_tournament_game_rules_1 foreign key (game_rules_id) references game_rules (id) on delete restrict on update restrict;
create index ix_tournament_game_rules_1 on tournament (game_rules_id);

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table game_rules;

alter table tournament drop column game_rules_id;

SET FOREIGN_KEY_CHECKS=1;