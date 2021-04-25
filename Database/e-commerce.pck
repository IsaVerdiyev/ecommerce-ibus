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

  procedure add_product(p_name             nvarchar2,
                        p_description      nvarchar2,
                        p_unit_price       number,
                        p_merchant_id      number,
                        p_product_category nvarchar2,
                        p_product_id       out number);

  procedure add_inventory_item(p_name              nvarchar2,
                               p_product_id        in number,
                               p_inventory_item_id out number);

  procedure add_delivery_options(p_product_id       in nvarchar2,
                                 p_delivery_options in nvarchar2);
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

  procedure add_product(p_name             nvarchar2,
                        p_description      nvarchar2,
                        p_unit_price       number,
                        p_merchant_id      number,
                        p_product_category nvarchar2,
                        p_product_id       out number) is
  begin
    insert into ecommerce_products
      (name, description, merchant_id, product_category, unit_price)
    values
      (p_name,
       p_description,
       p_merchant_id,
       p_product_category,
       p_unit_price)
    returning id into p_product_id;
  end;

  procedure add_inventory_item(p_name              nvarchar2,
                               p_product_id        in number,
                               p_inventory_item_id out number) is
  begin
    insert into ecommerce_inventory_items
      (name, product_id)
    values
      (p_name, p_product_id)
    returning id into p_inventory_item_id;
  end;

  procedure add_delivery_options(p_product_id       in nvarchar2,
                                 p_delivery_options in nvarchar2) is
  begin
    insert into ecommerce_delivery_options
      (id, some_delivery_options)
    values
      (p_product_id, p_delivery_options);
  end;
end;
/
