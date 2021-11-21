create schema if not exists admin;

alter schema admin owner to postgres;

create table if not exists admin.customer_table
(
    system_id               uuid not null,
    authorisation_date      timestamp,
    authorised_user         varchar(255),
    created_date            timestamp,
    created_user            varchar(255),
    modified_date           timestamp,
    modified_user           varchar(255),
    transaction_status      varchar(255),
    address_line1           varchar(255),
    address_line2           varchar(255),
    address_line3           varchar(255),
    is_bank                 boolean,
    country                 varchar(255),
    customer_id             varchar(255),
    director_details        varchar(255),
    director_name           varchar(255),
    email_address           varchar(255),
    name                    varchar(255),
    po_box                  varchar(255),
    sponsor_details         varchar(255),
    sponsor_name            varchar(255),
    status                  varchar(255),
    tax_registration_number varchar(255),
    vat_registration_number varchar(255),
    constraint pk_customertable_systemid
        primary key (system_id)
);

alter table admin.customer_table
    owner to postgres;

create table if not exists admin.product_table
(
    uuid               uuid not null,
    authorisation_date timestamp,
    authorised_user    varchar(255),
    created_date       timestamp,
    created_user       varchar(255),
    modified_date      timestamp,
    modified_user      varchar(255),
    transaction_status varchar(255),
    code               varchar(255),
    name               varchar(255),
    constraint pk_producttable_uuid
        primary key (uuid)
);

alter table admin.product_table
    owner to postgres;

create table if not exists admin.rm_table
(
    system_id          uuid not null,
    authorisation_date timestamp,
    authorised_user    varchar(255),
    created_date       timestamp,
    created_user       varchar(255),
    modified_date      timestamp,
    modified_user      varchar(255),
    transaction_status varchar(255),
    email_address      varchar(255),
    expiry_date        timestamp,
    joining_date       timestamp,
    rm_name            varchar(255),
    rm_id              varchar(255),
    active_status      boolean,
    valid_date         timestamp,
    constraint pk_rmtable_systemid
        primary key (system_id),
    constraint uk_rmtable_rmid
        unique (rm_id)
);

alter table admin.rm_table
    owner to postgres;

create table if not exists admin.agreement_table
(
    system_id                 uuid not null,
    authorisation_date        timestamp,
    authorised_user           varchar(255),
    created_date              timestamp,
    created_user              varchar(255),
    modified_date             timestamp,
    modified_user             varchar(255),
    transaction_status        varchar(255),
    contract_document_number  varchar(255),
    contract_reference_number varchar(255),
    expiry_date               timestamp,
    number_of_counter_parties integer,
    remarks                   varchar(255),
    status                    boolean,
    transaction_date          timestamp,
    valid_date                timestamp,
    anchor_customer_id        uuid,
    product_id                uuid,
    rm_id                     uuid,
    constraint pk_agrementtable_systemid
        primary key (system_id),
    constraint uk_agreementtable_contractdocumentnumber
        unique (contract_document_number),
    constraint uk_agreementtable_contractreferencenumber
        unique (contract_reference_number),
    constraint fk_agreementtable_anchorcustomerid
        foreign key (anchor_customer_id) references admin.customer_table,
    constraint fk_agreementtable_productid
        foreign key (product_id) references admin.product_table,
    constraint fk_agreementtable_rmid
        foreign key (rm_id) references admin.rm_table
);

alter table admin.agreement_table
    owner to postgres;

create table if not exists admin.agreement_counter_parties_table
(
    agreement_id     uuid not null,
    counter_party_id uuid not null,
    constraint fk_agremeentcounterpartiestable_counterpartyid
        foreign key (counter_party_id) references admin.customer_table,
    constraint fk_agreementcounterpartiestable_agreementid
        foreign key (agreement_id) references admin.agreement_table
);

alter table admin.agreement_counter_parties_table
    owner to postgres;

create table if not exists admin.role_table
(
    role_id            uuid not null,
    authorisation_date timestamp,
    authorised_user    varchar(255),
    created_date       timestamp,
    created_user       varchar(255),
    modified_date      timestamp,
    modified_user      varchar(255),
    transaction_status varchar(255),
    role_name          varchar(255),
    constraint pk_roletable_roleid
        primary key (role_id)
);

alter table admin.role_table
    owner to postgres;

