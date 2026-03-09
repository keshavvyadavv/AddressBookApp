package com.AddressBookApp.test;

import com.AddressBookApp.model.Contact;
import com.AddressBookApp.service.AddressBook;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UC24_JSONServerUpdateTest {

    private AddressBook addressBook;

    @BeforeEach
    public void setup() {
        addressBook = new AddressBook();
        RestAssured.baseURI = "http://localhost:3000"; 
    }

    @Test
    public void testUpdateContactOnJSONServer() {
        int contactId = 1;

        Contact updatedContact = new Contact(
                "Alice Updated",
                "1112223333",
                "alice_updated@example.com",
                "Bhopal",
                "MP"
        );

        Response response = given()
                .header("Content-Type", "application/json")
                .body(updatedContact)
                .when()
                .put("/contacts/" + contactId)
                .then()
                .statusCode(200) // ensure update success
                .extract().response();

        addressBook.getContacts().removeIf(c -> c.getName().equalsIgnoreCase("Alice"));
        addressBook.addContact(updatedContact);

        assertTrue(addressBook.getContacts().stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase("Alice Updated")));

        System.out.println("Contact updated on JSON Server and memory synced:");
        addressBook.getContacts().forEach(System.out::println);
    }
}