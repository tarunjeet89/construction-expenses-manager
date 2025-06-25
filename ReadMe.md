

select cm.name,p.amount,p.payment_date, p.payment_mode,p.payment_intent, pd.file_name from construction_members cm inner join payments p on p.construction_member_id=cm.id 
left join payment_documents pd on pd.payment_id=p.id order by p.payment_date

select sum (amount) from payments