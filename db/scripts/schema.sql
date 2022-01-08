create table if not exists post(
	id serial primary key,
	name varchar(255) not null,
	link varchar(255) unique not null,
	text text,
	created timestamp
);