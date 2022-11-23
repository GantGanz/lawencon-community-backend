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
    user_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_article ADD CONSTRAINT t_article_pk PRIMARY KEY(id);
ALTER TABLE t_article ADD CONSTRAINT t_article_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);

CREATE TABLE t_post(
    id text,
    post_title text NOT NULL,
    post_content text NOT NULL,
    post_type_id text NOT NULL,
    user_id text NOT NULL,
    created_by text NOT NULL,
    created_at timestamp WITHOUT time ZONE NOT NULL,
    updated_by text,
    updated_at timestamp WITHOUT time ZONE,
    ver int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);
ALTER TABLE t_post ADD CONSTRAINT t_post_pk PRIMARY KEY(id);
ALTER TABLE t_post ADD CONSTRAINT t_post_post_type_fk FOREIGN KEY(post_type_id) REFERENCES t_post_type(id);
ALTER TABLE t_post ADD CONSTRAINT t_post_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);

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
ALTER TABLE t_bookmark ADD CONSTRAINT t_bookmark_ck UNIQUE(user_id, post_id);

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
ALTER TABLE t_like ADD CONSTRAINT t_like_ck UNIQUE(user_id, post_id);

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
    user_id text NOT NULL,
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
ALTER TABLE t_activity ADD CONSTRAINT t_activity_user_fk FOREIGN KEY(user_id) REFERENCES t_user(id);

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

