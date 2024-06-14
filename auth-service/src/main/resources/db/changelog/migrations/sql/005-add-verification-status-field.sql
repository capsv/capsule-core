ALTER TABLE auth.person
    ADD COLUMN is_confirm boolean NOT NULL DEFAULT false;