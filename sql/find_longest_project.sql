SELECT name, DATEDIFF(month, START_DATE, FINISH_DATE) AS month_count
FROM 
client JOIN project ON project.client_id = client.id 
WHERE DATEDIFF(month, START_DATE, FINISH_DATE) = 
(SELECT MAX(month_count) FROM
(SELECT DATEDIFF(month, START_DATE, FINISH_DATE) as month_count
FROM project));