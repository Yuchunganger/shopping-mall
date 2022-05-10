CREATE TABLE IF NOT EXISTS course
(
    id                        INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    teacher                   VARCHAR(128) NOT NULL,
    title                     VARCHAR(128) NOT NULL,
    category                  VARCHAR(64)  NOT NULL,
    cover_image_url           VARCHAR(256) NOT NULL,
    teacher_image_url         VARCHAR(256) NOT NULL,
    course_url                VARCHAR(256) NOT NULL,
    price                     INT          NOT NULL,
    proposal_price            INT,
    proposal_due_time         TIMESTAMP    NOT NULL,
    is_discount               BOOLEAN,
    discount                  INT,
    criteria_num_sold_tickets INT          NOT NULL,
    current_num_sold_tickets  INT          NOT NULL,
    status                    VARCHAR(128),
    description               VARCHAR(1024),
    created_date              TIMESTAMP    NOT NULL,
    last_modified_date        TIMESTAMP    NOT NULL
    );