create table if not exists admin.sbr_table
(
    system_id                       uuid not null,
    authorisation_date              timestamp,
    authorised_user                 varchar(255),
    created_date                    timestamp,
    created_user                    varchar(255),
    modified_date                   timestamp,
    modified_user                   varchar(255),
    transaction_status              varchar(255),
    administrative_fee_amount       numeric(19, 2),
    administrative_fee_currency     varchar(255),
    anchor_customer_address_line1   varchar(255),
    anchor_customer_address_line2   varchar(255),
    anchor_customer_address_line3   varchar(255),
    anchor_customer_contact_name    varchar(255),
    anchor_customer_email           varchar(255),
    anchor_customer_fax             varchar(255),
    anchor_customer_po_box          varchar(255),
    anchor_customer_telephone       varchar(255),
    anchor_party_account_id         varchar(255),
    applied_limit_amount            numeric(19, 2),
    applied_limit_currency          varchar(255),
    auto_financing_availability     boolean,
    auto_financing                  boolean,
    cash_margin                     real,
    commercial_contract_details     varchar(255),
    counter_party_account_id        varchar(255),
    counter_party_address_line1     varchar(255),
    counter_party_address_line2     varchar(255),
    counter_party_address_line3     varchar(255),
    counter_party_contact_name      varchar(255),
    counter_party_email             varchar(255),
    counter_party_fax               varchar(255),
    counter_party_po_box            varchar(255),
    counter_party_telephone         varchar(255),
    direct_contact_flag             varchar(255),
    documents_required              varchar(255),
    ear_mark_reference              varchar(255),
    early_payment_fee_amount        numeric(19, 2),
    early_payment_fee_currency      varchar(255),
    factoring_commission_rate       real,
    financing_information           varchar(255),
    finacning_profit_margin_rate    real,
    goods_description               varchar(255),
    invoice_service_charge_amount   numeric(19, 2),
    invoice_service_charge_currency varchar(255),
    limit_amount                    numeric(19, 2),
    limit_currency                  varchar(255),
    limit_expiry                    timestamp,
    limit_reference                 varchar(255),
    limit_type_flag                 varchar(255),
    management_fee_amount           numeric(19, 2),
    management_fee_currency         varchar(255),
    max_loan_percentage             real,
    nature_of_business              varchar(255),
    payment_terms_condition         varchar(255),
    payment_terms_days              integer,
    profit_rate_type                varchar(255),
    rebate_account                  varchar(255),
    rebate_rate                     real,
    recourse_flag                   varchar(255),
    sbr_id                          varchar(255),
    status                          boolean,
    transaction_date                timestamp,
    agreement_id                    uuid,
    anchor_customer_id              uuid,
    counter_party_id                uuid,
    constraint pk_sbrtable_systemid
        primary key (system_id),
    constraint fk_sbrtable_agreementid
        foreign key (agreement_id) references admin.agreement_table,
    constraint fk_sbrtable_anchorcustomerid
        foreign key (anchor_customer_id) references admin.customer_table,
    constraint fk_sbrtable_counterparytid
        foreign key (counter_party_id) references admin.customer_table
);

alter table admin.sbr_table
    owner to postgres;

create table if not exists admin.user_table
(
    system_id uuid not null
        constraint user_table_pkey
            primary key,
    authorisation_date timestamp,
    authorised_user varchar(255),
    created_date timestamp,
    created_user varchar(255),
    modified_date timestamp,

    modified_user varchar(255),
    transaction_status varchar(255),
    effective_date timestamp,
    expiry_date timestamp,
    email_address varchar(255)
        constraint uk_usertable_emailaddress
            unique,
    first_name varchar(255),
    last_name varchar(255),
    active_status boolean,
    user_id varchar(255),
    delete_flag boolean
);

alter table admin.user_table owner to postgres;



alter table admin.user_table
    owner to postgres;

create table if not exists admin.user_customer_mapping
(
    user_id     uuid not null,
    customer_id uuid not null,
    constraint fk_usercustomermappingtable_customerid
        foreign key (customer_id) references admin.customer_table,
    constraint fk_usercustomermappingtable_userid
        foreign key (user_id) references admin.user_table
);

alter table admin.user_customer_mapping
    owner to postgres;

create table if not exists admin.user_roles
(
    user_id uuid not null,
    role_id uuid not null,
    constraint fk_userrolestable_roleid
        foreign key (role_id) references admin.role_table,
    constraint fk_userrolestable_userid
        foreign key (user_id) references admin.user_table
);

alter table admin.user_roles
    owner to postgres;

