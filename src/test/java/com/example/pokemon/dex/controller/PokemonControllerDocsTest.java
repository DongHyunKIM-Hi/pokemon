package com.example.pokemon.dex.controller;

import com.example.pokemon.dex.model.entity.Pokemon;
import com.example.pokemon.dex.service.PokemonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Boot 4.1 does not yet ship {@code @AutoConfigureRestDocs} (spring-restdocs 4.0.1
 * has no autoconfigure module for it), so MockMvc is wired by hand with
 * {@link RestDocumentationExtension} + {@code documentationConfiguration(...)}
 * instead of {@code @WebMvcTest} + {@code @AutoConfigureRestDocs}.
 */
@ExtendWith(RestDocumentationExtension.class)
class PokemonControllerDocsTest {

    private final PokemonService pokemonService = mock(PokemonService.class);
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(new PokemonController(pokemonService))
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void 포켓몬_등록() throws Exception {
        given(pokemonService.registerPokemon("피카츄", "electric", 5))
                .willReturn(new Pokemon(1L, "피카츄", "electric", 5));

        mockMvc.perform(post("/v1/pokemons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"피카츄\",\"type\":\"electric\",\"level\":5}"))
                .andExpect(status().isCreated())
                .andDo(document("pokemon-create",
                        requestFields(
                                fieldWithPath("name").description("포켓몬 이름"),
                                fieldWithPath("type").description("타입"),
                                fieldWithPath("level").description("레벨 (1~100)")
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 여부"),
                                fieldWithPath("data.id").description("생성된 포켓몬 id"),
                                fieldWithPath("data.name").description("이름"),
                                fieldWithPath("data.type").description("타입"),
                                fieldWithPath("data.level").description("레벨"),
                                fieldWithPath("error").description("에러 정보 (성공 시 null)")
                        )));
    }

    @Test
    void 포켓몬_단건_조회() throws Exception {
        given(pokemonService.getPokemon(1L))
                .willReturn(new Pokemon(1L, "피카츄", "electric", 5));

        mockMvc.perform(get("/v1/pokemons/{pokemon-id}", 1L))
                .andExpect(status().isOk())
                .andDo(document("pokemon-get",
                        pathParameters(
                                parameterWithName("pokemon-id").description("조회할 포켓몬 id")
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 여부"),
                                fieldWithPath("data.id").description("포켓몬 id"),
                                fieldWithPath("data.name").description("이름"),
                                fieldWithPath("data.type").description("타입"),
                                fieldWithPath("data.level").description("레벨"),
                                fieldWithPath("error").description("에러 정보 (성공 시 null)")
                        )));
    }

    @Test
    void 포켓몬_목록_조회() throws Exception {
        given(pokemonService.getPokemons("electric"))
                .willReturn(List.of(new Pokemon(1L, "피카츄", "electric", 5)));

        mockMvc.perform(get("/v1/pokemons").param("type", "electric"))
                .andExpect(status().isOk())
                .andDo(document("pokemon-list",
                        queryParameters(
                                parameterWithName("type").description("필터링할 타입 (없으면 전체 조회)").optional()
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 여부"),
                                fieldWithPath("data[].id").description("포켓몬 id"),
                                fieldWithPath("data[].name").description("이름"),
                                fieldWithPath("data[].type").description("타입"),
                                fieldWithPath("data[].level").description("레벨"),
                                fieldWithPath("error").description("에러 정보 (성공 시 null)")
                        )));
    }

    @Test
    void 포켓몬_수정() throws Exception {
        given(pokemonService.updatePokemon(1L, "electric", 10))
                .willReturn(new Pokemon(1L, "피카츄", "electric", 10));

        mockMvc.perform(patch("/v1/pokemons/{pokemon-id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"electric\",\"level\":10}"))
                .andExpect(status().isOk())
                .andDo(document("pokemon-update",
                        pathParameters(
                                parameterWithName("pokemon-id").description("수정할 포켓몬 id")
                        ),
                        requestFields(
                                fieldWithPath("type").description("타입"),
                                fieldWithPath("level").description("레벨 (1~100)")
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 여부"),
                                fieldWithPath("data.id").description("포켓몬 id"),
                                fieldWithPath("data.name").description("이름"),
                                fieldWithPath("data.type").description("타입"),
                                fieldWithPath("data.level").description("레벨"),
                                fieldWithPath("error").description("에러 정보 (성공 시 null)")
                        )));
    }

    @Test
    void 포켓몬_삭제() throws Exception {
        willDoNothing().given(pokemonService).deletePokemon(anyLong());

        mockMvc.perform(delete("/v1/pokemons/{pokemon-id}", 1L))
                .andExpect(status().isNoContent())
                .andDo(document("pokemon-delete",
                        pathParameters(
                                parameterWithName("pokemon-id").description("삭제할 포켓몬 id")
                        )));
    }
}
