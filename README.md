# Address Book Application

## Introduction
The Address Book Application is a Java project used to manage contact details.  
It demonstrates concepts like **OOP, Collections, File Handling, JDBC, Multithreading, and REST API integration**.  
The application evolves from basic contact management to database and JSON server integration.

## UC-1: Create Contact
Create a **Contact class** to store contact details.

**Fields:**
- First Name
- Last Name
- Address
- City
- State
- Zip
- Phone Number
- Email

This use case establishes the **basic data structure for contacts** in the Address Book.

---

## UC-2: Add Contact to Address Book

Ability to **add a new contact** to the Address Book.

**Implementation:**
- Created an `AddressBook` class.
- Stored contacts using a **Java Collection (ArrayList)**.
- Added a method to insert new `Contact` objects into the address book.

This allows users to **store multiple contacts in the Address Book**.

---

## UC-3: Prevent Duplicate Contact

Ability to **prevent duplicate contacts** in the Address Book.

**Implementation:**
- Checked existing contacts before adding a new one.
- Compared contacts using **first name and last name**.

This ensures that **duplicate entries are not added** to the Address Book.

---

## UC-4: Edit Existing Contact

Ability to **edit an existing contact** in the Address Book.

**Implementation:**
- Search contact using **first name**.
- Update required contact fields like **address, city, state, phone, or email**.

This allows users to **modify contact details when needed**.

---

## UC-5: Delete Contact

Ability to **delete a contact** from the Address Book.

**Implementation:**
- Search contact using **first name**.
- Remove the contact from the **Address Book list**.

This allows users to **remove unwanted contacts** from the Address Book.

