
-- 회원
create table if not exists member (
    member_id int primary key auto_increment,
    login_id varchar(50) not null,
    password varchar(64) not null,
    name varchar(50) not null,
    grade enum('admin','basic') not null,   -- 관리자,일반
    email varchar(50),
    phone_number varchar(64),
    gender enum('male','female') not null,  -- 남자,여자
    birthdate DATE,
    student_no varchar(64),
    remark varchar(200),
    created_date timestamp not null default current_timestamp,
    updated_date timestamp not null default current_timestamp
);

-- 회원 추가정보
create table if not exists member_info(
    member_id int primary key,
    dummy1 int,
    dummy2 varchar(100),
    constraint fk_member_info_member_id foreign key(member_id) references member(member_id)
    on delete cascade
);

-- 게시판
create table if not exists post (
    post_id int primary key auto_increment,
    title varchar(100),
    member_id int,
    category int not null,    -- 공지사항,Q&A,자유게시판,갤러리
    content text,
    created_date timestamp not null default current_timestamp,
    updated_date timestamp not null default current_timestamp,
    constraint fk_post_member_id foreign key (member_id) references member (member_id)
    on delete cascade
);

-- 게시판 댓글
create table if not exists post_comment (
    post_comment_id int primary key auto_increment,
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
    source_kind int not null,    -- 게시판,스터디,회원
    source_id int not null,
    file_path varchar(200) not null,
    original_file_name varchar(200) not null,
    store_file_name varchar(200) not null
);



-- 스터디
create table if not exists study (
    study_id int primary key auto_increment,
    member_id int,  -- 스터디 만든 사람
    subject_id int,
    title varchar(100),
    status enum('active','complete','pending','ready') not null,    -- 진행중,완료,중단,준비중
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
    created_date timestamp not null default current_timestamp,
    constraint fk_study_participant_study_id foreign key (study_id) references study(study_id)
    on delete cascade,
    constraint fk_study_participant_member_id foreign key (member_id) references member(member_id)
    on delete cascade
);

-- 스터디 활동내역
create table if not exists study_activity (
    study_activity_id int primary key auto_increment,
    study_id int not null,
    study_date date not null,
    title varchar(100),
    content text,
    created_member_id int,
    created_date timestamp not null default current_timestamp,
    updated_date timestamp not null default current_timestamp,
    constraint fk_study_activity_study_id foreign key (study_id) references study(study_id)
    on delete cascade,
    constraint fk_study_activity_created_member_id foreign key (created_member_id) references member(member_id)
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
    updated_date timestamp not null default current_timestamp,
    constraint fk_enrollment_member_id foreign key (member_id) references member(member_id)
    on delete cascade,
    constraint fk_enrollment_subject_id foreign key (subject_id) references subject(subject_id)
    on delete cascade
);





