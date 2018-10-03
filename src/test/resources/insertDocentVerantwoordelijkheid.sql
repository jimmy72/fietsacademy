insert into docentenverantwoordelijkheden(verantwoordelijkheidid, docentid)
values (
	(select id from verantwoordelijkheden where naam='test'),
	(select id from docenten where voornaam='testM'));