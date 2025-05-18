package in.codecraftsbysanta.userauthservice.models;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testRoleCreation() {
        Role role = new Role();
        role.setId(1L);
        role.setValue("ADMIN");

        assertEquals(1L, role.getId());
        assertEquals("ADMIN", role.getValue());
    }

    @Test
    void testRoleUsersAssociation() {
        Role role = new Role();
        role.setUsers(new HashSet<>());

        assertNotNull(role.getUsers());
        assertTrue(role.getUsers().isEmpty());
    }
}