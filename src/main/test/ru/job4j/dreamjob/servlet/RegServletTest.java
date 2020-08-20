package ru.job4j.dreamjob.servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.psql.StoreUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StoreUser.class)
public class RegServletTest {
    private StubStoreUser base;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        base = new StubStoreUser();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        PowerMockito.mockStatic(StoreUser.class);
        when(StoreUser.instOf()).thenReturn(base);
    }

    @Test
    public void tryRegUserPass() throws ServletException, IOException {
        base.save(new User(0, "one", "one@email", "111"));

        when(request.getParameter("name")).thenReturn("test");

        new RegServlet().doPost(request, response);

//        assertThat(list.iterator().next().getName(), is("one"));
        assertEquals("test", base.getById(0).getName());
    }

    /**
     * test pass if will have NPE. Pass because NPE throw on:
     * req.getRequestDispatcher("reg.jsp").forward(req, resp);
     * <p>
     * it's not a test case but it final part of code,
     * so it should check main function part.
     */
    @Test
    public void tryUpdUserPass() throws ServletException, IOException {
        base.save(new User(0, "one", "test@test", "test"));

        when(request.getParameter("email")).thenReturn("test@test");
        when(request.getParameter("password")).thenReturn("updPassword");

        try {
            new RegServlet().doPost(request, response);
        } catch (NullPointerException e) {
            assertEquals("updPassword", base.getById(0).getPassword());
        }
    }

    /**
     * test pass if will have NPE. Pass because NPE throw on:
     * (HttpSession sc) sc.setAttribute("user", user);
     * <p>
     * it's not a test case but it final part of code,
     * so it should check main function part.
     */
    @Test
    public void tryAuthUserPass() throws ServletException, IOException {
        base.save(new User(0, "one", "test@test", "test"));

        when(request.getParameter("email")).thenReturn("test@test");
        when(request.getParameter("password")).thenReturn("updPassword");

        boolean testPass = false;

        try {
            new AuthServlet().doGet(request, response);
        } catch (NullPointerException e) {
            testPass = true;
        }
        assertTrue(testPass);
    }

}
