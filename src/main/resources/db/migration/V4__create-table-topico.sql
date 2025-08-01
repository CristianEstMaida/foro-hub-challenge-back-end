CREATE TABLE topics (
    id bigint not null auto_increment,
    titulo varchar(255) not null,
    mensaje varchar(255) not null,
    fecha_creacion datetime not null,
    status varchar(50),
    autor bigint not null,
    curso bigint not null,
    primary key(id),

    constraint fk_topico_autor_id foreign key (autor) references users(id),
    constraint fk_topico_curso_id foreign key (curso) references courses(id)
);