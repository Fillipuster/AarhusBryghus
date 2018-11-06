-- Reference Example - Use in the future!
-- TODO: Remove me before submitting report;
select salg, prisKategori, antal, navn, beskrivelse, pris, produktKategori, rabat, (antal * pris) as total, aftaltPris from ProduktLinjer pl
join ProduktPriser pp on pl.produktPris = pp.id
join Produkter p on pp.produkt = p.id
where salg = 3

-- Opgave 2.a 
select navn, produktKategori, pris, rabat, prisKategori from Produkter p
join ProduktPriser pp on pp.produkt = p.id
where p.id = 1

-- opgave 2.b
select s.id as salgId, sum(rabat * (antal * pris)) as discountSavings from ProduktLinjer pl
join Salg s on pl.salg = s.id
join ProduktPriser pp on pp.produkt = pl.produktPris
where rabat > 0
group by s.id

-- opgave 2.c
select sum(antal * pris) as totalPrice
from ProduktLinjer pl join ProduktPriser pp on pl.produktPris = pp.id
join Salg s on s.id = pl.salg
where s.id = 3

-- Opgave 2.d
select sum(antal) as monthSale
from ProduktLinjer pl join ProduktPriser pp on pl.produktPris = pp.id
Join Salg s on pl.salg = s.id
where month(s.dato) = 11 and year(s.dato) = 2018
group by pp.id
having sum(antal) > 5
 
-- Opgave 2.e
-- TODO: Tjek op på om ikke der menes de produkter som ikke er med i en ProduktPris record i PrisKategori 'bar'.
select p.navn, pris
from Produkter p join ProduktPriser pp on p.id = pp.produkt
join PrisKategorier pk on pp.prisKategori = pk.navn
where pk.navn in ('bar') and pp.pris is null

-- Opgave 2.f
select avg(totalPrice) as averagePrice from
(
	select sum(antal * pris) as totalPrice
	from ProduktLinjer pl
	join ProduktPriser pp on pl.produktPris = pp.id
	join Salg s on s.id = pl.salg
	group by s.id
) as t

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