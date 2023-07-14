SELECT client.name, DATEDIFF(month, START_DATE, FINISH_DATE)*SUM(salary) AS price
FROM client 
JOIN project ON project.client_id = client.id
JOIN project_worker ON project_worker.project_id = project.id 
JOIN worker ON project_worker.worker_id = worker.id 
GROUP BY project_id
ORDER BY price DESC;