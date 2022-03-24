package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;


    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti(){
        ArrayList<Konto> konto = new ArrayList<>();
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Konto enkonto = new Konto("01010110523", "1309.34.23456",
                15000, "brukerkonto",
                "Nok", transaksjoner);
        konto.add(enkonto);

        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        //arrange
        when(repository.hentAlleKonti()).thenReturn(konto);

        //act
        List <Konto> result = adminKontoController.hentAlleKonti();

        //assert
        assertEquals(konto, result);

    }

    @Test
    public void hentAlleKontiFeil(){
        when(sjekk.loggetInn()).thenReturn(null);


        //act
        List<Konto> result = adminKontoController.hentAlleKonti();

        //assert
        assertNull(result);
    }

    @Test
    public void registrerKonto_OK(){
        ArrayList<Konto> konto = new ArrayList<>();
        ArrayList<Transaksjon>transaksjoner = new ArrayList<>();

        Transaksjon t = new Transaksjon(20, "1234.56.78999",
                1200, "04.01.21", "Tilbakebetaling",
                "Avventer","1111.11.1111");
        transaksjoner.add(t);

        Konto konto1 = new Konto("01010110523", "1309.34.23456",
                12_901.35, "Type?", "Euro", transaksjoner);
        konto.add(konto1);

        String p = "01010110523";

        when(sjekk.loggetInn()).thenReturn(p);

        //arrange
        Mockito.when(repository.registrerKonto(konto1)).thenReturn("Ok");

        //act
        String resultat = adminKontoController.registrerKonto(konto1);

        //assert
        assertEquals("Ok", resultat);
    }

    @Test
    public void registrerKonto_Feil(){
        ArrayList<Konto> konto = new ArrayList<>();
        ArrayList<Transaksjon>transaksjoner = new ArrayList<>();

        Transaksjon t1 = new Transaksjon(20, "1234.56.78999",
                1200, "04.01.21", "Tilbakebetaling",
                "Avventer","1111.11.1111");
        Transaksjon t2 = new Transaksjon(30, "1234.56.79999",
                1300, "04.01.21", "Tilbakebetaling",
                "Avventer","1111.12.1111");
        transaksjoner.add(t1);
        transaksjoner.add(t2);

        Konto konto1 = new Konto("01010110523", "1309.34.23456",
                12_901.35, "Type?", "Euro", transaksjoner);
        konto.add(konto1);

        when(sjekk.loggetInn()).thenReturn(null);


        //act
        String resultat = adminKontoController.registrerKonto(konto1);

        //assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void endreKonto(){
        ArrayList<Konto> konto = new ArrayList<>();
        ArrayList<Transaksjon>transaksjoner = new ArrayList<>();

        Transaksjon t1 = new Transaksjon(20, "1234.56.78999",
                1200, "04.01.21", "Tilbakebetaling",
                "Avventer","1111.11.1111");
        Transaksjon t2 = new Transaksjon(30, "1234.56.79999",
                1300, "04.01.21", "Tilbakebetaling",
                "Avventer","1111.12.1111");
        transaksjoner.add(t1);
        transaksjoner.add(t2);

        Konto konto1 = new Konto("01010110523", "1309.34.23456",
                12_901.35, "Type?", "Euro", transaksjoner);
        konto.add(konto1);

        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        //arrange
        Mockito.when(repository.endreKonto(konto1)).thenReturn("Ok");

        //act
        String resultat = adminKontoController.endreKonto(konto1);

        //assert
        assertEquals("Ok", resultat);
    }

    @Test
    public void endreKontoFeil(){
        ArrayList<Konto> konto = new ArrayList<>();
        ArrayList<Transaksjon>transaksjoner = new ArrayList<>();

        Transaksjon t1 = new Transaksjon(20, "1234.56.78999",1200,
                "04.01.21", "Tilbakebetaling", "Avventer","1111.11.1111");
        Transaksjon t2 = new Transaksjon(30, "1234.56.79999",1300,
                "04.01.21", "Tilbakebetaling", "Avventer","1111.12.1111");
        transaksjoner.add(t1);
        transaksjoner.add(t2);

        Konto konto1 = new Konto("01010110523", "1309.34.23456",
                12_901.35, "Type?", "Euro", transaksjoner);
        konto.add(konto1);

        when(sjekk.loggetInn()).thenReturn(null);


        //act
        String resultat = adminKontoController.endreKonto(konto1);

        //assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void slettKonto(){
        String personnummer ="01010110523";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        //arrange
        Mockito.when(adminKontoController.slettKonto("01230110523")).thenReturn("OK");

        //act
        String result = adminKontoController.slettKonto("01230110523");

        //assert
        assertEquals("OK",result);
    }

    @Test
    public void slettKontoFeil(){
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String result = adminKontoController.slettKonto("01230110523");

        //assert
        assertEquals("Ikke innlogget", result);
    }
}