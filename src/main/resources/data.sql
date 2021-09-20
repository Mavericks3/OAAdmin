select * from public.resource_table;

DELETE FROM PUBLIC.ROLE_TABLE;

INSERT INTO public.role_table (role_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status,role_name) VALUES ('633f051b-4f39-46a7-a5f8-4db4fc0f1438', null, null, null, null, null, null, 'MASTER',  'BANK_USER_CHECKER');
INSERT INTO public.role_table (role_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status,role_name) VALUES ('bc1e2893-9541-442c-96e0-dd762378c6a1', null, null, null, null, null, null, 'MASTER',  'CORPORATE_USER_VIEWER');
INSERT INTO public.role_table (role_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status,role_name) VALUES ('9c369001-99af-48e7-b463-258a9dce2128', null, null, null, null, null, null, 'MASTER',  'BANK_USER_VIEWER');
INSERT INTO public.role_table (role_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status,role_name) VALUES ('0de3a370-40ff-4027-b9f9-cd6f56931d27', null, null, null, null, null, null, 'MASTER',  'CORPORATE_USER_MAKER');
INSERT INTO public.role_table (role_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status,role_name) VALUES ('02ca5215-7956-41e4-babe-fd99181be0a6', null, null, '2021-09-09 13:22:29.000000', null, null, null, 'MASTER',  'BANK_USER_MAKER');
INSERT INTO public.role_table (role_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status,role_name) VALUES ('a5e1526c-c5e0-44ef-8416-74254f32eb8e', '2021-09-09 13:20:27.000000', 'RAVIKANTH', '2021-09-09 13:20:42.000000', 'RAVIKANTH', '2021-09-09 13:20:52.000000', 'RAVIKANTH', 'MASTER',  'SUPER_ADMIN');
INSERT INTO public.role_table (role_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status,role_name) VALUES ('be10ce93-499b-4bff-8f9f-dbe9d2474190', '2021-09-09 13:21:49.000000', 'RAVIKANTH', '2021-09-09 13:21:57.000000', null, null, null, 'MASTER',  'BANK_ADMIN');
INSERT INTO public.role_table (role_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status,role_name) VALUES ('d005a107-1b1f-4ebb-8697-d0dbae66e9da', null, null, null, null, null, null, 'MASTER',  'CORPORATE_USER_CHECKER');

DELETE FROM PUBLIC.customer_table;
INSERT INTO public.customer_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, address_line1, address_line2, address_line3, country, customer_id, director_details, director_name, email_address, name, po_box, sponsor_details, sponsor_name, status, tax_registration_number, vat_registration_number, is_bank) VALUES ('ea3b9532-5f64-4f89-aa24-e198b93ec6de', null, null, null, null, null, null, 'MASTER', 'addressline31', 'addressline32', 'addressline33', 'india', null, 'ramakrishna puram, vijayawada3', 'mamillapalli3', 'ravikanthm3@gmail.com', 'Ravikanth Electronics3', '111113', 'Kallepallivari street, Vijayawada3', 'Venkateswarlu', 'Active', 'Tax123453', 'Vat234563', false);
INSERT INTO public.customer_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, address_line1, address_line2, address_line3, country, customer_id, director_details, director_name, email_address, name, po_box, sponsor_details, sponsor_name, status, tax_registration_number, vat_registration_number, is_bank) VALUES ('a855476b-3836-433d-81cb-35ec255db192', null, null, null, null, null, null, 'MASTER', 'addressline21', 'addressline22', 'addressline23', 'india', 'ravikanth2', 'ramakrishna puram, vijayawada2', 'mamillapalli2', 'ravikanthm2@gmail.com', 'Ravikanth Electronics2', '111112', 'Kallepallivari street, Vijayawada2', 'Venkateswarlu', 'Active', 'Tax123452', 'Vat234562', false);
INSERT INTO public.customer_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, address_line1, address_line2, address_line3, country, customer_id, director_details, director_name, email_address, name, po_box, sponsor_details, sponsor_name, status, tax_registration_number, vat_registration_number, is_bank) VALUES ('3c34b901-d8df-4c34-bc35-0cc18a688331', null, null, '2021-09-09 11:00:07.894000', 'ToBeChanged', '2021-09-09 11:00:07.894000', 'ToBeChanged', 'MASTER', 'addressline31', 'addressline32', 'addressline33', 'india', 'Ravikanth5', 'ramakrishna puram, vijayawada3', 'mamillapalli3', 'ravikanthm3@gmail.com', 'Ravikanth Electronics4', '111113', 'Kallepallivari street, Vijayawada3', 'Venkateswarlu', 'Active', 'Tax123453', 'Vat234563', false);
INSERT INTO public.customer_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, address_line1, address_line2, address_line3, country, customer_id, director_details, director_name, email_address, name, po_box, sponsor_details, sponsor_name, status, tax_registration_number, vat_registration_number, is_bank) VALUES ('373c3c01-2bda-423f-bcdc-c5eae7c177c4', null, null, '2021-09-09 14:57:24.955000', 'ToBeChanged', '2021-09-09 14:57:24.955000', 'ToBeChanged', 'MASTER', 'addressline31', 'addressline32', 'addressline33', 'india', 'Ravikanth4', 'ramakrishna puram, vijayawada3', 'mamillapalli3', 'ravikanthm3@gmail.com', 'Ravikanth Electronics3', '111113', 'Kallepallivari street, Vijayawada3', 'Venkateswarlu', 'Active', 'Tax123453', 'Vat234563', false);
INSERT INTO public.customer_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, address_line1, address_line2, address_line3, country, customer_id, director_details, director_name, email_address, name, po_box, sponsor_details, sponsor_name, status, tax_registration_number, vat_registration_number, is_bank) VALUES ('793a2fa1-6a9f-44c2-88bd-dcf90a9089d4', null, null, null, null, null, null, 'MASTER', 'addressline1', 'addressline2', 'addressline3', 'india', 'AndhraBank', 'ramakrishna puram, vijayawada', 'mamillapalli', 'ravikanthm@gmail.com', 'Andhra Bank', '11111', 'Kallepallivari street, Vijayawada', 'Venkateswarlu', 'Active', 'Tax12345', 'Vat23456', true);
INSERT INTO public.customer_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, address_line1, address_line2, address_line3, country, customer_id, director_details, director_name, email_address, name, po_box, sponsor_details, sponsor_name, status, tax_registration_number, vat_registration_number, is_bank) VALUES ('8e40ab59-8c91-431f-a695-6c3150eb65ed', '2021-09-11 12:35:44.917000', 'ToBeChanged', null, null, '2021-09-11 12:35:44.900000', 'ToBeChanged', 'MASTER', 'addressline311', 'addressline32', 'addressline33', 'india', 'Ravikanth3', 'ramakrishna puram, vijayawada3', 'mamillapalli3', 'ravikanthm3@gmail.com', 'Ravikanth Electronics3', '111113', 'Kallepallivari street, Vijayawada3', 'Venkateswarlu', 'Active', 'Tax123453', 'Vat234563', false);

