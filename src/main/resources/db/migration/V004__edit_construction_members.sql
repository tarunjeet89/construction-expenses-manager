ALTER TABLE construction_members DROP COLUMN IF EXISTS email;
ALTER TABLE construction_members DROP COLUMN IF EXISTS first_name;
ALTER TABLE construction_members DROP COLUMN IF EXISTS last_name;
ALTER TABLE construction_members DROP COLUMN IF EXISTS dob;
ALTER TABLE construction_members DROP COLUMN IF EXISTS alternate_phone_number;
ALTER TABLE construction_members DROP COLUMN IF EXISTS address_line_1;
ALTER TABLE construction_members DROP COLUMN IF EXISTS address_line_2;
ALTER TABLE construction_members DROP COLUMN IF EXISTS city;
ALTER TABLE construction_members DROP COLUMN IF EXISTS state;
ALTER TABLE construction_members DROP COLUMN IF EXISTS pin_code;
ALTER TABLE construction_members DROP COLUMN IF EXISTS member_type;

ALTER TABLE payments DROP COLUMN IF EXISTS work_from;
ALTER TABLE payments DROP COLUMN IF EXISTS work_to;

