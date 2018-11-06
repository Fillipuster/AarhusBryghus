use AarhusBryghus
go

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

select AVG(pris) as PrisAVG
from ProduktPriser pp join Salg s on




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

-- Opgave 5
drop trigger if exists sletProdukt
go
create trigger sletProdukt on Produkter after delete as
	print('1')
	if ((select count (*) from Produkter where Produkter.produktKategori = (select produktKategori from deleted)) = 0) begin
		print('2')
		delete from ProduktKategorier where navn = (select produktKategori from deleted)
	end
go

insert into ProduktKategorier values ('test')
insert into Produkter values ('test øl', '7cl, frugtig', 'test') -- Produkter.id starter på 1; 1
select * from ProduktKategorier
select * from Produkter 
delete from Produkter where navn = 'test øl'

select * from ProduktKategorier

select * from Produkter