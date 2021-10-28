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
    constraint customer_table_pkey
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
    constraint product_table_pkey
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
    constraint rm_table_pkey
        primary key (system_id),
    constraint uk_kyeeo0b1k3f14c4d6c88jll96
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
    constraint agreement_table_pkey
        primary key (system_id),
    constraint uk_10l36pk4yfqddgox6yhr4nbti
        unique (contract_document_number),
    constraint uk_l3nsw0msbtd45c2pxaqmd19lb
        unique (contract_reference_number),
    constraint fk8v027y1c13s4j1b58xugtst50
        foreign key (anchor_customer_id) references admin.customer_table,
    constraint fk135ttxdrmluvfjpfdmgmo34v5
        foreign key (product_id) references admin.product_table,
    constraint fk4fomn1tot37i3gngdhixwj8oi
        foreign key (rm_id) references admin.rm_table
);

alter table admin.agreement_table
    owner to postgres;

create table if not exists admin.agreement_counter_parties_table
(
    agreement_id     uuid not null,
    counter_party_id uuid not null,
    constraint fkd52tymo5m0jpph62w5r3mppt0
        foreign key (counter_party_id) references admin.customer_table,
    constraint fkpq0t83bd01mmtn0vakg7f0xtr
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
    constraint role_table_pkey
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
    constraint sbr_table_pkey
        primary key (system_id),
    constraint fkqi1xpmdvgqdjpeqbt6opkwbc5
        foreign key (agreement_id) references admin.agreement_table,
    constraint fkaxx2k2nd2xoo33hk0ixme1jbw
        foreign key (anchor_customer_id) references admin.customer_table,
    constraint fkqn1o52vblpmlic6oj526bmv7n
        foreign key (counter_party_id) references admin.customer_table
);

alter table admin.sbr_table
    owner to postgres;

create table if not exists admin.user_table
(
    system_id          uuid not null,
    authorisation_date timestamp,
    authorised_user    varchar(255),
    created_date       timestamp,
    created_user       varchar(255),
    modified_date      timestamp,
    modified_user      varchar(255),
    transaction_status varchar(255),
    effective_date     timestamp,
    email_address      varchar(255),
    first_name         varchar(255),
    last_name          varchar(255),
    active_status      boolean,
    user_id            varchar(255),
    constraint user_table_pkey
        primary key (system_id)
);

alter table admin.user_table
    owner to postgres;

create table if not exists admin.user_customer_mapping
(
    user_id     uuid not null,
    customer_id uuid not null,
    constraint fkn81afod09fjiphjvs6hmxq8vb
        foreign key (customer_id) references admin.customer_table,
    constraint fkge7lm9vcnrxwdt60d5tb3tm2b
        foreign key (user_id) references admin.user_table
);

alter table admin.user_customer_mapping
    owner to postgres;

create table if not exists admin.user_roles
(
    user_id uuid not null,
    role_id uuid not null,
    constraint fke786ne22ois769q419yn4y5fi
        foreign key (role_id) references admin.role_table,
    constraint fkoxgn0x8mxxu1hovew528qckp9
        foreign key (user_id) references admin.user_table
);

alter table admin.user_roles
    owner to postgres;

create table if not exists admin.notification_table
(
    message_id uuid not null
        constraint notification_table_pkey
            primary key,
    authorisation_date timestamp,
    authorised_user varchar(255),
    created_date timestamp,
    created_user varchar(255),
    modified_date timestamp,
    modified_user varchar(255),
    transaction_status varchar(255),
    attachments bytea,
    bcc_list varchar(255),
    cc_list varchar(255),
    content varchar(255),
    from_list varchar(255),
    status integer,
    subject varchar(255),
    to_list varchar(255),
    transaction_information varchar(255),
    notification_event varchar(255)
);

alter table admin.notification_table owner to postgres;


