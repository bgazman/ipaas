
-- Grant usage on the schema to the Keycloak user
GRANT USAGE ON SCHEMA ipaas_auth TO ipaas_keycloak_user;

-- Grant create permissions on the schema
GRANT CREATE ON SCHEMA ipaas_auth TO ipaas_keycloak_user;

-- Optionally, grant all privileges on existing objects in the schema
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA ipaas_auth TO ipaas_keycloak_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA ipaas_auth TO ipaas_keycloak_user;

CREATE SCHEMA IF NOT EXISTS ipaas_auth AUTHORIZATION ipaas_keycloak_user;
