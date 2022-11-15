CREATE TABLE t_file(
	id varchar(36),
	file_codes text not null,
	extensions varchar(5) not null,
	created_by varchar(36) not null,
	created_at timestamp without time zone not null,
	updated_by varchar(36),
	updated_at timestamp without time zone,
	ver int not null default 0,
	is_active boolean not null default true
);
ALTER TABLE t_file ADD CONSTRAINT t_file_pk PRIMARY KEY(id);

CREATE TABLE t_user_role (
    id varchar(36),
    role_code varchar(5) NOT NULL,
    role_name varchar(20) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_user_role ADD CONSTRAINT t_user_role_pk PRIMARY KEY(id);
ALTER TABLE t_user_role ADD CONSTRAINT t_user_role_bk UNIQUE(role_code);
ALTER TABLE t_user_role ADD CONSTRAINT t_user_role_ck UNIQUE(role_code, role_name);

CREATE TABLE t_post_type(
    id varchar(36),
    post_type_name varchar(20) NOT NULL,
    post_type_code varchar(5) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_post_type ADD CONSTRAINT t_post_type_pk PRIMARY KEY(id);
ALTER TABLE t_post_type ADD CONSTRAINT t_post_type_bk UNIQUE(post_type_code);
ALTER TABLE t_post_type ADD CONSTRAINT t_post_type_ck UNIQUE(post_type_code, post_type_name);

CREATE TABLE t_industry (
    id varchar(36),
    industry_name varchar(50) NOT NULL,
    industry_code varchar(5) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_industry ADD CONSTRAINT t_industry_pk PRIMARY KEY(id);
ALTER TABLE t_industry ADD CONSTRAINT t_industry_bk UNIQUE(industry_code);
ALTER TABLE t_industry ADD CONSTRAINT t_industry_ck UNIQUE(industry_code, industry_name);

CREATE TABLE t_position(
    id varchar(36),
    position_name varchar(50) NOT NULL,
    position_code varchar(5) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_position ADD CONSTRAINT t_position_pk PRIMARY KEY(id);
ALTER TABLE t_position ADD CONSTRAINT t_position_bk UNIQUE(position_code);
ALTER TABLE t_position ADD CONSTRAINT t_position_ck UNIQUE(position_code, position_name);

CREATE TABLE t_user(
    id varchar(36),
    fullname varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    pass text NOT NULL,
    company varchar(50) NOT NULL,
    wallet double precision NOT NULL DEFAULT 0,
    is_premium boolean NOT NULL DEFAULT FALSE,
    role_id varchar(36) NOT NULL,
    industry_id varchar(36) NOT NULL,
    position_id varchar(36) NOT NULL,
    file_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_user ADD CONSTRAINT t_user_pk PRIMARY KEY(id);
ALTER TABLE t_user ADD CONSTRAINT t_user_bk UNIQUE(email);
ALTER TABLE t_user ADD CONSTRAINT t_user_ck UNIQUE(email, fullname);
ALTER TABLE t_user ADD CONSTRAINT t_user_role_fk FOREIGN KEY (role_id) REFERENCES t_user_role(id);
ALTER TABLE t_user ADD CONSTRAINT t_user_industry_fk FOREIGN KEY (industry_id) REFERENCES t_industry(id);
ALTER TABLE t_user ADD CONSTRAINT t_user_position_fk FOREIGN KEY (position_id) REFERENCES t_position(id);
ALTER TABLE t_user ADD CONSTRAINT t_user_file_fk FOREIGN KEY (file_id) REFERENCES t_file(id);

CREATE TABLE t_user_verification(
    id varchar(36),
    verification_code varchar(5) NOT NULL,
    user_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_user_verification ADD CONSTRAINT t_user_verification_pk PRIMARY KEY(id);
ALTER TABLE t_user_verification ADD CONSTRAINT t_user_verification_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);

CREATE TABLE t_article(
    id varchar(36),
    article_title varchar(100) NOT NULL,
    article_content text NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_article ADD CONSTRAINT t_article_pk PRIMARY KEY(id);

CREATE TABLE t_post(
    id varchar(36),
    post_title varchar(100) NOT NULL,
    post_content text NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_post ADD CONSTRAINT t_post_pk PRIMARY KEY(id);

CREATE TABLE t_bookmark(
    id varchar(36),
    user_id varchar(36) NOT NULL,
    post_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_bookmark ADD CONSTRAINT t_bookmark_pk PRIMARY KEY(id);
ALTER TABLE t_bookmark ADD CONSTRAINT t_bookmark_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);
ALTER TABLE t_bookmark ADD CONSTRAINT t_bookmark_post_fk FOREIGN KEY(post_id) REFERENCES t_post(id);

CREATE TABLE t_like(
    id varchar(36),
    user_id varchar(36) NOT NULL,
    post_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_like ADD CONSTRAINT t_like_pk PRIMARY KEY(id);
ALTER TABLE t_like ADD CONSTRAINT t_like_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);
ALTER TABLE t_like ADD CONSTRAINT t_like_post_fk FOREIGN KEY(post_id) REFERENCES t_post(id);

CREATE TABLE t_comment(
    id varchar(36),
    comment_content text NOT NULL,
    user_id varchar(36) NOT NULL,
    post_id varchar(36),
    comment_id varchar(36),
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_comment ADD CONSTRAINT t_comment_pk PRIMARY KEY(id);
ALTER TABLE t_comment ADD CONSTRAINT t_comment_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);
ALTER TABLE t_comment ADD CONSTRAINT t_comment_post_fk FOREIGN KEY(post_id) REFERENCES t_post(id);
ALTER TABLE t_comment ADD CONSTRAINT t_comment_comment_fk FOREIGN KEY(comment_id) REFERENCES t_comment(id);

CREATE TABLE t_attachment_post(
    id varchar(36),
    file_id varchar(36) NOT NULL,
    post_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_attachment_post ADD CONSTRAINT t_attachment_post_pk PRIMARY KEY(id);
ALTER TABLE t_attachment_post ADD CONSTRAINT t_attachment_post_file_fk FOREIGN KEY(file_id) REFERENCES t_file(id);
ALTER TABLE t_attachment_post ADD CONSTRAINT t_attachment_post_post_fk FOREIGN KEY(post_id) REFERENCES t_post(id);

CREATE TABLE t_attachment_article(
    id varchar(36),
    file_id varchar(36) NOT NULL,
    post_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_attachment_article ADD CONSTRAINT t_attachment_article_pk PRIMARY KEY(id);
ALTER TABLE t_attachment_article ADD CONSTRAINT t_attachment_article_file_fk FOREIGN KEY(file_id) REFERENCES t_file(id);
ALTER TABLE t_attachment_article ADD CONSTRAINT t_attachment_article_post_fk FOREIGN KEY(post_id) REFERENCES t_post(id);

CREATE TABLE t_poll (
    id varchar(36),
    poll_title varchar(100) NOT NULL,
    end_at timestamp WITHOUT time ZONE NOT NULL,
    post_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_poll ADD CONSTRAINT t_poll_pk PRIMARY KEY(id);
ALTER TABLE t_poll ADD CONSTRAINT t_poll_post_fk FOREIGN KEY(post_id) REFERENCES t_post(id);

CREATE TABLE t_poll_option(
    id varchar(36),
    poll_content text NOT NULL,
    post_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_poll_option ADD CONSTRAINT t_poll_option_pk PRIMARY KEY(id);
ALTER TABLE t_poll_option ADD CONSTRAINT t_poll_option_post_fk FOREIGN KEY(post_id) REFERENCES t_post(id);

CREATE TABLE t_poll_vote (
    id varchar(36),
    poll_option_id varchar(36) NOT NULL,
    user_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_poll_vote ADD CONSTRAINT t_poll_vote_pk PRIMARY KEY(id);
ALTER TABLE t_poll_vote ADD CONSTRAINT t_poll_vote_option_fk FOREIGN KEY(poll_option_id) REFERENCES t_poll_option(id);
ALTER TABLE t_poll_vote ADD CONSTRAINT t_poll_vote_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);

CREATE TABLE t_activity_type(
    id varchar(36),
    activity_type_name varchar(20) NOT NULL,
    activity_type_code varchar(6) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_activity_type ADD CONSTRAINT t_activity_type_pk PRIMARY KEY(id);
ALTER TABLE t_activity_type ADD CONSTRAINT t_activity_type_bk UNIQUE(activity_code);
ALTER TABLE t_activity_type ADD CONSTRAINT t_activity_type_ck UNIQUE(activity_code, activity_name);

CREATE TABLE t_activity(
    id varchar(36),
    title varchar(50) NOT NULL,
    provider varchar(50) NOT NULL,
    activity_location text NOT NULL,
    start_at timestamp WITHOUT time ZONE NOT NULL,
    end_at timestamp WITHOUT time ZONE NOT NULL,
    fee double precision NOT NULL,
    activity_type_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_activity ADD CONSTRAINT t_activity_pk PRIMARY KEY(id);
ALTER TABLE t_activity ADD CONSTRAINT t_activity_activity_type_fk FOREIGN KEY(activity_type_id) REFERENCES t_activity_type(id);

CREATE TABLE t_attachment_activity(
    id varchar(36),
    file_id varchar(36) NOT NULL,
    activity_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_attachment_activity ADD CONSTRAINT t_attachment_activity_pk PRIMARY KEY(id);
ALTER TABLE t_attachment_activity ADD CONSTRAINT t_attachment_activity_file_fk FOREIGN KEY(file_id) REFERENCES t_file(id);
ALTER TABLE t_attachment_activity ADD CONSTRAINT t_attachment_activity_post_fk FOREIGN KEY(activity_id) REFERENCES t_activity(id);

CREATE TABLE t_activity_member (
    id varchar(36),
    is_approved boolean NOT NULL DEFAULT FALSE,
    file_id varchar(36) NOT NULL,
    activity_id varchar(36) NOT NULL,
    user_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_activity_member ADD CONSTRAINT t_activity_member_pk PRIMARY KEY(id);
ALTER TABLE t_activity_member ADD CONSTRAINT t_activity_member_file_fk FOREIGN KEY(file_id) REFERENCES t_file(id);
ALTER TABLE t_activity_member ADD CONSTRAINT t_activity_member_post_fk FOREIGN KEY(activity_id) REFERENCES t_activity(id);
ALTER TABLE t_activity_member ADD CONSTRAINT t_activity_member_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id); 

CREATE TABLE t_income(
    id varchar(36),
    nominal double precision NOT NULL,
    user_id varchar(36) NOT NULL,
    activity_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_income ADD CONSTRAINT t_income_pk PRIMARY KEY(id);
ALTER TABLE t_income ADD CONSTRAINT t_income_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);
ALTER TABLE t_income ADD CONSTRAINT t_income_event_fk FOREIGN KEY(activity_id) REFERENCES t_activity(id);