DELETE FROM PUBLIC.user_table;
INSERT INTO public.user_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, effective_date, email_address, first_name, last_name, active_status, user_id) VALUES ('91e925e3-0a6b-462a-9175-b13449de71bd', '2021-09-11 12:35:45.221000', 'RAVIKANTH', '2021-09-11 08:56:15.875000', 'RAVIKANTH', '2021-09-11 12:35:45.244000', 'RAVIKANTH', 'MASTER', '2016-09-09 17:18:52.000000', 'nekkanti@gmail.com', 'kp', 'nekkanti', false, 'kp');
INSERT INTO public.user_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, effective_date, email_address, first_name, last_name, active_status, user_id) VALUES ('c0b4d893-9f6f-4ec8-844a-3eea75eb4ef8', null, null, null, null, null, null, 'MASTER', '2021-09-09 17:18:52.000000', 'ilyas143@gmail.com', 'Ilyas', 'Hussain', true, 'Ilyas');
INSERT INTO public.user_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, effective_date, email_address, first_name, last_name, active_status, user_id) VALUES ('a14aca67-2b98-4402-8dfd-85cd4ce38fa7', null, null, '2021-09-10 15:00:33.349000', 'RAVIKANTH', '2021-09-10 15:00:33.349000', 'RAVIKANTH', 'MASTER', '2021-09-09 17:18:52.000000', 'sasi@gmail.com', 'sasi', 'kanth', true, 'sasi');
INSERT INTO public.user_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, effective_date, email_address, first_name, last_name, active_status, user_id) VALUES ('adbf3f67-a12b-41c3-bfea-81aa777a714b', '2021-09-11 12:35:45.384000', 'RAVIKANTH', '2021-09-11 11:56:44.200000', 'RAVIKANTH', '2021-09-11 12:35:45.408000', 'RAVIKANTH', 'MASTER', '2016-09-09 17:18:52.000000', 'kedar@gmail.com', 'kedarnath', 'Dulla', false, 'kedar');
INSERT INTO public.user_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, effective_date, email_address, first_name, last_name, active_status, user_id) VALUES ('4853916d-7e7e-4935-87c3-015ece538734', null, null, '2021-09-10 19:18:31.465000', 'RAVIKANTH', '2021-09-10 19:18:31.465000', 'RAVIKANTH', 'MASTER', '2019-09-09 17:18:52.000000', 'antony@gmail.com', 'Antony', 'donald', true, 'Antony');
INSERT INTO public.user_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, effective_date, email_address, first_name, last_name, active_status, user_id) VALUES ('2006b1b3-8343-427f-b88b-0ce2aafa72e0', null, null, '2021-09-10 20:19:18.418000', 'RAVIKANTH', '2021-09-10 20:19:18.418000', 'RAVIKANTH', 'MASTER', '2016-09-09 17:18:52.000000', 'sreeni@gmail.com', 'sreenivas', 'reddy', true, 'Sreenivas');
INSERT INTO public.user_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, effective_date, email_address, first_name, last_name, active_status, user_id) VALUES ('875ce700-7c18-4d37-935f-8cc025db6f20', null, null, '2021-09-11 12:18:56.402000', 'RAVIKANTH', '2021-09-11 12:18:56.402000', 'RAVIKANTH', 'MASTER', '2021-09-09 17:18:52.000000', 'sasi@gmail.com', 'sasi', 'kanth', true, 'sasi2');
INSERT INTO public.user_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, effective_date, email_address, first_name, last_name, active_status, user_id) VALUES ('deb06936-65b9-4007-aadf-78d1b5433a08', '2021-09-11 12:35:44.668000', 'RAVIKANTH', '2021-09-09 17:18:28.000000', 'ravikanth', '2021-09-11 12:35:44.626000', 'RAVIKANTH', 'MASTER', '2019-09-09 17:18:52.000000', 'anil@chinasystems-me.com', 'AnilKiran', 'Ankireddy1', false, 'Anil');
INSERT INTO public.user_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, effective_date, email_address, first_name, last_name, active_status, user_id) VALUES ('18355e5f-9396-4cc8-b391-11c6ea79189b', '2021-09-11 12:35:44.786000', 'RAVIKANTH', '2021-09-10 18:37:44.070000', 'RAVIKANTH', '2021-09-11 12:35:44.766000', 'RAVIKANTH', 'MASTER', '2016-09-09 17:18:52.000000', 'vijayan@chinasystems-me.com', 'vijayan', 'venkatesan s', true, 'vijayan');
INSERT INTO public.user_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, effective_date, email_address, first_name, last_name, active_status, user_id) VALUES ('d383c9f3-4a60-4a47-a451-287564ff5061', null, null, '2021-09-10 18:42:56.878000', 'RAVIKANTH', '2021-09-11 12:35:44.807000', 'RAVIKANTH', 'MASTER', '2021-09-09 17:18:52.000000', 'vijayan@gmail.com', 'vijayan', 'venkatesan', false, 'vijayan2');

