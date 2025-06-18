CREATE SCHEMA IF NOT EXISTS autocoder;

CREATE TABLE IF NOT EXISTS autocoder.user (
	username varchar(255) primary key,
	mail varchar(255) not null
);

CREATE TABLE IF NOT EXISTS autocoder.medical_note (
	id BIGSERIAL primary key,
	username varchar(255) not null,
	file_name varchar(255),
	extracted_text TEXT,
	uploaded_at timestamp not null,
	foreign key(username) references autocoder.user(username)
);

CREATE TABLE IF NOT EXISTS autocoder.result (
	id BIGSERIAL primary key,
	medical_note_id BIGSERIAL not null,
	result_json TEXT,
	processed_at timestamp not null,
	foreign key(medical_note_id) references autocoder.medical_note(id)
);
