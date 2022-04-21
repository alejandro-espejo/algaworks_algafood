alter table restaurante add ativo tinyint(1) not null;
SET SQL_SAFE_UPDATES=0;
update restaurante set ativo = true;