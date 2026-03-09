package com.AddressBookApp.test;

import com.AddressBookApp.model.Contact;
import com.AddressBookApp.service.AddressBook;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JSONServerTest {

    private AddressBook addressBook;

    @BeforeEach
    public void setup() {
        addressBook = new AddressBook();
        RestAssured.baseURI = "http://localhost:3000"; // JSON Server base URL
    }

    @Test
    public void testAddMultipleContactsToJSONServer() {
        List<Contact> contactsToAdd = Arrays.asList(
                new Contact("Alice", "1234567890", "alice@example.com", "Bhopal", "MP"),
                new Contact("Bob", "9876543210", "bob@example.com", "Indore", "MP"),
                new Contact("Charlie", "4561237890", "charlie@example.com", "Bhopal", "MP")
        );

        contactsToAdd.forEach(contact -> {
            given()
                    .header("Content-Type", "application/json")
                    .body(contact)
                    .when()
                    .post("/contacts")
                    .then()
                    .statusCode(201);

            addressBook.addContact(contact);
        });

        assertFalse(addressBook.getContacts().isEmpty());

        System.out.println("Contacts added to JSON Server and memory updated:");
        addressBook.getContacts().forEach(System.out::println);
    }
}