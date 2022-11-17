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
('ca0f691f-b583-4f33-954e-a7060613171d','Information Technology','IT','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now());

INSERT INTO t_position (id,position_name,position_code,created_by,created_at) VALUES
('ca0f691f-b583-4f33-954e-a7060613171d','System','SYS','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now());

INSERT INTO t_file (id,file_codes,extensions,created_by,created_at) VALUES
('2c77dd06-6557-48b2-a0e0-3d8245dd40f9','iVBORw0KGgoAAAANSUhEUgAAAgAAAAIACAYAAAD0eNT6AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAOxAAADsQBlSsOGwAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAACAASURBVHic7N13mFTV4cbx78xsL2wDREC6gnSsCAIqAvbuz957ixq7iYotNtTEEjUmNuw1NhQ7VlRQAoiiIKKCAltZtu/O/P64rCJ1d8+5c+fOfT/PM4+EMOeemZ2d895TQUREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREREQkiEJeV0BERAAoAA4BxgN9gRiwBJgGPLr6zyIiIpIEwsDuwONADU6jv75HDXAtkOpNNa3IAfYEzgHOAPYBCj2tkYiISJx1B64CFrHhRn99jylAugf1NdERuA+oZd3XUwc8A4z0rHYiIiIuywAOB94Ammhdw7/m40n8M3y7E7CClr2uT4HDgBRPaioiImLZMOAOoIS2N/prP66L6ytomxFAJa1/bYuBC4G8+FdZRETETB5wKvAh9hr9tR+nxe3VtN4QzANPJc7QQd84111ERKRVwsDOOI1WFe41/M2PemBsXF5Z62wF/Iq919kEvIwzWVJERCRhdAEuARbifqO/9qMCGOj+S2yxbjhd+G693pnAsfh7NYSIiPhYOnAozp1pI/Fv+Nd8fI8z095rXXDqEo/XvBSYiJYRiohInAwAbqTlM9vj9fgMyHLxdW9KB+Cr9dTL7YfmCYiIiGtCOMv3ZuJ9Q7+xh1fLAwuAWW2or81HI/As2k9AREQsyQNew/vGvaWP6915GzYoF5huqe62HtpPQEREjGTgNCZeN2itfZzgxpuxHpnAu3F6TW15aD8BERFpk9vwvhFry6Me2M2F92NNacCrCfBaW/KoAC5HPQIiItICXXEaUq8br7Y+yoCtrb8rjgjO/v1ev8bWPqbhDFmIiIhs0AV432CZPhZif3lgGHgoAV5bWx9TV78GERGR9fLjHe76Hh/jjNXbclcCvCbTx8kW3w8REUky7xHnhikcJrZ9v1Q3yn4KO8sDb4z3e+LSY4GF90JERJLUNOLUIPXoFIlNPCE39sPTHWOxaZvHLjgs243r/M3w/fhLvN6P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3q1QuJn719qYVRVw27mwYql3dQiFnOV9O4yD7ce6t53zOeOToY3coGR4cQoAIuKdWAy++tQZH3frzji/PZx7q7NjYSJY9pMTAqrj3AOS395p8EfsFZ/3QgEg4SkAiEhi+Ok7eO8FmPGuc7iODTl5cO4k6NTdTnm2LJgDd13i/nG7KakwaCfYYXfov4N7Sx7XRwEg4SV2ADhnvNnzTetnen0RL/k1oNuaJ5CZDefcnLhj29OnwmO3ulN2p+5Oo7/THk4IcoP596Ov21BNAhQRsa2oE+x3knM4z8dTYNqLULa8dWWkZzoT/hK18YfVywOXwhtP2CkvK9fp4h8+wVntIK5SABARcUtb9xPILYBTr4Ye/dyvo6l9jofipW1fHhgKQd9hMHwPGDwCUtOsVk82TAFARMRt69tPYM4n646fp6U7B/vsdax73d62NS8PrK2GeZ+3/HmFm8Hw8c7r9XplQ0ApAIiIxFPzfgLVlc6qgfISIOYMG/Qa6N6SNjelpjk7E06Z7PRyNNRv+N8NHgk7TYCthtk/wEhaRQFARMQLWbnOYT7JIhxxhgNG7Qsz3oHvZsPKUuf/69DZ6eYfOtqbTYxkvRQARETEnrwiZ97D2EO9rolsgk4DFBERCSAFABERkQBSABAREQkgBQAREZEAUgAQEREJIAUAERGRAFIAEBERCSAFABERkQBSABAREQkg7QToNq/PM/f6+iIikpDUAyAiIhJACgAiIiIBpAAgIiISQAoAIiIiAaQAICIiEkAKACIiIgGkACAiIhJACgAiIiIBpAAgIiISQAoAIiIiAaQAICIiEkAKACIiIgGkACAiIhJACgAiIiIBpAAgIiISQCleVyDpnTPe7Pl3vuHt9UWSmde/X36/vviaegBEREQCSAFAREQkgBQAREREAkgBQEREJIAUAERERAJIAUBERCSAFABEREQCSAFAREQkgBQAREREAkgBQEREJIAUAERERAJIAUBERCSAFABEREQCSAFAREQkgBQAREREAijkdQUsiBk9W+dhi4gE0znjTUvwdRuqHgAREZEAUgAQEREJIAUAERGRAFIAEBERCSAFABERkQBSABAREQkgBQAREZEAUgAQEREJIAUAERGRAFIAEBERCSAFABERkQBSABAREQkgBQAREZEAUgAQEREJIAUAERGRAErxugJJz/S86Tvf8Pb6IuIdr3///X592Sj1AIiIiASQAoCIiEgAKQCIiIgEkAKAiIhIACkAiIiIBJACgIiISAApAIiIiASQAoCIiEgAKQCIiIgEkAKAiIhIACkAiIiIBJACgIiISAApAIiIiASQAoCIiEgAKQCIiIgEUMjrClgQM3q2zpsWEQmmc8abluDrNlQ9ACIiIgGkACAiIhJACgAiIiIBpAAgIiISQAoAIiIiAaQAICIiEkAKACIiIgGkACAiIhJACgAiIiIBpAAgIiISQAoAIiIiAaQAICIiEkAKACIiIgGkACAiIhJACgAiIiIB5OuzjFeLeV0BEREJJF+3oeoBEBERCSAFABERkQBSABAREQkgBQAREZEAUgAQEREJIAUAERGRAFIAEBERCSAFABERkQBSABAREQkgBQAREZEAUgAQERHk9eY2AAAPnklEQVQJIAUAERGRAFIAEBERCSAFABERkQBSABAREQmgFK8r4LUZC5Z4XQUREfHAdn26eF0FT6kHQEREJIAUAERERAJIAUBERCSAFABEREQCSAFAREQkgBQAREREAkgBQEREJIAUAERERAJIAUBERCSAFABEREQCSAFAREQkgBQAREREAkgBQEREJIAUAERERAJIAUBERCSAUryugIi4x/S88xkLlliqiYgkGvUAiIiIBJACgIiISAApAIiIiASQAoCIiEgAKQCIiIgEkAKAiIhIACkAiIiIBFAyBIB6kyc3NDTYqoeIiPhEfb1R0wFQZ6MeXkqGALDK5Mk1VVW26iEiIj5RtarStIiVNurhpWQIAEY/hIqKclv1EBERn6isqDAuwkY9vJQMAcCoB+CnxYts1UNERHzixx+Mv/vVA5AAfjF58g8LF9iqh4iI+MTiRQtNi/jVRj28lAwB4FuTJ8+d9YWteoiIiE/M/mKGaRHzbdTDS8kQAIx+CDOmf0wsFrNVFxERSXCxWIyZn35iWowCQAL4xuTJpSXFfDV7lq26iIhIgpvz5UzKy0pNi/F9AEjxugIWfAlEMQgzrz7/DAOHDLNXI5EEMWPBEq+rIJJwXv3vs6ZFRAHf3zkmQw9AMTDHpICpr75IdbX2AxARSXbVVat489WXTIv5EjDuQvBaMgQAgHdMnryyvJwXnnzMVl1ERCRBPfvYI6w03wPgbRt18ZoCwGqT/32PegFERJJY1apKHn3gXzaKetdGIV5LlgDwFmC0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',
'png','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now());

INSERT INTO t_user (id,fullname,email,pass,company,role_id,industry_id,position_id,file_id,created_by,created_at) VALUES
('02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9','System','System','system','PT Lawencon Internasional','883bd334-9dbc-4f13-aa3c-d4ab404ee735','ca0f691f-b583-4f33-954e-a7060613171d','ca0f691f-b583-4f33-954e-a7060613171d','2c77dd06-6557-48b2-a0e0-3d8245dd40f9','02b8ff5a-7fe3-4d9c-b4f1-6fe1485965b9',now());