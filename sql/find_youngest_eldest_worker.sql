SELECT 
CASE 
WHEN birthday = (SELECT MAX(birthday) FROM worker) THEN 'Youngest'
WHEN birthday = (SELECT MIN(birthday) FROM worker) THEN 'Oldest'
END AS type,
name, birthday,
FROM worker
WHERE birthday = (SELECT MAX(birthday) FROM worker) OR birthday = (SELECT MIN(birthday) FROM worker);