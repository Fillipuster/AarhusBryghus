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
navn varchar(20),
beskrivelse varchar(255),
produktKategori varchar(10) foreign key references ProduktKategorier(navn),
constraint navnKategori primary key (navn, produktKategori)
)

create table PrisKategorier
(
navn varchar(20) primary key
)

create table ProduktPriser
(
pris decimal,
rabat decimal,
produkt varchar(20) foreign key references Produkter(navnKategori),
prisKategori varchar(20) foreign key references PrisKategorier(navn),
produktPrisKategori varchar(20) primary key (produkt, prisKategori)
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
produktPris varchar(20) foreign key references ProduktPriser(produktPrisKategori)
)