create table if not exists admin.notification_table
(
    message_id              uuid not null
        constraint pk_notificationtable_messageid
            primary key,
    authorisation_date      timestamp,
    authorised_user         varchar(255),
    created_date            timestamp,
    created_user            varchar(255),
    modified_date           timestamp,
    modified_user           varchar(255),
    transaction_status      varchar(255),
    attachments             bytea,
    bcc_list                varchar(255),
    cc_list                 varchar(255),
    content                 text,
    from_list               varchar(255),
    notification_event      varchar(255),
    status                  varchar(255),
    subject                 varchar(255),
    to_list                 varchar(255),
    transaction_information text
);

alter table admin.notification_table
    owner to postgres;




create table if not exists admin.audit_table
(
    uuid uuid not null
        constraint pk_audittable_uuid
            primary key,
    accessed_by varchar(255),
    accessed_resource varchar(255),
    event_action varchar(255),
    event_at timestamp,
    exception varchar(255),
    input_parameters text,
    jwt_token varchar(255),
    remote_address varchar(255),
    remote_host varchar(255),
    remote_user varchar(255),
    returned_result text
);

alter table admin.audit_table owner to postgres;

create table if not exists admin.portal_agreement_table
(
    system_id uuid not null
    constraint portal_agreement_table_pkey
    primary key,
    authorisation_date timestamp,
    authorised_user varchar(255),
    created_date timestamp,
    created_user varchar(255),
    modified_date timestamp,
    modified_user varchar(255),
    transaction_status varchar(255),
    contract_document_number varchar(255)
    constraint uk_mo0ppuf6fyjglkupvpjg4noep
    unique,
    contract_reference_number varchar(255)
    constraint uk_6vrxxhd92cw29dnm5m3cjty73
    unique,
    expiry_date timestamp,
    number_of_counter_parties integer,
    remarks varchar(255),
    status boolean,
    transaction_date timestamp,
    transmission_status varchar(255),
    valid_date timestamp,
    anchor_customer_id uuid
    constraint fkemu66hil3iokppeokikijdib9
    references admin.customer_table,
    product_id uuid
    constraint fkch4jpv1sxp30nt1ignsv1cksi
    references admin.product_table,
    rm_id uuid
    constraint fke6b4bch5gwl0aoxhslwdja9fr
    references admin.rm_table,
    message_id uuid
    constraint uk_ocd1x340i0svwdsrwtfighbyt
    unique
    );

alter table admin.portal_agreement_table owner to postgres;

create table if not exists admin.portal_agreement_counter_parties_table
(
    agreement_id uuid not null
        constraint fkjmbfatieopm0rfxksblm3hm3m
            references admin.portal_agreement_table,
    counter_party_id uuid not null
        constraint fk8o0oijj6xiv2u3g4eodufk3k6
            references admin.customer_table
);

alter table admin.portal_agreement_counter_parties_table owner to postgres;



create table if not exists admin.portal_customer_table
(
    system_id uuid not null
        constraint portal_customer_table_pkey
            primary key,
    authorisation_date timestamp,
    authorised_user varchar(255),
    created_date timestamp,
    created_user varchar(255),
    modified_date timestamp,
    modified_user varchar(255),
    transaction_status varchar(255),
    address_line1 varchar(255),
    address_line2 varchar(255),
    address_line3 varchar(255),
    is_bank boolean,
    country varchar(255),
    customer_id varchar(255),
    director_details varchar(255),
    director_name varchar(255),
    email_address varchar(255),
    message_id uuid
        constraint uk_rl8q16fejwyxfs91k8blte04j
            unique,
    name varchar(255),
    po_box varchar(255),
    sponsor_details varchar(255),
    sponsor_name varchar(255),
    status varchar(255),
    tax_registration_number varchar(255),
    transmission_status varchar(255),
    vat_registration_number varchar(255)
);

alter table admin.portal_customer_table owner to postgres;

create table if not exists admin.portal_rm_table
(
    system_id uuid not null
        constraint portal_rm_table_pkey
            primary key,
    authorisation_date timestamp,
    authorised_user varchar(255),
    created_date timestamp,
    created_user varchar(255),
    modified_date timestamp,
    modified_user varchar(255),
    transaction_status varchar(255),
    email_address varchar(255),
    expiry_date timestamp,
    joining_date timestamp,
    message_id uuid
        constraint uk_no2iaq526s3b24o6ht5spd94
            unique,
    rm_name varchar(255),
    rm_id varchar(255)
        constraint uk_em2i3ecqny2ivwqqspsc0vsdl
            unique,
    active_status boolean,
    transmission_status varchar(255),
    valid_date timestamp
);

alter table admin.portal_rm_table owner to postgres;

