use AarhusBryghus
go

/*
Drop Table
*/
drop table if exists ProduktLinjer
drop table if exists Salg
drop table if exists ProduktPriser
drop table if exists PrisKategorier
drop table if exists Produkter
drop table if exists ProduktKategorier
go


/*
Table Creation
*/
create table ProduktKategorier
(
navn varchar(10) primary key
)

create table Produkter
(
id int primary key identity,
navn varchar(20) not null,
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
pris decimal(10,2) not null,
rabat decimal(4,3),
prisKategori varchar(20) foreign key references PrisKategorier(navn) not null,
produkt int foreign key references Produkter(id) not null,

constraint produktPrisKategori unique (produkt, prisKategori),

check (pris > 0)
)

create table Salg
(
id int primary key identity,
dato date not null
)

create table ProduktLinjer
(
salg int foreign key references Salg(id),
antal int not null,
produktPris int foreign key references ProduktPriser(id),
aftaltPris decimal(10,2),

check (antal > 0)
)
go

/*
Test Data
*/
-- Flaskeøl
insert into ProduktKategorier values ('flaskeøl')

insert into Produkter values ('IPA', '7cl, frugtig', 'flaskeøl') -- Produkter.id starter på 1; 1
insert into Produkter values ('Klosterbryg', '7cl, fyldig og crisp', 'flaskeøl') -- 2
insert into Produkter values ('Blondie', '5cl, frisk', 'flaskeøl') -- 3
insert into Produkter values ('Black Monster', '5cl, uhyggelig', 'flaskeøl') -- 4

-- Fadøl
insert into ProduktKategorier values ('fadøl')

insert into Produkter values ('IPA', 'frugtig, fra fad', 'fadøl') -- 5
insert into Produkter values ('Klosterbryg', 'fyldig, fra fad', 'fadøl') -- 6
insert into Produkter values ('Blondie', 'frisk, hvedeøl, fra fad', 'fadøl') -- 7
insert into Produkter values ('Pilsner', 'klassiker, god til pizza, fra fad', 'fadøl') -- 8

-- Priser
insert into PrisKategorier values ('butik')

insert into ProduktPriser values (36.0, 0.05, 'butik', 1) -- 1
insert into ProduktPriser values (36.0, null, 'butik', 2) -- 2
insert into ProduktPriser values (36.0, null, 'butik', 3) -- 3
insert into ProduktPriser values (50.0, null, 'butik', 4) -- 4

insert into PrisKategorier values ('bar')

insert into ProduktPriser values (50.0, null, 'bar', 1) -- 5
insert into ProduktPriser values (50.0, null, 'bar', 2) -- 6
insert into ProduktPriser values (50.0, null, 'bar', 3) -- 7
insert into ProduktPriser values (50.0, null, 'bar', 4) -- 8

insert into ProduktPriser values (30.0, null, 'bar', 5) -- 9
insert into ProduktPriser values (30.0, null, 'bar', 6) -- 10
insert into ProduktPriser values (30.0, null, 'bar', 7) -- 11
insert into ProduktPriser values (30.0, 0.125, 'bar', 8) -- 12

-- Salg
insert into Salg values ('2018-09-24')
insert into Salg values ('2018-10-20')
insert into Salg values ('2018-11-19')
insert into Salg values ('2018-12-02')

-- ProduktLinje
insert into ProduktLinjer values (1, 2, 1, null) -- Salg #1 har købt 2 stk. af ProduktPris #1 til "null" aftalt pris.
insert into ProduktLinjer values (1, 1, 3, null)
insert into ProduktLinjer values (1, 1, 4, 40)

insert into ProduktLinjer values (2, 4, 2, null)
insert into ProduktLinjer values (2, 1, 4, null)

insert into ProduktLinjer values (3, 6, 12, null)

insert into ProduktLinjer values (3, 1, 12, null)
insert into ProduktLinjer values (3, 1, 10, null)
insert into ProduktLinjer values (3, 1, 9, null)
insert into ProduktLinjer values (3, 2, 7, 80.5)
insert into ProduktLinjer values (3, 1, 6, 0) -- God kunde;

insert into ProduktLinjer values (4, 1, 11, 20)

-- Reference Example - Use in the future!
select salg, prisKategori, antal, navn, beskrivelse, pris, produktKategori, rabat, (antal * pris) as total, aftaltPris from ProduktLinjer pl
join ProduktPriser pp on pl.produktPris = pp.id
join Produkter p on pp.produkt = p.id
where salg = 3


-- Opgave 2.a 
select navn, prisKategori, pris
from Produkter p left join ProduktPriser pk on p.id = pk.id
where p.id = 1


-- opgave 2.b
select sum(pris) * 0.05 as 'Rabat'
from ProduktLinjer pl join ProduktPriser pp on pl.produktPris = pp.id
join Salg s on s.id = pl.salg
where s.id = 1


-- opgave 2.c
select sum(pris) as 'Total Pris'
from ProduktLinjer pl join ProduktPriser pp on pl.produktPris = pp.id
join Salg s on s.id = pl.salg
where s.id = 1


-- Opgave 2.d
select sum(antal) as 'Solgt denne måned'
from ProduktLinjer pl join ProduktPriser pp on pl.produktPris = pp.id
Join Salg s on pl.salg = s.id
where MONTH(s.dato) = 11 and YEAR(s.dato) = 2018
GROUP BY pp.id
having sum(antal) > 5

 
-- Opgave 2.e
select p.navn, pris
from Produkter p join ProduktPriser pp on p.id = pp.produkt
join PrisKategorier pk on pp.prisKategori = pk.navn
where pk.navn in ('bar') and pp.pris is null


-- Opgave 2.f
--TODO
select AVG(pris) as PrisAVG
from ProduktPriser pp join ProduktLinjer pl on pp.id = pl.produktPris
join Salg s on pl.salg = s.id
GROUP BY s.id

-- Dette er er fra hjælp af Alexander - husk at man skal også tage aftalt pris med i regnskabet
select avg(TotalPrice)
from (
select sum(pris) as TotalPrice
from ProduktLinjer pl join ProduktPriser pp on pl.produktPris = pp.id
join Salg s on s.id = pl.salg
) as T




-- Opgave 3
select pk.navn as ProduktKategori, p.navn as Produkt, COUNT(s.id) as id
from Produkter p join ProduktPriser pp on p.id = pp.produkt
left join ProduktLinjer pl on pp.id = pl.produktPris
left join ProduktKategorier pk on pk.navn = p.produktKategori
left join Salg s on pl.salg = s.id
GROUP BY p.navn, pk.navn

-- Opgave 4
Create procedure PrislisteForProdukter
as
select * from Produkter
go