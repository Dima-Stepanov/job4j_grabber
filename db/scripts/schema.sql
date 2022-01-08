create table if not exists post(
	id serial primary key,
	name text not null,
	link text unique not null,
	text text,
	created timestamp
);