SELECT 'CREATE DATABASE "homebanking-master"'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'homebanking-master')\gexec
