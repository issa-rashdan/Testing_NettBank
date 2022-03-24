package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentTransaksjoner(){
        // arrange
        Konto enKonto = new Konto();

        String personnummer = "01010123456";
        when(sjekk.loggetInn()).thenReturn(personnummer);

        when(repository.hentTransaksjoner("01010110523","19.01.21", "26.03.21")).
                thenReturn(enKonto);
        //act
        Konto resultat = bankController.hentTransaksjoner("01010110523",
                "19.01.21", "26.03.21");

        //assert
        assertEquals(enKonto, resultat);

    }

    @Test
    public void hentTransaksjoner_Feil(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Konto resultat = bankController.hentTransaksjoner("01010110523","19.01.21",
                "26.03.21");

        //assert
        assertNull(resultat);

    }



    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        when(repository.hentKonti(personnummer)).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_LoggetInn(){
        //arrange
        List<Konto> saldi = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "105010123456",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "22334412345",
                1000, "Brukskonto", "NOK", null);
        Konto konto3 = new Konto("105010123456", "105020123456",
                1000, "Sparekonto", "NOK", null);
        saldi.add(konto1);
        saldi.add(konto2);
        saldi.add(konto3);

        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        when(repository.hentSaldi(personnummer)).thenReturn(saldi);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(saldi, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Konto> resultat = bankController.hentSaldi();

        //assert
        assertNull(resultat);
    }

    @Test
    public void registrerBetaling(){
        //arrange
        ArrayList<Transaksjon>transaksjoner = new ArrayList<>();

        Transaksjon betaling = new Transaksjon(20, "1234.56.78999",
                1200, "04.01.21", "Tilbakebetaling",
                "Avventer","1111.11.1111");
        transaksjoner.add(betaling);

        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        when(repository.registrerBetaling(betaling)).thenReturn("OK");

        //act
        String resultat = bankController.registrerBetaling(betaling);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerBetaling_Feil(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);


        //act
        String resultat = bankController.registrerBetaling(null);

        //assert
        assertNull(resultat);
    }

    @Test
    public void hentBetalinger(){
        //arrange
        ArrayList<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon betaling1 = new Transaksjon(20, "1234.56.78999",
                1200, "04.01.21", "Tilbakebetaling",
                "Avventer","1111.11.1111");
        Transaksjon betaling2 = new Transaksjon(30, "1234.56.78888",
                1300, "04.01.21", "Tilbakebetaling",
                "Avventer","2222.22.22222");
        Transaksjon betaling3 = new Transaksjon(40, "1234.56.77777",
                1400, "04.01.21", "Tilbakebetaling",
                "Avventer","3333.33.33333");
        transaksjoner.add(betaling1);
        transaksjoner.add(betaling2);
        transaksjoner.add(betaling3);

        String personnummer = "01010123456";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        when(repository.hentBetalinger(personnummer)).thenReturn(transaksjoner);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void hentBetalinger_Feil(){

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);
    }



    @Test
    public void utforBetaling(){
        ArrayList<Transaksjon>transaksjoner = new ArrayList<>();

        Transaksjon betaling1 = new Transaksjon(20, "1234.56.78999",
                1200, "04.01.21", "Tilbakebetaling",
                "Avventer","1111.11.1111");
        Transaksjon betaling2 = new Transaksjon(30, "1234.56.78888",
                1300, "04.01.21", "Tilbakebetaling",
                "Avventer","2222.22.22222");
        Transaksjon betaling3 = new Transaksjon(40, "1234.56.77777",
                1400, "04.01.21", "Tilbakebetaling",
                "Avventer","3333.33.33333");
        transaksjoner.add(betaling1);
        transaksjoner.add(betaling2);
        transaksjoner.add(betaling3);

        String personnummer = "01010123456";
        when(sjekk.loggetInn()).thenReturn(personnummer);

        when(repository.utforBetaling(20)).thenReturn("OK");

        when(repository.hentBetalinger(personnummer)).thenReturn(transaksjoner);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(20);

        //assert
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void utforBetaling_Feil(){
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(20);

        //assert
        assertNull(resultat);
    }

    @Test
    public void hentKundeInfo_loggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo("01010110523")).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }


    @Test
    public void endreKundeInfo(){
        //arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(enKunde)).thenReturn("Ok");

        //act
        String result = bankController.endre(enKunde);

        //assert
        assertEquals("Ok", result);
    }


    @Test
    public void endreKundeInfo_Feil(){
        //arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        //act
        String result = bankController.endre(enKunde);

        //assert
        assertNull(result);

    }
}