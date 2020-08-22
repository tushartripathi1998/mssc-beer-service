package guru.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbeerservice.bootsrtap.BeerLoader;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.*;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@ComponentScan(basePackages = "guru.springframework.msscbeerservice.web.mappers")
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    public static final String BEER_1_UPC = "0081113375213";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .param("isCold", "yes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer",
                pathParameters(
                        parameterWithName("beerId").description("UUID of desired beer to get")
                ),
                requestParameters(
                        parameterWithName("isCold").description("Is Beer Cold")
                ),
                responseFields(
                        fieldWithPath("id").description("Id of Beer"),
                        fieldWithPath("version").description("Version Number"),
                        fieldWithPath("createdDate").description("Date Created"),
                        fieldWithPath("lastModifiedDate").description("Last Modified Date"),
                        fieldWithPath("beerName").description("Name of Beer"),
                        fieldWithPath("beerStyleEnum").description("Style of Beer"),
                        fieldWithPath("upc").description("Upc of Beer"),
                        fieldWithPath("price").description("Price of Beer"),
                        fieldWithPath("quantityOnHand").description("Quantity on Hand")
                )
                ));
    }

    @Test
    void saveBeer() throws Exception{
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer",
                        requestFields(
                                fieldWithPath("id").ignored(),
                                fieldWithPath("version").description("Version Number"),
                                fieldWithPath("createdDate").description("Date Created"),
                                fieldWithPath("lastModifiedDate").description("Last Modified Date"),
                                fieldWithPath("beerName").description("Name of Beer"),
                                fieldWithPath("beerStyleEnum").description("Style of Beer"),
                                fieldWithPath("upc").description("Upc of Beer"),
                                fieldWithPath("price").description("Price of Beer"),
                                fieldWithPath("quantityOnHand").description("Quantity on Hand")
                        )
                ));
    }

    @Test
    void updateBeerById() throws Exception{
        BeerDto beerDto = BeerDto.builder().build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(RestDocumentationRequestBuilders
                .put("/api/v1/beer/"+UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    BeerDto getValidBeerDto(){
        return BeerDto.builder()
                .beerName("My Beer")
                .beerStyleEnum(BeerStyleEnum.ALE)
                .price(new BigDecimal("2.99"))
                .upc(122344444444L)
                .build();
    }
}