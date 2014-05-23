# --- !Ups

alter table tournament add column google_maps_url varchar(255);

# --- !Downs

alter table tournament drop column google_maps_url;
