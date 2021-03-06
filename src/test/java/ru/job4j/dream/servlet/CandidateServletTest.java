package ru.job4j.dream.servlet;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ru.job4j.dream.Candidate;
import ru.job4j.dream.PsqlStore;
import ru.job4j.dream.MemStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class CandidateServletTest {
    @Ignore
    @Test
    public void whenCreateCan() throws IOException {
        MemStore store = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PowerMockito.when(req.getParameter("id")).thenReturn("0");
        PowerMockito.when(req.getParameter("name")).thenReturn("Candidate");
        PowerMockito.when(req.getParameter("chosenCity")).thenReturn("1");
        new CandidateServlet().doPost(req, resp);
        Candidate result = store.findAllCandidates().iterator().next();
        Assert.assertThat(result.getName(), Is.is("Candidate"));
    }
}