
-- 회원
create table if not exists member (
    member_id int primary key auto_increment,
    login_id varchar(50) not null,
    password varchar(200) not null,
    name varchar(50) not null,
    grade int not null,  -- 0: 관리자, 1: 일반
    email varchar(50),
    phone_number varchar(15),
    gender int,     -- 0: male, 1 : female
    birthdate DATE,
    student_no varchar(20),
    remark varchar(200),
    created_date timestamp not null default current_timestamp,
    updated_date timestamp not null default current_timestamp
);

-- 회원 추가정보
create table if not exists member_info(
    member_id int primary key,
    dummy1 int,
    dummy2 varchar(100),
    constraint fk_member_info_member_id key(member_id) references member(member_id)
    on delete cascade
);

-- 게시판
create table if not exists post (
    post_id int primary key auto_increment,
    title varchar(100),
    member_id int,
    category_id int, -- 0: 공지사항, 1: 그 외
    content text,
    created_date timestamp not null default current_timestamp,
    updated_date timestamp not null default current_timestamp,
    constraint fk_post_member_id foreign key (member_id) references member (member_id)
    on delete cascade
);

-- 게시판 댓글
create table if not exists post_comment (
    post_comment_id int primary key,
    post_id int not null,
    member_id int,
    comment text,
    created_date timestamp not null default current_timestamp,
    updated_date timestamp not null default current_timestamp,
    constraint fk_post_comment_post_id foreign key (post_id) references post (post_id)
    on delete cascade,
    constraint fk_post_comment_member_id foreign key (member_id) references member (member_id)
    on delete cascade
);


-- 파일
create table if not exists file(
    file_id int primary key auto_increment,
    source_kind int,  -- 0: board, 1: study, 2: member
    source_id int not null,
    file_path varchar(200) not null,
    original_file_name varchar(200) not null,
    store_file_name varchar(200) not null
);



-- 스터디
create table if not exists study (
    study_id int primary key auto_increment,
    subject_id int,
    title varchar(100),
    status int, -- 0: 진행, 1:완료, 2: 중단
    schedule varchar(50),
    goal varchar(200),
    start_date date,
    end_date date,
    remark nvarchar(200),
    created_date timestamp not null default current_timestamp,
    updated_date timestamp not null default current_timestamp
);


-- 스터디 참여자
create table if not exists study_participant (
    study_participant_id int primary key auto_increment,
    study_id int not null,
    member_id int,
    CONSTRAINT fk_study_participant_study_id FOREIGN KEY (study_id) REFERENCES study(study_id)
    ON DELETE CASCADE,
    CONSTRAINT fk_study_participant_member_id FOREIGN KEY (member_id) REFERENCES member(member_id)
    ON DELETE CASCADE
);

-- 스터디 활동내역
create table if not exists study_activity (
    study_activity_id int primary key auto_increment,
    study_id int not null,
    study_date date not null,
    title varchar(100),
    content text,
    constraint fk_study_activity_study_id foreign key (study_id) references study(study_id)
    on delete cascade
);


-- 과목
create table if not exists subject (
    subject_id int primary key auto_increment,
    subject_name varchar(100) not null,
    grade int,
    semester int,
    professor varchar(50),
    remark varchar(200)
);

-- 수강신청내역
create table if not exists enrollment_history (
    enrollment_id int primary key auto_increment,
    member_id int,
    subject_id int,
    year    int,
    created_date timestamp not null default current_timestamp,
    updated_date timestamp not null default current_timestamp
    constraint fk_enrollment_member_id foreign key (member_id) references member(member_id)
    on delete cascade,
    constraint fk_enrollment_subject_id foreign key (subject_id) references subject(subject_id)
    on delete cascade
);





