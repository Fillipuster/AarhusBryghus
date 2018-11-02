use AarhusBryghus
go

drop table if exists ProduktLinjer
drop table if exists Salg
drop table if exists ProduktPriser
drop table if exists PrisKategorier
drop table if exists Produkter
drop table if exists ProduktKategorier
go


create table ProduktKategorier
(
navn varchar(10) primary key
)

create table Produkter
(
id int primary key identity,
navn varchar(20),
beskrivelse varchar(255),
produktKategori varchar(10) foreign key references ProduktKategorier(navn),
)

create table PrisKategorier
(
navn varchar(20) primary key
)

create table ProduktPriser
(
id int primary key identity,
pris decimal,
rabat decimal,
prisKategori varchar(20) foreign key references PrisKategorier(navn),
produkt int foreign key references Produkter(id),

produktPrisKategori varchar(20) unique (produkt, prisKategori)
)

create table Salg
(
id int primary key identity,
dato datetime
)

create table ProduktLinjer
(
antal int,
aftaltPris decimal,
salg int foreign key references Salg(id),
produktPris int foreign key references ProduktPriser(id)
)