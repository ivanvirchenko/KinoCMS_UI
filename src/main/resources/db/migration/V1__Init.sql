create table cinema
(
    id          serial not null,
    description varchar(4000),
    logo_name   varchar(255),
    logo_url    varchar(255),
    name        varchar(255),
    banner_name varchar(255),
    banner_url  varchar(255),
    conditions  varchar(4000),
    seo_id      int,
    city_id     int,
    primary key (id)
);

create table cinema_gallery
(
    cinema_id int not null,
    name      varchar(255),
    url       varchar(255)
);
create table city
(
    id   serial not null,
    name varchar(255),
    primary key (id)
);
create table contact
(
    id          serial not null,
    address     varchar(255),
    cinema_name varchar(255),
    coordinates varchar(255),
    logo_name   varchar(255),
    logo_url    varchar(255),
    primary key (id)
);
create table hall
(
    id          serial not null,
    description varchar(5000),
    logo_name   varchar(255),
    logo_url    varchar(255),
    name        varchar(255),
    banner_name varchar(255),
    banner_url  varchar(255),
    seo_id      int,
    cinema_id   int,
    primary key (id)
);
create table hall_gallery
(
    hall_id int not null,
    name    varchar(255),
    url     varchar(255)
);
create table hall_places
(
    hall_id    int     not null,
    is_taken   boolean not null,
    places_num int     not null,
    rows_num   int     not null
);
create table movie
(
    id          serial not null,
    description varchar(5000),
    logo_name   varchar(255),
    logo_url    varchar(255),
    name        varchar(255),
    end_date    date,
    start_date  date,
    video_link  varchar(255),
    seo_id      int,
    primary key (id)
);
create table movie_gallery
(
    movie_id int not null,
    name     varchar(255),
    url      varchar(255)
);
create table movies_to_types
(
    movie_id int not null,
    type_id  int not null
);
create table movie_session
(
    id        serial not null,
    date      date,
    price     numeric(19, 2),
    time      time,
    cinema_id int,
    hall_id   int,
    movie_id  int,
    primary key (id)
);
create table movie_type
(
    id   serial not null,
    type varchar(255),
    primary key (id)
);
create table news
(
    id          serial  not null,
    description varchar(5000),
    logo_name   varchar(255),
    logo_url    varchar(255),
    name        varchar(255),
    date        date    not null,
    enabled     boolean not null,
    video_link  varchar(255),
    seo_id      int,
    primary key (id)
);
create table news_gallery
(
    news_id int not null,
    name    varchar(255),
    url     varchar(255)
);
create table promotion
(
    id          serial  not null,
    description varchar(5000),
    logo_name   varchar(255),
    logo_url    varchar(255),
    name        varchar(255),
    date        date,
    enabled     boolean not null,
    video_link  varchar(255),
    seo_id      int,
    primary key (id)
);
create table promotion_gallery
(
    promotion_id int not null,
    name         varchar(255),
    url          varchar(255)
);
create table seo
(
    id          serial not null,
    description varchar(3048),
    key_words   varchar(255),
    title       varchar(255),
    url         varchar(255),
    primary key (id)
);
create table users
(
    id          serial not null,
    address     varchar(255),
    birth_day   date,
    card_number varchar(255),
    city        varchar(255),
    email       varchar(255),
    gender      varchar(255),
    language    varchar(255),
    last_name   varchar(255),
    login       varchar(255),
    name        varchar(255),
    password    varchar(255),
    number      varchar(255),
    primary key (id)
);

alter table city
    add constraint UK_qsstlki7ni5ovaariyy9u8y79 unique (name);
alter table movie_type
    add constraint UK_ibukwyhx2yr81uubycfm5w9ku unique (type);
alter table cinema
    add constraint FKd40r0s3rid0kvv7gdp9qjv2jv foreign key (seo_id) references seo;
alter table cinema
    add constraint FK2lxd9v0mo9e6r5aq9qpadm19s foreign key (city_id) references city;
alter table cinema_gallery
    add constraint FKoex5invwtoc7o038w23vuwff foreign key (cinema_id) references cinema;
alter table hall
    add constraint FKcp7hpol78j6nuplox3t2yvdsm foreign key (seo_id) references seo;
alter table hall
    add constraint FKte75ikgkdmhfutuupvx2lhknr foreign key (cinema_id) references cinema;
alter table hall_gallery
    add constraint FKji933efwhyxclu72jc7negaya foreign key (hall_id) references hall;
alter table hall_places
    add constraint FK6pxf8xo8ecoweyb7gh3x0wa4e foreign key (hall_id) references hall;
alter table movie
    add constraint FKtdcmj23y909gt8wmcovn5cmw6 foreign key (seo_id) references seo;
alter table movie_gallery
    add constraint FKawtxffvl7m1misjoao44rp4n8 foreign key (movie_id) references movie;
alter table movies_to_types
    add constraint FKtku7rej9oa6mffqd06tr1t7hu foreign key (type_id) references movie_type;
alter table movies_to_types
    add constraint FKb7iktchfnv5xg5015wm7msyq9 foreign key (movie_id) references movie;
alter table movie_session
    add constraint FK2coe6w77r77uvdbbd4x5xib7j foreign key (cinema_id) references cinema;
alter table movie_session
    add constraint FKog59f4y42c62vpwtmdxcfag1a foreign key (hall_id) references hall;
alter table movie_session
    add constraint FKer8dsjajcvlud03f5165ajl39 foreign key (movie_id) references movie;
alter table news
    add constraint FK6bp62a0a7bs2lgyvp5rmucst2 foreign key (seo_id) references seo;
alter table news_gallery
    add constraint FKkfb2fsc4s7pv1gdfm7funybi7 foreign key (news_id) references news;
alter table promotion
    add constraint FKjfw9k1qi4nkj0cffvokmkg5x0 foreign key (seo_id) references seo;
alter table promotion_gallery
    add constraint FKeqx0mp4fhr9s7j9bl4xjuojsk foreign key (promotion_id) references promotion