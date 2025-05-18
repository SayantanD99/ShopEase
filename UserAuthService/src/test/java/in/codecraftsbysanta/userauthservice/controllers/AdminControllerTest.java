package in.codecraftsbysanta.userauthservice.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminControllerTest {

    @Test
    void adminDashboard_returnsWelcomeMessage() {
        AdminController controller = new AdminController();
        String result = controller.adminDashboard();
        assertEquals("Welcome to the Admin Dashboard!", result);
    }
}