create table if not exists admin.portal_sbr_table
(
    system_id uuid not null
        constraint portal_sbr_table_pkey
            primary key,
    authorisation_date timestamp,
    authorised_user varchar(255),
    created_date timestamp,
    created_user varchar(255),
    modified_date timestamp,
    modified_user varchar(255),
    transaction_status varchar(255),
    administrative_fee_amount numeric(19,2),
    administrative_fee_currency varchar(255),
    anchor_customer_address_line1 varchar(255),
    anchor_customer_address_line2 varchar(255),
    anchor_customer_address_line3 varchar(255),
    anchor_customer_contact_name varchar(255),
    anchor_customer_email varchar(255),
    anchor_customer_fax varchar(255),
    anchor_customer_po_box varchar(255),
    anchor_customer_telephone varchar(255),
    anchor_party_account_id varchar(255),
    applied_limit_amount numeric(19,2),
    applied_limit_currency varchar(255),
    auto_financing_availability boolean,
    auto_financing boolean,
    cash_margin real,
    commercial_contract_details varchar(255),
    counter_party_account_id varchar(255),
    counter_party_address_line1 varchar(255),
    counter_party_address_line2 varchar(255),
    counter_party_address_line3 varchar(255),
    counter_party_contact_name varchar(255),
    counter_party_email varchar(255),
    counter_party_fax varchar(255),
    counter_party_po_box varchar(255),
    counter_party_telephone varchar(255),
    direct_contact_flag varchar(255),
    documents_required varchar(255),
    ear_mark_reference varchar(255),
    early_payment_fee_amount numeric(19,2),
    early_payment_fee_currency varchar(255),
    factoring_commission_rate real,
    financing_information varchar(255),
    finacning_profit_margin_rate real,
    goods_description varchar(255),
    invoice_service_charge_amount numeric(19,2),
    invoice_service_charge_currency varchar(255),
    limit_amount numeric(19,2),
    limit_currency varchar(255),
    limit_expiry timestamp,
    limit_reference varchar(255),
    limit_type_flag varchar(255),
    management_fee_amount numeric(19,2),
    management_fee_currency varchar(255),
    max_loan_percentage real,
    message_id uuid
        constraint uk_a16cwxnja1ounfuiidd8tuhup
            unique,
    nature_of_business varchar(255),
    payment_terms_condition varchar(255),
    payment_terms_days integer,
    profit_rate_type varchar(255),
    rebate_account varchar(255),
    rebate_rate real,
    recourse_flag varchar(255),
    sbr_id varchar(255),
    status boolean,
    transaction_date timestamp,
    transmission_status varchar(255),
    agreement_id uuid
        constraint fk8rjoshdqo7wfqk5ksc5xs3r0b
            references admin.portal_agreement_table,
    anchor_customer_id uuid
        constraint fk8bipcsg370viqwjsdvvo2rmj1
            references admin.portal_customer_table,
    counter_party_id uuid
        constraint fkefvohcxulaae00nytj1x2l68v
            references admin.portal_customer_table
);

alter table admin.portal_sbr_table owner to postgres;

create table if not exists admin.portal_user_table
(
    system_id uuid not null
    constraint portal_user_table_pkey
    primary key,
    authorisation_date timestamp,
    authorised_user varchar(255),
    created_date timestamp,
    created_user varchar(255),
    modified_date timestamp,
    modified_user varchar(255),
    transaction_status varchar(255),
    effective_date timestamp,
    email_address varchar(255)
    constraint uk_buxacj55goy29g1gpbl18blh3
    unique,
    first_name varchar(255),
    last_name varchar(255),
    message_id uuid
    constraint uk_ow3whsdwgd1petgpjn6gcl49p
    unique,
    active_status boolean,
    transmission_status varchar(255),
    user_id varchar(255)
    );

alter table admin.portal_user_table owner to postgres;


create table if not exists admin.portal_user_customer_mapping
(
    user_id uuid not null
        constraint fk7ctw06of2ioql9tpb8ip7c9ls
            references admin.portal_user_table,
    customer_id uuid not null
        constraint fk36kfd3pfo75joq400soninquj
            references admin.portal_customer_table
);

alter table admin.portal_user_customer_mapping owner to postgres;

create table if not exists admin.portal_user_roles
(
    user_id uuid not null
        constraint fk23lcf5coo7aqf4aasm10gwaa0
            references admin.portal_user_table,
    role_id uuid not null
        constraint fklm00op0c4ustworuehlnjd0dd
            references admin.role_table
);

alter table admin.portal_user_roles owner to postgres;

















