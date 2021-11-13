CREATE TABLE IF NOT EXISTS acl_sid (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    principal tinyint(1) NOT NULL,
    sid varchar(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_1 (sid,principal)
    );

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'user'),
(2, 1, 'api'),
(3, 1, 'admin');

CREATE TABLE IF NOT EXISTS acl_class (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    class varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_2 (class)
    );

CREATE TABLE IF NOT EXISTS acl_object_identity (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    object_id_class bigint(20) NOT NULL,
    object_id_identity bigint(20) NOT NULL,
    parent_object bigint(20) DEFAULT NULL,
    owner_sid bigint(20) DEFAULT NULL,
    entries_inheriting tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity),
    FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
    FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
    FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
    );

INSERT INTO acl_class (id, class) VALUES
(1, 'ru.sber.springsecurity.entity.AddressBook');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 3, 0),
(2, 1, 2, NULL, 3, 0),
(3, 1, 3, NULL, 3, 0);


CREATE TABLE IF NOT EXISTS acl_entry (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    acl_object_identity bigint(20) NOT NULL,
    ace_order int(11) NOT NULL,
    sid bigint(20) NOT NULL,
    mask int(11) NOT NULL,
    granting tinyint(1) NOT NULL,
    audit_success tinyint(1) NOT NULL,
    audit_failure tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order),
    FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id),
    FOREIGN KEY (sid) REFERENCES acl_sid(id));

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 1, 2, 3, 1, 1, 1, 1),
(3, 1, 3, 3, 2, 1, 1, 1);

insert into t_user values(id, is_active, password, username) VALUES
(1, true, user, user),
(2, true, admin, admin),
(3, true, api, api);
