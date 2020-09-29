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
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StoreUser.class)
public class AuthServletTest {
    private StubStoreUser base;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void setUp() {
        base = new StubStoreUser();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        PowerMockito.mockStatic(StoreUser.class);
        when(StoreUser.instOf()).thenReturn(base);
    }

    @Test
    public void tryAuthUserPass() throws ServletException, IOException {
        base.save(new User(0, "one", "test@test", "testPassword"));

//      Роман Вохмин: а так пробовал?
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        when(request.getParameter("email")).thenReturn("test@test");
        when(request.getParameter("password")).thenReturn("testPassword");

        new AuthServlet().doGet(request, response);

        assertNull(session.getAttribute("user"));
    }

}