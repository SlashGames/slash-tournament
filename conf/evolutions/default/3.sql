# --- !Ups

create table token (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  code                      varchar(255),
  type                      varchar(255),
  expires                   datetime,
  constraint pk_token primary key (id))
;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table token;

SET FOREIGN_KEY_CHECKS=1;