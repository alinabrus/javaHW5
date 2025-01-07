--liquibase formatted sql
--changeset abrus:20241219202408_create-tables
create table task_history (
    change_date datetime(6),
    id bigint not null auto_increment,
    todo_id bigint,
    changed_by varchar(255),
    new_state varchar(255),
    old_state varchar(255),
    primary key (id)
) engine=InnoDB;

create table todo (
    created_date datetime(6),
    due_date datetime(6),
    id bigint not null auto_increment,
    updated_date datetime(6),
    user_id bigint,
    description varchar(255),
    title varchar(255),
    priority enum ('HIGH','LOW','MEDIUM'),
    status enum ('COMPLETED','IN_PROGRESS','PENDING'),
    primary key (id),
    unique key todo_uk_title (title)
) engine=InnoDB;

alter table task_history add constraint task_history_fk_todo_id
foreign key (todo_id) references todo (id);

/* liquibase rollback
DROP TABLE `task_history`;
DROP TABLE `todo`;
*/
