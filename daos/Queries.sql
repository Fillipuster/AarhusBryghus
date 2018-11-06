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
select sum(pris) as 'Rabat'
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
drop procedure if exists PrisListeForProdukter
go
create procedure PrisListeForProdukter (@event varchar) as
	select pk.navn,	p.navn, pp.pris - pp.rabat as pris, event.navn
	from PrisKategorier pk, Produkter p
	join ProduktPriser pp on p.id = pp.id
	where pk.navn = event.navn
go
EXEC PrisListeForProdukter