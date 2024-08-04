create table "users" (
                         "is_active" boolean not null,
                         "is_locked" boolean not null,
                         "user_id" bigint primary key not null auto_increment,
                         "date_joined" timestamp(6) without time zone,
                         "email" character varying(255),
                         "gender" character varying(255),
                         "name" character varying(255),
                         "password" character varying(255),
                         "role" character varying(255),
                         "username" character varying(255)
);

create table "authors" (
                                "author_id" bigint primary key not null auto_increment,
                                "date_of_birth" date,
                                "gender" character varying(255),
                                "name" character varying(255)
);

create table "books" (
                         "author_id" bigint,
                         "book_id" bigint primary key not null auto_increment,
                         "publication_year" integer,
                         "blurb" character varying(10000),
                         "cover_image_url" character varying(255),
                         "isbn" character varying(255),
                         "title" character varying(255),
                         foreign key ("author_id") references "authors" ("author_id")
);


create table "genres" (
                          "genre_id" bigint primary key not null auto_increment,
                          "name" character varying(255)
);


create table "comment" (
                           "id" bigint primary key not null auto_increment,
                           "content" character varying(255),
                           "created_at" timestamp(6) without time zone,
                           "book_id" bigint,
                           "comment_author_id" bigint,
                           "parent_id" bigint,
                           foreign key ("parent_id") references "comment" ("id"),
                           foreign key ("book_id") references "books" ("book_id"),
                           foreign key ("comment_author_id") references "users" ("user_id")
);



create table "book_genre" (
                              "book_id" bigint not null,
                              "genre_id" bigint not null,
                              primary key ("book_id", "genre_id"),
                              foreign key ("genre_id") references "genres" ("genre_id"),
                              foreign key ("book_id") references "books" ("book_id")
);






create table "token" (
                         "id" bigint primary key not null auto_increment,
                         "expired" boolean not null,
                         "revoked" boolean not null,
                         "token" character varying(255),
                         "token_type" character varying(255),
                         "user_id" bigint,
                         foreign key ("user_id") references "users" ("user_id")
);


create table "user_favorite_books" (
                                       "book_id" bigint not null,
                                       "user_id" bigint not null,
                                       primary key ("book_id", "user_id"),
                                       foreign key ("book_id") references "books" ("book_id"),
                                       foreign key ("user_id") references "users" ("user_id")
);