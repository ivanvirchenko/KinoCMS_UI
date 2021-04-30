INSERT INTO city (name)
values ('Киев'),
       ('Одесса'),
       ('Харьков');

INSERT INTO movie_type (type)
values ('2D'),
       ('3D'),
       ('IMAX');

INSERT INTO seo (description, key_words, title, url)
values ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com'),
       ('description', 'keyword, keyword, keyword, keyword, keyword', 'The seo', 'http//:8080/www.google.com');

INSERT INTO movie (description, logo_name, logo_url, name, end_date, start_date, video_link, seo_id)
VALUES ( 'Реальна історія в’язня Гуантанамо, який провів за ґратами 14 років. У центрі сюжету мавританець Мохаммед Ульд Слахі (Тагар Рагім). Влада США підозрює його у вербуванні терористів під час терактів 11 вересня. Без офіційних звинувачень і можливості захисту в суді, Слахі запроторюють до в’язниці. Лише через 6 років він отримує право на адвоката, і у боротьбу проти урядової машини вступають адвокатка Ненсі Голландер (Джоді Фостер) та її помічниця Тері Дункан (Шейлін Вудлі). Завдяки їхній титанічній праці вдасться розкрити шокуючу істину та довести, що людський дух не стримають ніякі ґрати. Фільм засновано на автобіографічній книзі «Щоденник Гуантанамо».'
       , 'Mavritanec.jpeg'
       , '/movies/Mavritanec.jpeg'
       , 'Мавританець'
       , date(now())
       , date(now() + interval '14 day')
       , 'https://www.youtube.com/embed/-aRNcSi3_h0'
       , 1),

       ( 'Парочка улюблених мультяшних ворогів повертається на великі екрани в анімації «Том і Джеррі» від режисера Тіма Сторі. Коли Джеррі переїжджає в найдорожчий готель Нью-Йорка напередодні «весілля століття», весільна розпорядниця змушена найняти Тома, щоб здихатись від миші. Та війна між цими двома загрожує знищенням її кар''єри, весілля і, можливо, самого готелю. Але невдовзі постає ще більша проблема: диявольськи амбітний колега планує змову проти них трьох.'
       , 'Tom$Jerry.jpg'
       , '/movies/Tom$Jerry.jpg'
       , 'Том і Джеррі'
       , date(now())
       , date(now() + interval '14 day')
       , 'https://www.youtube.com/embed/HAfBOQfJbYo'
       , 2),

       ( 'Побудувавши успішну юридичну кар''єру, Марла майстерно користується людьми, прикриваючись турботою про них. Вона звикла безкарно обдирати своїх багатих клієнтів за рахунок рідкісної чарівності і чималої частки цинізму. Але її нова підопічна - не така проста, як здається, і солодке життя головної героїні скоро перетвориться на гру на виживання...'
       , 'aferistka.jpg'
       , '/movies/aferistka.jpg'
       , 'Аферистка'
       , date(now() + interval '2 day')
       , date(now() + interval '14 day')
       , 'https://www.youtube.com/embed/j3QgakCnxvA'
       , 3);