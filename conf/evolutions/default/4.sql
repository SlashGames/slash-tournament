# --- !Ups

alter table participation add column participation_status varchar(255);

# --- !Downs

alter table participation drop column participation_status;
