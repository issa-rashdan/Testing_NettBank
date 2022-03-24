package oslomet.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhetsController {

    @InjectMocks
    // denne skal testes
    private Sikkerhet sikkerhetsController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private MockHttpSession session;

    @Before
    public void initSession() {
        Map<String,Object> attributes = new HashMap<String,Object>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());
    }
    @Test
    public void test_sjekkLoggetInn() {
        // arrange
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("OK");


        // denne setningen vil ikke sette en attributten og det betyr at man kan ikke sette en attributt
        session.setAttribute("Innlogget", "12345678901");

        // act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901","HeiHeiHei");
        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void test_loggInnFeilPersonnummer(){
        //act
        String resultat = sikkerhetsController.sjekkLoggInn("1234567890","HeiHeiHei");

        //assert
        assertEquals("Feil i personnummer",resultat);
    }

    @Test
    public void test_loggInnFeilPassord(){
        //act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901","H");

        //assert
        assertEquals("Feil i passord",resultat);
    }

    @Test
    public void test_loggInnFeilPersonnummerEllerPassord(){ //sjekk
        // arrange
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Feil i personnummer eller passord");

        //act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901","HeiHeiHei");

        //assert
        assertEquals("Feil i personnummer eller passord",resultat);

    }

    @Test
    public void test_loggetUT(){
        // arrange
        session.setAttribute("Utlogget", null);

        sikkerhetsController.loggUt();

    }

    @Test
    public void test_sjekkLoggInnAdmin(){
        String results = sikkerhetsController.loggInnAdmin("Admin", "Admin");

        assertEquals("Logget inn", results);

    }
    @Test
    public void test_sjekkLoggInnAdminFeil(){
        String resultat1 = sikkerhetsController.loggInnAdmin("Admin", "Admin123");
        String resultat2 = sikkerhetsController.loggInnAdmin("Admin2", "Admin");

        assertEquals("Ikke logget inn", resultat1);
        assertEquals("Ikke logget inn", resultat2);
    }


    @Test
    public void test_LoggetInn(){
        // arrange
        session.setAttribute("Innlogget", "12345678901");

        // act
        String resultat = sikkerhetsController.loggetInn();

        // assert
        assertEquals("12345678901", resultat);
    }

    @Test
    public void test_LoggetInnFeil() {
        // act
        String resultat = sikkerhetsController.loggetInn();

        // assert
        assertNull(resultat);
    }
}