package com.primelab.nearBase.data.localcontact

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Patterns
import com.primelab.nearBase.model.Contact
import com.primelab.nearBase.model.ContactEmail
import com.primelab.nearBase.model.ContactPhone
import java.util.*

class LocalContact(val context: Context) : ContactSource {

    companion object {
        private val CONTACTS_PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts.PHOTO_URI
        )
        private val CONTACTS_KINDS_PROJECTION = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
    }

    private fun formatNumber(input: String): String {
        return input
    }

    @SuppressLint("Range")
    override suspend fun getAllContactWithEmail(userId: String): List<Contact> {
        val contactList = mutableListOf<Contact>()
        val cursor: Cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            CONTACTS_PROJECTION,
            null,
            null,
            null
        ) ?: return contactList
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
                val phones: MutableList<String> = ArrayList()
                val emails: MutableList<String> = ArrayList()
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val contactCursor: Cursor? = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        CONTACTS_KINDS_PROJECTION,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
                    )
                    while (contactCursor != null && contactCursor.moveToNext()) {
                        val phoneNo = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        if (!phoneNo.isNullOrEmpty()) {
                            var phoneNumber = phoneNo.replace("[^0-9]".toRegex(), "")
                            phoneNumber = formatNumber(phoneNumber)
                            phones.add(phoneNumber)
                        }
                    }
                    contactCursor?.close()
                }
                val crEmails: Cursor? = context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID
                            + " = ?", arrayOf(id), null
                )
                while (crEmails != null && crEmails.moveToNext()) {
                    val email = crEmails.getString(
                        crEmails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
                    )
                    // Checking validation of email
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emails.add(email)
                    }
                }
                val contact = Contact()
                contact.first_name = name?.split(" ")?.firstOrNull() ?: " "
                contact.last_name = name?.split(" ")?.getOrNull(1) ?: " "
                contact.owner_id = userId
                contact.id = id
                if (emails.isNotEmpty()) {
                    contact.email = listOf(ContactEmail(emails.first(), "personal"))
                }
                if (phones.isNotEmpty()) {
                    contact.phone = listOf(ContactPhone(phones.first(), "local"))
                }
                contactList.add(contact)
                crEmails?.close()
            }
        }
        cursor.close()
        return contactList
    }
}