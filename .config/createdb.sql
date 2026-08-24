SELECT 'CREATE DATABASE "homebanking-prod"'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'homebanking-prod')\gexec
