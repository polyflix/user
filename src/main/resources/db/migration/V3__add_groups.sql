CREATE TABLE groups
(
    id    UUID NOT NULL,
    name  VARCHAR(255),
    slug  VARCHAR(255),
    owner UUID NOT NULL,
    CONSTRAINT pk_groups PRIMARY KEY (id)
);

CREATE TABLE groups_users
(
    group_id UUID NOT NULL,
    user_id  UUID NOT NULL,
    CONSTRAINT pk_groups_users PRIMARY KEY (group_id, user_id)
);

ALTER TABLE groups
    ADD CONSTRAINT uc_groups_slug UNIQUE (slug);

ALTER TABLE groups
    ADD CONSTRAINT FK_GROUPS_ON_OWNER FOREIGN KEY (owner) REFERENCES users (id);

ALTER TABLE groups_users
    ADD CONSTRAINT fk_grouse_on_group_entity FOREIGN KEY (group_id) REFERENCES groups (id);

ALTER TABLE groups_users
    ADD CONSTRAINT fk_grouse_on_user_entity FOREIGN KEY (user_id) REFERENCES users (id);