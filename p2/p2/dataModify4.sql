select mid from media where title = 'Pokemon';
update rating set value = 5 where mid in (select mid from media where title = 'Pokemon');
select mid from media where title = 'Pokemon';


select * from account;
delete from account
where not exists
    (select accid from payment
    where
          payment.accid = account.accid);
select * from account;


select * from queues;
insert into queues select 'bobby', '123', media.mid from media where title = 'Titanic';
select * from queues;

select * from queues;
delete from queues
where not exists (
    select available_in.mid from available_in
    where regname = 'Canada')
  and queues.username = 'bobby';
select * from queues;
