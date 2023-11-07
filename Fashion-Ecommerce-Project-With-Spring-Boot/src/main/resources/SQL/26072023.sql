use fashion_ecommerce;
alter table categories
add column image MEDIUMBLOB null;
alter table categories drop column image;

alter table categories
add column image text;

select c.name, count(p.category_id) from categories c inner join  products p on p.category_id = c.category_id
where c.is_activated = true and c.is_delete = false;

ALTER TABLE `fashion_ecommerce`.`customers` 
ADD UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE;
