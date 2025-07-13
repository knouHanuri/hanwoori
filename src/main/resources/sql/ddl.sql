
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
    store_file_name varchar(200) not null,
    file_size int not null,
    created_date timestamp not null default current_timestamp
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
create table if not exists signup_history (
    signup_id int primary key auto_increment,
    member_id int not null,
    year    int not null,
    semester int not null,
    subject_id int not null,
    created_date timestamp not null default current_timestamp,
    updated_date timestamp not null default current_timestamp,
    constraint fk_signup_member_id foreign key (member_id) references member(member_id)
    on delete cascade,
    constraint fk_signup_subject_id foreign key (subject_id) references subject(subject_id)
    on delete cascade
);

-- 과목 insert
insert into subject(subject_name,grade,semester) values("컴퓨터 이해",1,1);
insert into subject(subject_name,grade,semester) values("세계의정치와경제",1,1);
insert into subject(subject_name,grade,semester) values("원격대학교육의이해",1,1);
insert into subject(subject_name,grade,semester) values("사진의이해",1,1);
insert into subject(subject_name,grade,semester) values("유비쿼터스컴퓨팅개론",1,1);
insert into subject(subject_name,grade,semester) values("파이썬프로그래밍기초",1,1);
insert into subject(subject_name,grade,semester) values("데이터정보처리입문",1,1);

insert into subject(subject_name,grade,semester) values("한국사의이해",2,1);
insert into subject(subject_name,grade,semester) values("생명과환경",2,1);
insert into subject(subject_name,grade,semester) values("원격대학교육의이해",2,1);
insert into subject(subject_name,grade,semester) values("이산수학",2,1);
insert into subject(subject_name,grade,semester) values("Java프로그래밍",2,1);
insert into subject(subject_name,grade,semester) values("HTML5웹프로그래밍",2,1);
insert into subject(subject_name,grade,semester) values("세상읽기와논술",2,1);

insert into subject(subject_name,grade,semester) values("원격대학교육의이해",3,1);
insert into subject(subject_name,grade,semester) values("그래픽커뮤니케이션",3,1);
insert into subject(subject_name,grade,semester) values("데이터베이스시스템",3,1);
insert into subject(subject_name,grade,semester) values("디지털논리회로",3,1);
insert into subject(subject_name,grade,semester) values("운영체제",3,1);
insert into subject(subject_name,grade,semester) values("인공지능",3,1);
insert into subject(subject_name,grade,semester) values("알고리즘",3,1);

insert into subject(subject_name,grade,semester) values("원격대학교육의이해",4,1);
insert into subject(subject_name,grade,semester) values("컴퓨터그래픽스",4,1);
insert into subject(subject_name,grade,semester) values("모바일앱프로그래밍",4,1);
insert into subject(subject_name,grade,semester) values("컴퓨터보안",4,1);
insert into subject(subject_name,grade,semester) values("소프트웨어공학",4,1);
insert into subject(subject_name,grade,semester) values("정보통신망",4,1);
insert into subject(subject_name,grade,semester) values("생활과건강",4,1);


insert into subject(subject_name,grade,semester) values("대학영어",1,2);
insert into subject(subject_name,grade,semester) values("심리학에게묻다",1,2);
insert into subject(subject_name,grade,semester) values("원격대학교육의이해",1,2);
insert into subject(subject_name,grade,semester) values("대중영화의이해",1,2);
insert into subject(subject_name,grade,semester) values("컴퓨터과학개론",1,2);
insert into subject(subject_name,grade,semester) values("멀티미디어시스템",1,2);
insert into subject(subject_name,grade,semester) values("C프로그래밍",1,2);

insert into subject(subject_name,grade,semester) values("대학수학의이해",2,2);
insert into subject(subject_name,grade,semester) values("원격대학교육의이해",2,2);
insert into subject(subject_name,grade,semester) values("오픈소스기반데이터분석",2,2);
insert into subject(subject_name,grade,semester) values("자료구조",2,2);
insert into subject(subject_name,grade,semester) values("선형대수",2,2);
insert into subject(subject_name,grade,semester) values("프로그래밍언어론",2,2);
insert into subject(subject_name,grade,semester) values("경제학의이해",2,2);

insert into subject(subject_name,grade,semester) values("원격대학교육의이해",3,2);
insert into subject(subject_name,grade,semester) values("생활법률",3,2);
insert into subject(subject_name,grade,semester) values("컴퓨터구조",3,2);
insert into subject(subject_name,grade,semester) values("JSP프로그래밍",3,2);
insert into subject(subject_name,grade,semester) values("UNIX시스템",3,2);
insert into subject(subject_name,grade,semester) values("시뮬레이션",3,2);
insert into subject(subject_name,grade,semester) values("머신러닝",3,2);

insert into subject(subject_name,grade,semester) values("원격대학교육의이해",4,2);
insert into subject(subject_name,grade,semester) values("경영전략론",4,2);
insert into subject(subject_name,grade,semester) values("클라우드컴퓨팅",4,2);
insert into subject(subject_name,grade,semester) values("컴파일러구성",4,2);
insert into subject(subject_name,grade,semester) values("딥러닝",4,2);
insert into subject(subject_name,grade,semester) values("빅데이터이해와활용",4,2);
insert into subject(subject_name,grade,semester) values("성,사랑,사회",4,2);




