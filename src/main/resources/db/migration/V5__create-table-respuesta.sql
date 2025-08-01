CREATE TABLE answers (
    id bigint not null auto_increment,
    mensaje varchar(255) not null,
    topico bigint not null,
    fechaCreacion datetime not null,
    autor varchar(255) not null,
    solucion bigint not null,
    primary key(id),

    constraint fk_respuesta_topico_id foreign key (topico) references topics(id),
    constraint fk_respuesta_solucion_id foreign key (solucion) references users(id)
);
