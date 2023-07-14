SELECT name, COUNT(client_id) AS project_count 
FROM
client JOIN project ON project.client_id = client.id
GROUP BY name 
HAVING  COUNT(client_id) = 
(SELECT MAX(project_count) FROM 
(SELECT COUNT(client_id) AS project_count
FROM project 
GROUP BY client_id));