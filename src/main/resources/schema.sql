drop schema  if exists admin cascade;

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
    non_customer            boolean,
    country                 varchar(255),
    customer_id             varchar(255),
    director_details        varchar(255),
    director_name           varchar(255),
    email_address           varchar(255),
    name                    varchar(255),
    po_box                  varchar(255),
    sponsor_details         varchar(255),
    sponsor_name            varchar(255),
    status                  boolean,
    tax_registration_number varchar(255),
    vat_registration_number varchar(255),
    delete_flag boolean,
    effective_date timestamp,
    expiry_date timestamp,
    constraint pk_customertable_systemid
        primary key (system_id)
);

alter table admin.customer_table
    owner to postgres;

create table if not exists admin.product_table
(
    system_id               uuid not null,
    authorisation_date timestamp,
    authorised_user    varchar(255),
    created_date       timestamp,
    created_user       varchar(255),
    modified_date      timestamp,
    modified_user      varchar(255),
    transaction_status varchar(255),
    product_code               varchar(255),
    product_name               varchar(255),
    effective_date      timestamp,
    expiry_date timestamp,
    delete_flag boolean,
    active_status boolean,
    constraint pk_producttable_uuid
        primary key (system_id)
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
    first_name            varchar(255),
    last_name            varchar(255),
    rm_id              varchar(255),
    active_status      boolean,
    delete_flag        boolean,
    effective_date         timestamp,
    constraint pk_rmtable_systemid
        primary key (system_id),
    constraint uk_rmtable_rmid
        unique (rm_id)
);

alter table admin.rm_table
    owner to postgres;

create table if not exists admin.rm_customers_table
(
    rm_id     uuid not null,
    customer_id uuid not null,
    constraint fk_rmcustomerstable_rmid
    foreign key (rm_id) references admin.rm_table,
    constraint fk_rmcustomerstable_customerid
    foreign key (customer_id) references admin.customer_table
    );

alter table admin.rm_customers_table
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
    limit_amount numeric(19,2),
    limit_currency varchar(255),
    limit_expiry timestamp,
    limit_reference varchar(255),
    cash_margin numeric(19,2),
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
    expiry_date                    timestamp,
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


create table if not exists admin.portal_table
(
    message_id          uuid,
    insert_time timestamp,
    processed_time timestamp,
    transmit_time timestamp,
    message text,
    message_type varchar(255),
    transmit_retry_count int,
    process_retry_count int,
    status varchar(255),
    constraint pk_portabletable_messageid
    primary key (message_id)
    );

alter table admin.portal_table owner to postgres;

create table if not exists admin.account_table
(
    system_id          uuid not null
        constraint account_table_pkey
            primary key,
    authorisation_date timestamp,
    authorised_user    varchar(255),
    created_date       timestamp,
    created_user       varchar(255),
    modified_date      timestamp,
    modified_user      varchar(255),
    transaction_status varchar(255),
    currency           varchar(255),
    deleteflag         boolean,
    description        varchar(255),
    id                 varchar(255)
        constraint uk_5acbu68wyasufjcd3f6hbktiv
            unique,
    name               varchar(255),
    status             boolean,
    type               varchar(255)
);

alter table admin.account_table
    owner to postgres;



















