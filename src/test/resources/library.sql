
-- US 01
select count(id) from users; --
-- 1855

select count(distinct id) from users;
-- 1855


-- RESULT --> MANUALLY IT IS PASSED


-- US 02
select * from users;



-- US 03
select count(*) from book_borrow
where is_returned=0;

-- US 04
select * from books
where name = 'Clean Code'
order by isbn desc ;


-- US 07

select * from book_borrow
where is_returned = 0 and book_id = 5799 and user_id = 5770;

select * from books
where name = 'Self Confidence' and id = 5799; -- id 5799

select full_name from users
where email='student18@library' and id = 5770; -- id 5770

select is_returned, bb.book_id,  b.id, name
from book_borrow bb join books b on bb.book_id = b.id
where is_returned = 0 and name = 'Self Confidence';

select is_returned, bb.user_id, u.id , email
from book_borrow bb join users u on bb.user_id = u.id
where is_returned = 0 and email = 'student18@library';

select full_name, name, is_returned
from book_borrow bb join books b on bb.book_id = b.id
                    join users u on bb.user_id = u.id
where email = 'student18@library' and name = 'Self Confidence' and is_returned = 0;