DELETE FROM PUBLIC.user_customer_mapping;
INSERT INTO public.user_customer_mapping (user_id, customer_id) VALUES ('deb06936-65b9-4007-aadf-78d1b5433a08', '793a2fa1-6a9f-44c2-88bd-dcf90a9089d4');
INSERT INTO public.user_customer_mapping (user_id, customer_id) VALUES ('a14aca67-2b98-4402-8dfd-85cd4ce38fa7', '793a2fa1-6a9f-44c2-88bd-dcf90a9089d4');
INSERT INTO public.user_customer_mapping (user_id, customer_id) VALUES ('18355e5f-9396-4cc8-b391-11c6ea79189b', '793a2fa1-6a9f-44c2-88bd-dcf90a9089d4');
INSERT INTO public.user_customer_mapping (user_id, customer_id) VALUES ('d383c9f3-4a60-4a47-a451-287564ff5061', '793a2fa1-6a9f-44c2-88bd-dcf90a9089d4');
INSERT INTO public.user_customer_mapping (user_id, customer_id) VALUES ('4853916d-7e7e-4935-87c3-015ece538734', 'a855476b-3836-433d-81cb-35ec255db192');
INSERT INTO public.user_customer_mapping (user_id, customer_id) VALUES ('2006b1b3-8343-427f-b88b-0ce2aafa72e0', '793a2fa1-6a9f-44c2-88bd-dcf90a9089d4');
INSERT INTO public.user_customer_mapping (user_id, customer_id) VALUES ('91e925e3-0a6b-462a-9175-b13449de71bd', '793a2fa1-6a9f-44c2-88bd-dcf90a9089d4');
INSERT INTO public.user_customer_mapping (user_id, customer_id) VALUES ('875ce700-7c18-4d37-935f-8cc025db6f20', '793a2fa1-6a9f-44c2-88bd-dcf90a9089d4');
INSERT INTO public.user_customer_mapping (user_id, customer_id) VALUES ('f6368da2-7ecb-4e20-be14-b66175ed69b1', '3c34b901-d8df-4c34-bc35-0cc18a688331');
INSERT INTO public.user_customer_mapping (user_id, customer_id) VALUES ('adbf3f67-a12b-41c3-bfea-81aa777a714b', '373c3c01-2bda-423f-bcdc-c5eae7c177c4');

