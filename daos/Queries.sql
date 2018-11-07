use AarhusBryghus
go

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
select p.navn, pris, p.produktKategori
from ProduktPriser pp join Produkter p on pp.produkt = p.id
where p.id not in (select Produkter.id from Produkter join 
ProduktPriser on Produkter.id = ProduktPriser.produkt 
where prisKategori = 'bar')


-- Opgave 2.f
select avg(totalPrice) as averagePrice from
(
	select sum(antal * pris) as totalPrice
	from ProduktLinjer pl
	join ProduktPriser pp on pl.produktPris = pp.id
	join Salg s on s.id = pl.salg
	group by s.id
) as t