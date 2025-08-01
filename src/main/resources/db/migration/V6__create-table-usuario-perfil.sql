CREATE TABLE usuario_perfil (
    id bigint not null,
    perfiles bigint not null,
    primary key(id, perfiles),

    constraint fk_usuario_id foreign key(id) references users(id),
    constraint fk_perfil_id foreign key(perfiles) references perfil(id)
);