DELETE FROM PUBLIC.user_roles;
INSERT INTO public.user_roles (user_id, role_id) VALUES ('deb06936-65b9-4007-aadf-78d1b5433a08', 'a5e1526c-c5e0-44ef-8416-74254f32eb8e');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('c0b4d893-9f6f-4ec8-844a-3eea75eb4ef8', 'a5e1526c-c5e0-44ef-8416-74254f32eb8e');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('a14aca67-2b98-4402-8dfd-85cd4ce38fa7', 'a5e1526c-c5e0-44ef-8416-74254f32eb8e');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('18355e5f-9396-4cc8-b391-11c6ea79189b', 'be10ce93-499b-4bff-8f9f-dbe9d2474190');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('d383c9f3-4a60-4a47-a451-287564ff5061', 'be10ce93-499b-4bff-8f9f-dbe9d2474190');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('2006b1b3-8343-427f-b88b-0ce2aafa72e0', '02ca5215-7956-41e4-babe-fd99181be0a6');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('2006b1b3-8343-427f-b88b-0ce2aafa72e0', '633f051b-4f39-46a7-a5f8-4db4fc0f1438');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('2006b1b3-8343-427f-b88b-0ce2aafa72e0', '9c369001-99af-48e7-b463-258a9dce2128');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('875ce700-7c18-4d37-935f-8cc025db6f20', 'a5e1526c-c5e0-44ef-8416-74254f32eb8e');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('91e925e3-0a6b-462a-9175-b13449de71bd', '02ca5215-7956-41e4-babe-fd99181be0a6');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('91e925e3-0a6b-462a-9175-b13449de71bd', '633f051b-4f39-46a7-a5f8-4db4fc0f1438');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('adbf3f67-a12b-41c3-bfea-81aa777a714b', '0de3a370-40ff-4027-b9f9-cd6f56931d27');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('adbf3f67-a12b-41c3-bfea-81aa777a714b', 'd005a107-1b1f-4ebb-8697-d0dbae66e9da');
INSERT INTO public.user_roles (user_id, role_id) VALUES ('adbf3f67-a12b-41c3-bfea-81aa777a714b', 'bc1e2893-9541-442c-96e0-dd762378c6a1');


INSERT INTO public.product_table (uuid, name) VALUES ('cd320922-df66-497a-841e-798c6fc1b0c3', 'Supplier Finance');
INSERT INTO public.product_table (uuid, name) VALUES ('55442dfa-9e08-49ef-bead-8615714c2ad1', 'Receivable Finance');


INSERT INTO public.rm_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, email_address, expiry_date, joining_date, rm_name, rm_id, active_status, valid_date) VALUES ('e7ef392f-09ba-4ca1-b680-722308527d77', null, null, '2021-09-20 15:10:12.107000', 'RAVIKANTH', '2021-09-20 15:10:12.107000', 'RAVIKANTH', 'PENDING', 'vijayan@gmail.com', '2050-09-20 13:56:54.000000', '2021-09-20 13:57:01.000000', 'Vijayan', 'RM00002', true, '2021-09-20 13:57:25.000000');

INSERT INTO public.agreement_table (system_id, authorisation_date, authorised_user, created_date, created_user, modified_date, modified_user, transaction_status, contract_document_number, contract_reference_number, expiry_date, number_of_counter_parties, remarks, transaction_date, valid_date, anchor_customer_id, product_id, rm_id) VALUES ('4ddb7042-f11f-4eb3-9c58-dbf79e4c0791', null, null, null, null, null, null, 'PENDING', 'CONTDOC00001', 'CONTREF00001', '2050-09-20 15:29:01.000000', 2, 'AGREEMENT REMARK', '2021-09-20 15:29:16.000000', '2021-09-20 15:29:19.000000', 'a855476b-3836-433d-81cb-35ec255db192', 'cd320922-df66-497a-841e-798c6fc1b0c3', 'e7ef392f-09ba-4ca1-b680-722308527d77');

DELETE FROM public.agreement_counter_parties_table;

INSERT INTO public.agreement_counter_parties_table (agreement_id, counter_party_id) VALUES ('4ddb7042-f11f-4eb3-9c58-dbf79e4c0791', '3c34b901-d8df-4c34-bc35-0cc18a688331');
INSERT INTO public.agreement_counter_parties_table (agreement_id, counter_party_id) VALUES ('4ddb7042-f11f-4eb3-9c58-dbf79e4c0791', '373c3c01-2bda-423f-bcdc-c5eae7c177c4');