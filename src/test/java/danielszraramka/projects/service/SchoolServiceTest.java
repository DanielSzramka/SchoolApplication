package danielszraramka.projects.service;

import danielszraramka.projects.model.Address;
import danielszraramka.projects.model.School;
import danielszraramka.projects.repository.SchoolRepository;
import danielszraramka.projects.service.command.CreateAddressCommand;
import danielszraramka.projects.service.command.CreateSchoolCommand;
import danielszraramka.projects.service.exeption.SchoolAlreadyExistsException;
import danielszraramka.projects.service.exeption.SchoolNotFoundException;
import danielszraramka.projects.service.mapper.SchoolMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchoolServiceTest {

    public static final String SCHOOL_NAME = "Szkoła";
    public static final String TAX_NUMBER = "123345123123";
    public static final String SHORT_NAME = "sp";
    public static final String ZIP_CODE = "40-543";
    public static final String CITY = "Wrocław";
    public static final String STREET = "sadfasdf";
    public static final String FLAT_NUMBER = "12A";
    public static final String APARTMENT_NUMBER = "99";
    public static final long SCHOOL_ID = 44L;

    @InjectMocks
    private SchoolService schoolService;

    @Mock
    private SchoolMapper schoolMapper;

    @Mock
    private SchoolRepository schoolRepository;

    @Captor
    private ArgumentCaptor<School> schoolArgumentCaptor;

    @Captor
    private ArgumentCaptor<CreateSchoolCommand> createSchoolCommandArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> taxNumberArgumentCaptor;

    @Test
    public void shouldCreateSchool_whenSchoolDoesntExist() {
        //given
        CreateSchoolCommand createSchoolCommand = createTestCreateSchoolCommand();
        School school = createTestSchool();
        when(schoolMapper.mapFromCreateSchoolCommand(eq(createSchoolCommand))).thenReturn(school);
        when(schoolRepository.findSchoolByTaxNumber(eq(TAX_NUMBER))).thenReturn(Optional.empty());

        //when
        schoolService.createSchool(createSchoolCommand);

        //then
        verify(schoolRepository, times(1)).save(schoolArgumentCaptor.capture());
        School savedSchool = schoolArgumentCaptor.getValue();
        assertThat(savedSchool).isEqualTo(school);
    }

    @Test
    public void shouldThrowSchoolAlreadyExistException_whenSchoolExist() {
        //given
        CreateSchoolCommand createSchoolCommand = createTestCreateSchoolCommand();
        School school = createTestSchool();
        when(schoolRepository.findSchoolByTaxNumber(eq(TAX_NUMBER))).thenReturn(Optional.of(school));

        //when & then
        assertThrows(SchoolAlreadyExistsException.class, () -> schoolService.createSchool(createSchoolCommand));
    }

    @Test
    public void shouldReturnSchool_whenSchoolExist() {
        //given
        School school = createTestSchool();
        when(schoolRepository.findSchoolByTaxNumber(eq(TAX_NUMBER))).thenReturn(Optional.of(school));

        //when
        Optional<School> schoolValue = schoolService.isSchoolExist(TAX_NUMBER);

        //then
        assertTrue(schoolValue.isPresent());
    }

    @Test
    public void shouldReturnNull_whenSchoolDosentExist() {
        //given
        when(schoolRepository.findSchoolByTaxNumber(eq(TAX_NUMBER))).thenReturn(Optional.empty());

        //when
        Optional<School> schoolValue = schoolService.isSchoolExist(TAX_NUMBER);

        //then
        assertTrue(schoolValue.isEmpty());
    }

    @Test
    public void shouldUpdateSchool_whenSchoolExist() {
        //given
        CreateSchoolCommand createSchoolCommand = createTestCreateSchoolCommand(SCHOOL_ID);
        School school = createTestSchool(SCHOOL_ID);
        when(schoolRepository.findById(eq(SCHOOL_ID))).thenReturn(Optional.of(school));

        //when
        schoolService.updateSchool(createSchoolCommand);

        //then
        verify(schoolMapper, times(1)).updateSchool(createSchoolCommandArgumentCaptor.capture(), schoolArgumentCaptor.capture());
        CreateSchoolCommand createSchoolCommandValue = createSchoolCommandArgumentCaptor.getValue();
        School schoolValue = schoolArgumentCaptor.getValue();
        assertThat(createSchoolCommandValue).isEqualTo(createSchoolCommand);
        assertThat(schoolValue).isEqualTo(school);
    }

    @Test
    public void shouldThrowSchoolNotFoundException_whenSchoolDosentExist() {
        //given
        CreateSchoolCommand createSchoolCommand = createTestCreateSchoolCommand(SCHOOL_ID);
        when(schoolRepository.findById(eq(SCHOOL_ID))).thenReturn(Optional.empty());

        //when & then
        assertThrows(SchoolNotFoundException.class, () -> schoolService.updateSchool(createSchoolCommand));
    }

    private School createTestSchool() {
        return createTestSchool(null);
    }

    private School createTestSchool(Long id) {
        return School.builder()
                .schoolId(id)
                .fullNameOfSchool(SCHOOL_NAME)
                .taxNumber(TAX_NUMBER)
                .shortName(SHORT_NAME)
                .address(Address.builder()
                        .zipCode(ZIP_CODE)
                        .city(CITY)
                        .street(STREET)
                        .flatNumber(FLAT_NUMBER)
                        .apartmentNumber(APARTMENT_NUMBER)
                        .build())

                .build();
    }

    private CreateSchoolCommand createTestCreateSchoolCommand() {
        return createTestCreateSchoolCommand(null);
    }

    private CreateSchoolCommand createTestCreateSchoolCommand(Long id) {
        return CreateSchoolCommand.builder()
                .idCreateSchoolCommand(id)
                .fullNameOfSchool(SCHOOL_NAME)
                .taxNumber(TAX_NUMBER)
                .shortName(SHORT_NAME)
                .address(CreateAddressCommand.builder()
                        .zipCode(ZIP_CODE)
                        .city(CITY)
                        .street(STREET)
                        .flatNumber(FLAT_NUMBER)
                        .apartmentNumber(APARTMENT_NUMBER)
                        .build())
                .build();
    }
}