--======= DML =======--
INSERT INTO t_role (id,role_code,role_name,created_by,created_at) VALUES
('883bd334-9dbc-4f13-aa3c-d4ab404ee735','SYS','System','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('d58091c0-aade-4160-a97f-55ef77dc9682','SA','Super Admin','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('9e4aac7a-e0d6-409b-8c55-a99decc5167a','ADM','Admin','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('85b2bed6-104e-4f17-9f94-8aede7dd18cb','MMB','Member','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now());

INSERT INTO t_post_type (id,post_type_code,post_type_name,created_by,created_at) VALUES
('a572d04c-2da6-4703-8dc8-82b8384e3d60','REG','Regular','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('7d0a84c9-ede1-4733-a28f-9b286e59e9e3','POL','Polling','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('dbcd172a-7d8e-4852-9de2-41dc2f7fcfd3','PRE','Premium','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now());

INSERT INTO t_activity_type (id,activity_type_code,activity_type_name,created_by,created_at) VALUES
('e4cd6ea9-2363-49c9-bc36-264359bf4e49','EVN','Event','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('9e8da04e-acc8-45a2-af5a-573a87fccfbd','CRS','Course','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now());

INSERT INTO t_industry (id,industry_name,industry_code,created_by,created_at) VALUES
('4eb0b229-953a-41f9-980f-0fa188f76b9b','Information Technology','IT','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now());

INSERT INTO t_position (id,position_name,position_code,created_by,created_at) VALUES
('7de4273d-3306-4f4c-91c2-3512a73839f7','Marketing','MKT','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('21a18ccc-8774-42d2-9174-2b4d881863c9','DevOps','DVO','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('ddf22d06-dd27-4da1-b759-787ef74d3e43','Quality Assurance','QA','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('2da44488-608f-4805-b74f-d49f035968bb','Reviewer','RVR','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('60b25d58-b163-4ca4-8896-2ce00df62bc9','Human Resource','HR','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('7f93fadf-024b-4f0f-8ac6-208fdfc3b5cc','UI/UX Design','UIXD','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('834705d7-e8fd-4b78-ac32-6418f4b44e20','Front-End Developer','FED','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('8569b00f-ecd8-4f6a-9d18-ee18e41cc55e','Back-End Developer','BED','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('ca0f691f-b583-4f33-954e-a7060613171d','System','SYS','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('1e8f8cae-3272-4721-9fdd-893681f3bcce','Admin','ADM','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now());

INSERT INTO t_file (id,file_codes,extensions,created_by,created_at) VALUES
('d45027a8-a2e2-46a9-b2c2-4f4fd2310d7c','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','png','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('2c77dd06-6557-48b2-a0e0-3d8245dd40f9','iVBORw0KGgoAAAANSUhEUgAAAgAAAAIACAYAAAD0eNT6AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAOxAAADsQBlSsOGwAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAACAASURBVHic7N13mFTV4cbx78xsL2wDREC6gnSsCAIqAvbuz957ixq7iYotNtTEEjUmNuw1NhQ7VlRQAoiiIKKCAltZtu/O/P64rCJ1d8+5c+fOfT/PM4+EMOeemZ2d895TQUREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREQkiEJeV0BERAAoAA4BxgN9gRiwBJgGPLr6zyIiIpIEwsDuwONADU6jv75HDXAtkOpNNa3IAfYEzgHOAPYBCj2tkYiISJx1B64CFrHhRn99jylAugf1NdERuA+oZd3XUwc8A4z0rHYiIiIuywAOB94Ammhdw7/m40n8M3y7E7CClr2uT4HDgBRPaioiImLZMOAOoIS2N/prP66L6ytomxFAJa1/bYuBC4G8+FdZRETETB5wKvAh9hr9tR+nxe3VtN4QzANPJc7QQd84111ERKRVwsDOOI1WFe41/M2PemBsXF5Z62wF/Iq919kEvIwzWVJERCRhdAEuARbifqO/9qMCGOj+S2yxbjhd+G693pnAsfh7NYSIiPhYOnAozp1pI/Fv+Nd8fI8z095rXXDqEo/XvBSYiJYRiohInAwAbqTlM9vj9fgMyHLxdW9KB+Cr9dTL7YfmCYiIiGtCOMv3ZuJ9Q7+xh1fLAwuAWW2or81HI/As2k9AREQsyQNew/vGvaWP6915GzYoF5huqe62HtpPQEREjGTgNCZeN2itfZzgxpuxHpnAu3F6TW15aD8BERFpk9vwvhFry6Me2M2F92NNacCrCfBaW/KoAC5HPQIiItICXXEaUq8br7Y+yoCtrb8rjgjO/v1ev8bWPqbhDFmIiIhs0AV432CZPhZif3lgGHgoAV5bWx9TV78GERGR9fLjHe76Hh/jjNXbclcCvCbTx8kW3w8REUky7xHnhikcJrZ9v1Q3yn4KO8sDb4z3e+LSY4GF90JERJLUNOLUIPXoFIlNPCE39sPTHWOxaZvHLjgs243r/M3w/fhLvN6P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3q1QuJn719qYVRVw27mwYql3dQiFnOV9O4yD7ce6t53zOeOToY3coGR4cQoAIuKdWAy++tQZH3frzji/PZx7q7NjYSJY9pMTAqrj3AOS395p8EfsFZ/3QgEg4SkAiEhi+Ok7eO8FmPGuc7iODTl5cO4k6NTdTnm2LJgDd13i/nG7KakwaCfYYXfov4N7Sx7XRwEg4SV2ADhnvNnzTetnen0RL/k1oNuaJ5CZDefcnLhj29OnwmO3ulN2p+5Oo7/THk4IcoP596Ov21BNAhQRsa2oE+x3knM4z8dTYNqLULa8dWWkZzoT/hK18YfVywOXwhtP2CkvK9fp4h8+wVntIK5SABARcUtb9xPILYBTr4Ye/dyvo6l9jofipW1fHhgKQd9hMHwPGDwCUtOsVk82TAFARMRt69tPYM4n646fp6U7B/vsdax73d62NS8PrK2GeZ+3/HmFm8Hw8c7r9XplQ0ApAIiIxFPzfgLVlc6qgfISIOYMG/Qa6N6SNjelpjk7E06Z7PRyNNRv+N8NHgk7TYCthtk/wEhaRQFARMQLWbnOYT7JIhxxhgNG7Qsz3oHvZsPKUuf/69DZ6eYfOtqbTYxkvRQARETEnrwiZ97D2EO9rolsgk4DFBERCSAFABERkQBSABAREQkgBQAREZEAUgAQEREJIAUAERGRAFIAEBERCSAFABERkQBSABAREQkg7QToNq/PM/f6+iIikpDUAyAiIhJACgAiIiIBpAAgIiISQAoAIiIiAaQAICIiEkAKACIiIgGkACAiIhJACgAiIiIBpAAgIiISQAoAIiIiAaQAICIiEkAKACIiIgGkACAiIhJACgAiIiIBpAAgIiISQCleVyDpnTPe7Pl3vuHt9UWSmde/X36/vviaegBEREQCSAFAREQkgBQAREREAkgBQEREJIAUAERERAJIAUBERCSAFABEREQCSAFAREQkgBQAREREAkgBQEREJIAUAERERAJIAUBERCSAFABEREQCSAFAREQkgBQAREREAijkdQUsiBk9W+dhi4gE0znjTUvwdRuqHgAREZEAUgAQEREJIAUAERGRAFIAEBERCSAFABERkQBSABAREQkgBQAREZEAUgAQEREJIAUAERGRAFIAEBERCSAFABERkQBSABAREQkgBQAREZEAUgAQEREJIAUAERGRAErxugJJz/S86Tvf8Pb6IuIdr3///X592Sj1AIiIiASQAoCIiEgAKQCIiIgEkAKAiIhIACkAiIiIBJACgIiISAApAIiIiASQAoCIiEgAKQCIiIgEkAKAiIhIACkAiIiIBJACgIiISAApAIiIiASQAoCIiEgAKQCIiIgEUMjrClgQM3q2zpsWEQmmc8abluDrNlQ9ACIiIgGkACAiIhJACgAiIiIBpAAgIiISQAoAIiIiAaQAICIiEkAKACIiIgGkACAiIhJACgAiIiIBpAAgIiISQAoAIiIiAaQAICIiEkAKACIiIgGkACAiIhJACgAiIiIB5OuzjFeLeV0BEREJJF+3oeoBEBERCSAFABERkQBSABAREQkgBQAREZEAUgAQEREJIAUAERGRAFIAEBERCSAFABERkQBSABAREQkgBQAREZEAUgAQERHk9eY2AAAPnklEQVQJIAUAERGRAFIAEBERCSAFABERkQBSABAREQmgFK8r4LUZC5Z4XQUREfHAdn26eF0FT6kHQEREJIAUAERERAJIAUBERCSAFABEREQCSAFAREQkgBQAREREAkgBQEREJIAUAERERAJIAUBERCSAFABEREQCSAFAREQkgBQAREREAkgBQEREJIAUAERERAJIAUBERCSAUryugIi4x/S88xkLlliqiYgkGvUAiIiIBJACgIiISAApAIiIiASQAoCIiEgAKQCIiIgEkAKAiIhIACkAiIiIBFAyBIB6kyc3NDTYqoeIiPhEfb1R0wFQZ6MeXkqGALDK5Mk1VVW26iEiIj5RtarStIiVNurhpWQIAEY/hIqKclv1EBERn6isqDAuwkY9vJQMAcCoB+CnxYts1UNERHzixx+Mv/vVA5AAfjF58g8LF9iqh4iI+MTiRQtNi/jVRj28lAwB4FuTJ8+d9YWteoiIiE/M/mKGaRHzbdTDS8kQAIx+CDOmf0wsFrNVFxERSXCxWIyZn35iWowCQAL4xuTJpSXFfDV7lq26iIhIgpvz5UzKy0pNi/F9AEjxugIWfAlEMQgzrz7/DAOHDLNXI5EEMWPBEq+rIJJwXv3vs6ZFRAHf3zkmQw9AMTDHpICpr75IdbX2AxARSXbVVat489WXTIv5EjDuQvBaMgQAgHdMnryyvJwXnnzMVl1ERCRBPfvYI6w03wPgbRt18ZoCwGqT/32PegFERJJY1apKHn3gXzaKetdGIV5LlgDwFmC0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','png','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now());

INSERT INTO t_user (id,fullname,email,pass,company,role_id,industry_id,position_id,file_id,created_by,created_at) VALUES
('02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9','System','System','system','PT Lawencon Internasional','883bd334-9dbc-4f13-aa3c-d4ab404ee735','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now());


INSERT INTO t_user (id,fullname,email,pass,company,role_id,industry_id,position_id,file_id,created_by,created_at) VALUES
('1','dummy','dummy1','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('2','dummy','dummy2','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('3','dummy','dummy3','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('4','dummy','dummy4','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('5','dummy','dummy5','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('6','dummy','dummy6','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('7','dummy','dummy7','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('8','dummy','dummy8','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('9','dummy','dummy9','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('10','dummy','dummy10','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('11','dummy','dummy11','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('12','dummy','dummy12','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('13','dummy','dummy13','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('14','dummy','dummy14','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('15','dummy','dummy15','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('16','dummy','dummy16','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('17','dummy','dummy17','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('18','dummy','dummy18','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('19','dummy','dummy19','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now()),
('20','dummy','dummy20','system','PT Lawencon Internasional','85b2bed6-104e-4f17-9f94-8aede7dd18cb','4eb0b229-953a-41f9-980f-0fa188f76b9b','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now());

SELECT COUNT(u.id) FROM t_user u INNER JOIN t_role ur ON ur.id = u.role_id WHERE role_code = 'SYS';