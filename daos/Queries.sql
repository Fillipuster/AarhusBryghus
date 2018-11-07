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

-- Opgave 3
drop view if exists ProduktInfo
go
create view ProduktInfo as
	select pk.navn as productCategory, p.navn as productName, count(s.id) as partOfOrders
	from Produkter p join ProduktPriser pp on p.id = pp.produkt
	left join ProduktLinjer pl on pp.id = pl.produktPris
	left join ProduktKategorier pk on pk.navn = p.produktKategori
	left join Salg s on pl.salg = s.id
	group by p.navn, pk.navn
go
select * from ProduktInfo


-- Opgave 4.a
drop procedure if exists PrisListeForProdukter
go
create procedure PrisListeForProdukter (@eventet varchar(20)) as
	select p.navn, pp.pris * (1 - ISNULL(pp.rabat, 0)) as pris
	from ProduktPriser pp
	join Produkter p on pp.produkt = p.id
	where pp.prisKategori = @eventet
go
EXEC PrisListeForProdukter 'bar'


-- Opgave 4.b
drop procedure if exists AddRabat
go
create procedure AddRabat (@produktKategori varchar(10), @rabat decimal(4,3)) as
	update ProduktPriser set rabat = @rabat
	where id in (select pp.id from ProduktPriser pp join Produkter p on pp.produkt = p.id where p.produktKategori = @produktKategori)
go
select p.navn, p.produktKategori, pp.rabat
from Produkter p join ProduktPriser pp on p.id = pp.produkt
go
EXEC AddRabat 'fadøl', 0.05
go
select p.navn, p.produktKategori, pp.rabat
from Produkter p join ProduktPriser pp on p.id = pp.produkt


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