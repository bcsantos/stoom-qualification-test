CREATE TABLE public.address (
	id uuid NOT NULL,
	street_name varchar(2000) NOT NULL,
	"number" varchar (100) NOT NULL,
	complement varchar(500),
	neighbourhood varchar(100) NOT NULL,
	city varchar(100) NOT NULL,
	state varchar(30) NOT NULL,
	country varchar(50) NOT NULL,
	zipcode varchar(10) NOT NULL,
	latitude varchar(50) NOT NULL,
	longitude varchar(50) NOT NULL,
	CONSTRAINT address_pk PRIMARY KEY (id)
);
