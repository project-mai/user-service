/*
 * This file is generated by jOOQ.
 */
package ru.mai.user.jooq.tables.records


import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record4
import org.jooq.Row4
import org.jooq.impl.UpdatableRecordImpl

import ru.mai.user.jooq.tables.User


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class UserRecord() : UpdatableRecordImpl<UserRecord>(User.USER), Record4<Long?, String?, String?, String?> {

    open var id: Long?
        set(value): Unit = set(0, value)
        get(): Long? = get(0) as Long?

    open var name: String?
        set(value): Unit = set(1, value)
        get(): String? = get(1) as String?

    open var password: String?
        set(value): Unit = set(2, value)
        get(): String? = get(2) as String?

    open var email: String?
        set(value): Unit = set(3, value)
        get(): String? = get(3) as String?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Long?> = super.key() as Record1<Long?>

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row4<Long?, String?, String?, String?> = super.fieldsRow() as Row4<Long?, String?, String?, String?>
    override fun valuesRow(): Row4<Long?, String?, String?, String?> = super.valuesRow() as Row4<Long?, String?, String?, String?>
    override fun field1(): Field<Long?> = User.USER.ID
    override fun field2(): Field<String?> = User.USER.NAME
    override fun field3(): Field<String?> = User.USER.PASSWORD
    override fun field4(): Field<String?> = User.USER.EMAIL
    override fun component1(): Long? = id
    override fun component2(): String? = name
    override fun component3(): String? = password
    override fun component4(): String? = email
    override fun value1(): Long? = id
    override fun value2(): String? = name
    override fun value3(): String? = password
    override fun value4(): String? = email

    override fun value1(value: Long?): UserRecord {
        set(0, value)
        return this
    }

    override fun value2(value: String?): UserRecord {
        set(1, value)
        return this
    }

    override fun value3(value: String?): UserRecord {
        set(2, value)
        return this
    }

    override fun value4(value: String?): UserRecord {
        set(3, value)
        return this
    }

    override fun values(value1: Long?, value2: String?, value3: String?, value4: String?): UserRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        return this
    }

    /**
     * Create a detached, initialised UserRecord
     */
    constructor(id: Long? = null, name: String? = null, password: String? = null, email: String? = null): this() {
        this.id = id
        this.name = name
        this.password = password
        this.email = email
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised UserRecord
     */
    constructor(value: ru.mai.user.jooq.tables.pojos.User?): this() {
        if (value != null) {
            this.id = value.id
            this.name = value.name
            this.password = value.password
            this.email = value.email
            resetChangedOnNotNull()
        }
    }
}
