package oslomet.testing;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKunderOk() {
        Kunde kunde1 = new Kunde("01010110523", "Line", "Jensen",
                "Osloveien 82", "0850", "Oslo",
                "99462336", "HeiHei123");
        Kunde kunde2 = new Kunde("01070230525", "Ola", "Normann",
                "Bærumsveien 2", "0855", "Oslo",
                "41662377", "123HeiHei");
        List<Kunde> kundeliste = new ArrayList<>();
        kundeliste.add(kunde1);
        kundeliste.add(kunde2);

        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        // arrange
        Mockito.when(repository.hentAlleKunder()).thenReturn(kundeliste);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertEquals(kundeliste, resultat);
    }

    @Test
    public void hentAlleKunderFeil() {
        when(sjekk.loggetInn()).thenReturn(null);


        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }

    @Test
    public void lagreKundeOk() {
        Kunde kunde1 = new Kunde("01010110523", "Line", "Jensen",
                "Osloveien 82", "0850", "Oslo",
                "99462336", "HeiHei123");

        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        //arrange
        Mockito.when(repository.registrerKunde(kunde1)).thenReturn("OK");

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertEquals("OK", resultat);

    }

    @Test
    public void lagreKundeFeil() {
        Kunde kunde1 = new Kunde("01010110523", "Line", "Jensen",
                "Osloveien 82", "0850", "Oslo",
                "99462336", "HeiHei123");

        when(sjekk.loggetInn()).thenReturn(null);


        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void endre() {
        Kunde kunde1 = new Kunde("01010110523", "Line", "Jensen",
                "Osloveien 82", "0850", "Oslo",
                "99462336", "HeiHei123");

        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        //arrange
        Mockito.when(repository.endreKundeInfo(kunde1)).thenReturn("OK");

        // act
        String resultat = adminKundeController.endre(kunde1);

        // arrange
        assertEquals("OK", resultat);
    }

    @Test
    public void endreFeil() {
        Kunde kunde1 = new Kunde("01010110523", "Line", "Jensen",
                "Osloveien 82", "0850", "Oslo",
                "99462336", "HeiHei123");

        when(sjekk.loggetInn()).thenReturn(null);


        // act
        String resultat = adminKundeController.endre(kunde1);

        // arrange
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void slett() {
        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        //arrange
        Mockito.when(repository.slettKunde(personnummer)).thenReturn("OK");

        // act
        String resultat = adminKundeController.slett(personnummer);

        // arrange
        assertEquals("OK", resultat);
    }

    @Test
    public void slettFeil() {
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.slett(null);

        // arrange
        assertEquals("Ikke logget inn", resultat);
    }
}