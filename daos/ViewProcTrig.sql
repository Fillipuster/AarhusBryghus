use AarhusBryghus
go

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
	-- Test
select * from ProduktInfo


-- Opgave 4.a
drop procedure if exists PrisListeForProdukter
go
create procedure PrisListeForProdukter (@eventet varchar(20)) as
	select p.navn, pp.pris * (1 - isnull(pp.rabat, 0)) as pris
	from ProduktPriser pp
	join Produkter p on pp.produkt = p.id
	where pp.prisKategori = @eventet
go
	-- Test
exec PrisListeForProdukter 'bar'


-- Opgave 4.b
drop procedure if exists AddRabat
go
create procedure AddRabat (@produktKategori varchar(10), @rabat decimal(4,3)) as
	update ProduktPriser set rabat = @rabat
	where id in (select pp.id from ProduktPriser pp join Produkter p on pp.produkt = p.id where p.produktKategori = @produktKategori)
go
	-- Test
select p.navn, p.produktKategori, pp.rabat from Produkter p join ProduktPriser pp on p.id = pp.produkt
go
exec AddRabat 'fadøl', 0.05
go
select p.navn, p.produktKategori, pp.rabat from Produkter p join ProduktPriser pp on p.id = pp.produkt


-- Opgave 5
drop trigger if exists SletProdukt
go
create trigger SletProdukt on Produkter after delete as
    if ((select count (*) from Produkter where Produkter.produktKategori = (select produktKategori from deleted)) = 0) begin
        delete from ProduktKategorier where navn = (select produktKategori from deleted)
    end
go
	-- Test
insert into ProduktKategorier values ('test')
insert into Produkter values ('test øl', '7cl, frugtig', 'test')
select * from Produkter p join ProduktKategorier pk on p.produktKategori = pk.navn
delete from Produkter where navn = 'test øl'
select * from Produkter p join ProduktKategorier pk on p.produktKategori = pk.navn