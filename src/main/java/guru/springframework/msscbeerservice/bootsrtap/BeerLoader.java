package guru.springframework.msscbeerservice.bootsrtap;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

//@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
    public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BEER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BEER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");

    private final BeerRepository beerRepository;

    @Autowired
    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if (beerRepository.count() == 0){
            beerRepository.save(
                    Beer.builder()
                            .beerName("Mango Bobs")
                            .beerStyle(BeerStyleEnum.IPA.name())
                            .minOnHand(12)
                            .quantityToBrew(200)
                            .upc(BeerLoader.BEER_1_UPC)
                            .price(new BigDecimal("12.95"))
                            .createdDate(new Timestamp(System.currentTimeMillis()))
                            .build()
            );
            beerRepository.save(
                    Beer.builder()
                            .beerName("Galaxy Cat")
                            .beerStyle(BeerStyleEnum.GOSE.name())
                            .minOnHand(12)
                            .quantityToBrew(200)
                            .upc(BeerLoader.BEER_2_UPC)
                            .price(new BigDecimal("11.95"))
                            .createdDate(new Timestamp(System.currentTimeMillis()))
                            .build()
            );
            beerRepository.save(
                    Beer.builder()
                            .beerName("No Hammer On The Bar")
                            .beerStyle(BeerStyleEnum.PALE_ALE.name())
                            .minOnHand(12)
                            .quantityToBrew(200)
                            .upc(BeerLoader.BEER_3_UPC)
                            .price(new BigDecimal("14.95"))
                            .createdDate(new Timestamp(System.currentTimeMillis()))
                            .build()
            );
            System.out.println(new Timestamp(System.currentTimeMillis()));
        }
    }
}
