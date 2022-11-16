CREATE TABLE t_file(
	id text,
	file_codes text not null,
	extensions text not null,
	created_by text not null,
	created_at timestamp without time zone not null,
	updated_by text,
	updated_at timestamp without time zone,
	ver int not null default 0,
	is_active boolean not null default true
);
ALTER TABLE t_file ADD CONSTRAINT t_file_pk PRIMARY KEY(id);

CREATE TABLE t_role (
    id text,
    role_code text NOT NULL,
    role_name text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_role ADD CONSTRAINT t_role_pk PRIMARY KEY(id);
ALTER TABLE t_role ADD CONSTRAINT t_role_bk UNIQUE(role_code);
ALTER TABLE t_role ADD CONSTRAINT t_role_ck UNIQUE(role_code, role_name);

CREATE TABLE t_post_type(
    id text,
    post_type_name text NOT NULL,
    post_type_code text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_post_type ADD CONSTRAINT t_post_type_pk PRIMARY KEY(id);
ALTER TABLE t_post_type ADD CONSTRAINT t_post_type_bk UNIQUE(post_type_code);
ALTER TABLE t_post_type ADD CONSTRAINT t_post_type_ck UNIQUE(post_type_code, post_type_name);

CREATE TABLE t_industry (
    id text,
    industry_name text NOT NULL,
    industry_code text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_industry ADD CONSTRAINT t_industry_pk PRIMARY KEY(id);
ALTER TABLE t_industry ADD CONSTRAINT t_industry_bk UNIQUE(industry_code);
ALTER TABLE t_industry ADD CONSTRAINT t_industry_ck UNIQUE(industry_code, industry_name);

CREATE TABLE t_position(
    id text,
    position_name text NOT NULL,
    position_code text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_position ADD CONSTRAINT t_position_pk PRIMARY KEY(id);
ALTER TABLE t_position ADD CONSTRAINT t_position_bk UNIQUE(position_code);
ALTER TABLE t_position ADD CONSTRAINT t_position_ck UNIQUE(position_code, position_name);

CREATE TABLE t_user(
    id text,
    fullname text NOT NULL,
    email text NOT NULL,
    pass text NOT NULL,
    company text NOT NULL,
    is_premium boolean NOT NULL DEFAULT FALSE,
    role_id text NOT NULL,
    industry_id text NOT NULL,
    position_id text NOT NULL,
    file_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_user ADD CONSTRAINT t_user_pk PRIMARY KEY(id);
ALTER TABLE t_user ADD CONSTRAINT t_user_bk UNIQUE(email);
ALTER TABLE t_user ADD CONSTRAINT t_user_ck UNIQUE(email, fullname);
ALTER TABLE t_user ADD CONSTRAINT t_role_fk FOREIGN KEY (role_id) REFERENCES t_role(id);
ALTER TABLE t_user ADD CONSTRAINT t_user_industry_fk FOREIGN KEY (industry_id) REFERENCES t_industry(id);
ALTER TABLE t_user ADD CONSTRAINT t_user_position_fk FOREIGN KEY (position_id) REFERENCES t_position(id);
ALTER TABLE t_user ADD CONSTRAINT t_user_file_fk FOREIGN KEY (file_id) REFERENCES t_file(id);

CREATE TABLE t_article(
    id text,
    article_title text NOT NULL,
    article_content text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_article ADD CONSTRAINT t_article_pk PRIMARY KEY(id);

CREATE TABLE t_post(
    id text,
    post_title text NOT NULL,
    post_content text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_post ADD CONSTRAINT t_post_pk PRIMARY KEY(id);

CREATE TABLE t_bookmark(
    id text,
    user_id text NOT NULL,
    post_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_bookmark ADD CONSTRAINT t_bookmark_pk PRIMARY KEY(id);
ALTER TABLE t_bookmark ADD CONSTRAINT t_bookmark_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);
ALTER TABLE t_bookmark ADD CONSTRAINT t_bookmark_post_fk FOREIGN KEY(post_id) REFERENCES t_post(id);

CREATE TABLE t_like(
    id text,
    user_id text NOT NULL,
    post_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_like ADD CONSTRAINT t_like_pk PRIMARY KEY(id);
ALTER TABLE t_like ADD CONSTRAINT t_like_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);
ALTER TABLE t_like ADD CONSTRAINT t_like_post_fk FOREIGN KEY(post_id) REFERENCES t_post(id);

CREATE TABLE t_comment(
    id text,
    comment_content text NOT NULL,
    user_id text NOT NULL,
    post_id text,
    comment_id text,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_comment ADD CONSTRAINT t_comment_pk PRIMARY KEY(id);
ALTER TABLE t_comment ADD CONSTRAINT t_comment_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);
ALTER TABLE t_comment ADD CONSTRAINT t_comment_post_fk FOREIGN KEY(post_id) REFERENCES t_post(id);
ALTER TABLE t_comment ADD CONSTRAINT t_comment_comment_fk FOREIGN KEY(comment_id) REFERENCES t_comment(id);

CREATE TABLE t_attachment_post(
    id text,
    file_id text NOT NULL,
    post_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_attachment_post ADD CONSTRAINT t_attachment_post_pk PRIMARY KEY(id);
ALTER TABLE t_attachment_post ADD CONSTRAINT t_attachment_post_file_fk FOREIGN KEY(file_id) REFERENCES t_file(id);
ALTER TABLE t_attachment_post ADD CONSTRAINT t_attachment_post_post_fk FOREIGN KEY(post_id) REFERENCES t_post(id);

CREATE TABLE t_attachment_article(
    id text,
    file_id text NOT NULL,
    article_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_attachment_article ADD CONSTRAINT t_attachment_article_pk PRIMARY KEY(id);
ALTER TABLE t_attachment_article ADD CONSTRAINT t_attachment_article_file_fk FOREIGN KEY(file_id) REFERENCES t_file(id);
ALTER TABLE t_attachment_article ADD CONSTRAINT t_attachment_article_article_fk FOREIGN KEY(article_id) REFERENCES t_article(id);

CREATE TABLE t_poll (
    id text,
    poll_title text NOT NULL,
    end_at timestamp WITHOUT time ZONE NOT NULL,
    post_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_poll ADD CONSTRAINT t_poll_pk PRIMARY KEY(id);
ALTER TABLE t_poll ADD CONSTRAINT t_poll_post_fk FOREIGN KEY(post_id) REFERENCES t_post(id);

CREATE TABLE t_poll_option(
    id text,
    poll_content text NOT NULL,
    poll_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_poll_option ADD CONSTRAINT t_poll_option_pk PRIMARY KEY(id);
ALTER TABLE t_poll_option ADD CONSTRAINT t_poll_option_poll_fk FOREIGN KEY(poll_id) REFERENCES t_poll(id);

CREATE TABLE t_poll_vote (
    id text,
    poll_option_id text NOT NULL,
    user_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_poll_vote ADD CONSTRAINT t_poll_vote_pk PRIMARY KEY(id);
ALTER TABLE t_poll_vote ADD CONSTRAINT t_poll_vote_option_fk FOREIGN KEY(poll_option_id) REFERENCES t_poll_option(id);
ALTER TABLE t_poll_vote ADD CONSTRAINT t_poll_vote_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);

CREATE TABLE t_activity_type(
    id text,
    activity_type_name text NOT NULL,
    activity_type_code text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_activity_type ADD CONSTRAINT t_activity_type_pk PRIMARY KEY(id);
ALTER TABLE t_activity_type ADD CONSTRAINT t_activity_type_bk UNIQUE(activity_type_code);
ALTER TABLE t_activity_type ADD CONSTRAINT t_activity_type_ck UNIQUE(activity_type_code, activity_type_name);

CREATE TABLE t_activity(
    id text,
    title text NOT NULL,
    provider text NOT NULL,
    activity_location text NOT NULL,
    start_at timestamp WITHOUT time ZONE NOT NULL,
    end_at timestamp WITHOUT time ZONE NOT NULL,
    fee double precision NOT NULL,
    activity_type_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_activity ADD CONSTRAINT t_activity_pk PRIMARY KEY(id);
ALTER TABLE t_activity ADD CONSTRAINT t_activity_activity_type_fk FOREIGN KEY(activity_type_id) REFERENCES t_activity_type(id);

CREATE TABLE t_attachment_activity(
    id text,
    file_id text NOT NULL,
    activity_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_attachment_activity ADD CONSTRAINT t_attachment_activity_pk PRIMARY KEY(id);
ALTER TABLE t_attachment_activity ADD CONSTRAINT t_attachment_activity_file_fk FOREIGN KEY(file_id) REFERENCES t_file(id);
ALTER TABLE t_attachment_activity ADD CONSTRAINT t_attachment_activity_post_fk FOREIGN KEY(activity_id) REFERENCES t_activity(id);

CREATE TABLE t_activity_member (
    id text,
    activity_id text NOT NULL,
    user_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_activity_member ADD CONSTRAINT t_activity_member_pk PRIMARY KEY(id);
ALTER TABLE t_activity_member ADD CONSTRAINT t_activity_member_post_fk FOREIGN KEY(activity_id) REFERENCES t_activity(id);
ALTER TABLE t_activity_member ADD CONSTRAINT t_activity_member_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id); 

CREATE TABLE t_payment_activity(
    id text,
    nominal double precision NOT NULL,
    is_approved boolean NOT NULL DEFAULT FALSE,
    file_id text NOT NULL,
    user_id text NOT NULL,
    activity_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_payment_activity ADD CONSTRAINT t_payment_activity_pk PRIMARY KEY(id);
ALTER TABLE t_payment_activity ADD CONSTRAINT t_payment_activity_file_fk FOREIGN KEY(file_id) REFERENCES t_file(id);
ALTER TABLE t_payment_activity ADD CONSTRAINT t_payment_activity_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);
ALTER TABLE t_payment_activity ADD CONSTRAINT t_payment_activity_activity_fk FOREIGN KEY(activity_id) REFERENCES t_activity(id);

CREATE TABLE t_payment_premium(
    id text,
    nominal double precision NOT NULL,
    is_approved boolean NOT NULL DEFAULT FALSE,
    file_id text NOT NULL,
    user_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_payment_premium ADD CONSTRAINT t_payment_premium_pk PRIMARY KEY(id);
ALTER TABLE t_payment_premium ADD CONSTRAINT t_payment_premium_file_fk FOREIGN KEY(file_id) REFERENCES t_file(id);
ALTER TABLE t_payment_premium ADD CONSTRAINT t_payment_premium_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);

======= DML =======
INSERT INTO t_role (id,role_code,role_name,created_by,created_at) VALUES
('883bd334-9dbc-4f13-aa3c-d4ab404ee735','SYS','System',1,now()),
('d58091c0-aade-4160-a97f-55ef77dc9682','SA','Super Admin',1,now()),
('9e4aac7a-e0d6-409b-8c55-a99decc5167a','ADM','Admin',1,now()),
('85b2bed6-104e-4f17-9f94-8aede7dd18cb','MMB','Member',1,now());

INSERT INTO t_post_type (id,post_type_code,post_type_name,created_by,created_at) VALUES
('a572d04c-2da6-4703-8dc8-82b8384e3d60','REG','Regular',1,now()),
('7d0a84c9-ede1-4733-a28f-9b286e59e9e3','POL','Polling',1,now()),
('dbcd172a-7d8e-4852-9de2-41dc2f7fcfd3','PRE','Premium',1,now());

INSERT INTO t_activity_type (id,activity_type_code,activity_type_name,created_by,created_at) VALUES
('e4cd6ea9-2363-49c9-bc36-264359bf4e49','EVN','Event',1,now()),
('9e8da04e-acc8-45a2-af5a-573a87fccfbd','CRS','Course',1,now());