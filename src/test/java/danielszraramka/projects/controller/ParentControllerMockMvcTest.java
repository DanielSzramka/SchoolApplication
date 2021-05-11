package danielszraramka.projects.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import danielszraramka.projects.controller.dto.ParentDto;
import danielszraramka.projects.controller.mapper.command.ParentCommandMapper;
import danielszraramka.projects.controller.mapper.dto.ParentDtoMapper;
import danielszraramka.projects.controller.request.CreateParentRequestAndUpdate;
import danielszraramka.projects.model.Parent;
import danielszraramka.projects.service.ParentService;
import danielszraramka.projects.service.command.CreateParentCommand;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ParentController.class)
@ActiveProfiles("mockMvcTest")
class ParentControllerMockMvcTest {

    private static final Long PARENT_ID = 34L;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParentService parentService;

    @MockBean
    private ParentCommandMapper parentCommandMapper;

    @MockBean
    private ParentDtoMapper parentDtoMapper;

    @Captor
    private ArgumentCaptor<Long> parentIdArgumentCaptor;

    @Captor
    private ArgumentCaptor<Parent> parentArgumentCaptor;

    @Captor
    private ArgumentCaptor<List<Parent>> parentsArgumentCaptor;

    @Captor
    private ArgumentCaptor<CreateParentRequestAndUpdate> createParentRequestAndUpdateArgumentCaptor;

    @Captor
    private ArgumentCaptor<CreateParentCommand> createParentCommandArgumentCaptor;

    @Test
    //@WithUserDetails("parent")
    public void shouldReturnParent() throws Exception {

        when(parentDtoMapper.mapFromParent(any())).thenReturn(ParentDto.builder()
                .id(PARENT_ID)
                .build());

        MvcResult mvcResult = mockMvc.perform(get("/parents/{id}", PARENT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ParentDto parentDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ParentDto.class);

        verify(parentService).getParent(parentIdArgumentCaptor.capture());
        Long parentIdValue = parentIdArgumentCaptor.getValue();
        assertThat(parentIdValue).isEqualTo(PARENT_ID);

        assertThat(parentDto.getId()).isEqualTo(PARENT_ID);
    }

    @Test
    public void shouldReturnParentsList() throws Exception {
        List<ParentDto> parentDtos = createParentList();

//        List<ParentDto> doubleParentDtos = new ArrayList<>();
//        doubleParentDtos.addAll(parentDtos);
//        doubleParentDtos.addAll(parentDtos);

        when(parentDtoMapper.mapFromParents(anyList())).thenReturn(parentDtos);

        MvcResult mvcResult = mockMvc.perform(get("/parents")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<ParentDto> responseDtos = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        MatcherAssert.assertThat(responseDtos, Matchers.hasSize(2));
        MatcherAssert.assertThat(responseDtos, Matchers.contains(parentDtos.get(0), parentDtos.get(1)));

    }

    @WithUserDetails("parent")
    @Test
    public void shouldUpdateParent_whenParentExist() throws Exception {
//        ParentDto parentDto = ParentDto.builder()
//                .id(PARENT_ID)
//                .phoneNumber("938478378")
//                .identityNumber("sdfds342534324")
//                .name("asdasdasds")
//                .citizenship("asdasd")
//                .surname("asdsad23232")
//                .build();
        CreateParentRequestAndUpdate createParentRequestAndUpdate = createParentRequestAndUpdateTest();
        CreateParentCommand createParentCommand = createParentCommandTest();

        when(parentCommandMapper.mapFromCreateParentRequest(eq(createParentRequestAndUpdate), eq(PARENT_ID)))
                .thenReturn(createParentCommand);
        MvcResult mvcResult = mockMvc.perform(put("/parents/{id}", PARENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createParentRequestAndUpdate)))
                .andExpect(status().isOk())
                .andReturn();

        verify(parentCommandMapper).mapFromCreateParentRequest(createParentRequestAndUpdateArgumentCaptor.capture(),
                parentIdArgumentCaptor.capture());

        verify(parentService).updateParent(createParentCommandArgumentCaptor.capture());

        CreateParentRequestAndUpdate createParentRequestAndUpdateValue = createParentRequestAndUpdateArgumentCaptor.getValue();
        Long parentIdValue = parentIdArgumentCaptor.getValue();
        CreateParentCommand createParentCommandValue = createParentCommandArgumentCaptor.getValue();

        assertThat(createParentRequestAndUpdateValue).isEqualTo(createParentRequestAndUpdateValue);
        assertThat(parentIdValue).isEqualTo(PARENT_ID);
        assertThat(createParentCommandValue).isEqualTo(createParentCommand);

    }

    //parentService.removeParent(id);
    @Test
    public void shouldDeleteParent() throws Exception {

        MvcResult mvcResult = mockMvc.perform(delete("/parents/{id}", PARENT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    private CreateParentCommand createParentCommandTest() {
        return CreateParentCommand.builder()
                .idCreateParentCommand(PARENT_ID)
                .build();
    }

    private CreateParentRequestAndUpdate createParentRequestAndUpdateTest() {
        return CreateParentRequestAndUpdate.builder()
                .idCreateParentRequestAndUpdate(PARENT_ID)
                .mail("asasd@gmail.com")
                .identityNumber("sdfds342534324")
                .name("asdasdasds")
                .surname("asdsad23232")
                .citizenship("asdasd")
                .phoneNumber("938478378")
                .build();
    }


    //    verify(parentService).getParent(parentIdArgumentCaptor.capture());
    private List<ParentDto> createParentList() {
        return Arrays.asList(
                ParentDto.builder().id(54L).build(),
                ParentDto.builder().id(22L).build()
        );
    }

}