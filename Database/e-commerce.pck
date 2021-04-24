create or replace package e_commerce is

  -- Author  : VERDIYEVI
  -- Created : 24-Apr-21 12:41:34
  -- Purpose : 

  procedure add_merchant(p_name          in nvarchar2,
                         p_type          in nvarchar2,
                         p_owner_name    in nvarchar2,
                         p_address       in nvarchar2,
                         p_phone_number  in nvarchar2,
                         p_email_address in nvarchar2,
                         p_password      in nvarchar2,
                         p_merchant_id   out number);

  procedure get_merchant_by_name(p_name       in nvarchar2,
                                 p_out_cursor out sys_refcursor);
end;
/
create or replace package body e_commerce is

  procedure add_merchant(p_name          in nvarchar2,
                         p_type          in nvarchar2,
                         p_owner_name    in nvarchar2,
                         p_address       in nvarchar2,
                         p_phone_number  in nvarchar2,
                         p_email_address in nvarchar2,
                         p_password      in nvarchar2,
                         p_merchant_id   out number) is
  begin
    insert into ecommerce_merchants
      (Address,
       Email_Address,
       Name,
       Owner_Name,
       Password,
       Phone_Number,
       Type)
    values
      (p_address,
       p_email_address,
       p_name,
       p_owner_name,
       p_password,
       p_phone_number,
       p_type)
    returning id into p_merchant_id;
  end;

  procedure get_merchant_by_name(p_name       in nvarchar2,
                                 p_out_cursor out sys_refcursor) is
  begin
    open p_out_cursor for
      select t.id            merchant_id,
             t.address       address,
             t.email_address email_address,
             t.name          name,
             t.owner_name    owner_name,
             t.password      password,
             t.phone_number  phone_number,
             t.type          type
        from ecommerce_merchants t
       where t.name = p_name;
  end;

end;
/
