ALTER TABLE book DROP COLUMN id;
ALTER TABLE book ADD COLUMN id SERIAL PRIMARY KEY;

INSERT INTO book(name, publicationYear) VALUES ( 'Sapiens' , 2011);
INSERT INTO book( name, publicationYear) VALUES ( 'Homo Deus' , 2015);
INSERT INTO book( name, publicationYear) VALUES ( 'Enlightenment Now' , 2018);
INSERT INTO book(name, publicationYear) VALUES ( 'Factfulness' , 2018);
INSERT INTO book(name, publicationYear) VALUES ( 'Sleepwalkers' , 2012);
INSERT INTO book( name, publicationYear) VALUES ( 'The Silk Roads' , 2015);
INSERT INTO book( name, publicationYear) VALUES ( 'The culture map' , 2016);
INSERT INTO book( name, publicationYear) VALUES ( 'Billy Summers' , 2021);
INSERT INTO book(name, publicationYear) VALUES ( 'The Handmaids Tale' , 2016);
INSERT INTO book( name, publicationYear) VALUES ( 'The Institue' , 2019);