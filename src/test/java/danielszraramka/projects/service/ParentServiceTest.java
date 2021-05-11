package danielszraramka.projects.service;

import danielszraramka.projects.model.Parent;
import danielszraramka.projects.model.User;
import danielszraramka.projects.repository.ParentRepository;
import danielszraramka.projects.repository.UserRepository;
import danielszraramka.projects.service.command.CreateParentCommand;
import danielszraramka.projects.service.exeption.EmailAlreadyExistsException;
import danielszraramka.projects.service.exeption.PersonNotFoundException;
import danielszraramka.projects.service.mapper.ParentMapper;
import danielszraramka.projects.service.mapper.UserMapper;
import danielszraramka.projects.service.query.FindParentsQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParentServiceTest {

    private static final String USER_EMAIL = "sdifjdsiodf@wp.pl";
    private static final String USER_PASSWORD_HASH = "3432432n434iu32nh4ui32432i";
    private static final String PARENT_PHONE_NUMBER = "343-42334-3434";
    private static final Long COMMAND_ID = 23223L;
    private static final Long PARENT_ID = 442L;
    private static final long USER_ID = 12L;


    @InjectMocks
    private ParentService parentService;

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ParentMapper parentMapper;
    @Mock
    private Specification<Parent> parentSpecification;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<Parent> parentArgumentCaptor;

    @Captor
    private ArgumentCaptor<CreateParentCommand> createParentCommandArgumentCaptor;

    @Captor
    private ArgumentCaptor<Specification<Parent>> specificationArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> idParentArgumentCaptor;

    public static Specification<Parent> hasPhoneNumber(String phoneNumber) {
        return (parent, cq, cb) -> cb.equal(parent.get("phoneNumber"), phoneNumber);
    }

    @Test
    public void shouldSaveUserAndParent_whenMailDoesntExist() {
        //given
        CreateParentCommand command = createTestCreateParentCommand();
        User mappedUser = createTestUser();
        Parent mappedParent = createTestParent();
        when(userMapper.mapFromCreateParentCommand(eq(command))).thenReturn(mappedUser);
        when(parentMapper.mapFromCreateParentCommandAndUser(eq(command), eq(mappedUser))).thenReturn(mappedParent);
        when(userRepository.findUserByMail(eq(USER_EMAIL))).thenReturn(Optional.empty());

        //when
        parentService.createParent(command);

        //then
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        verify(parentRepository, times(1)).save(parentArgumentCaptor.capture());

        User savedUser = userArgumentCaptor.getValue();
        Parent savedParent = parentArgumentCaptor.getValue();

        assertThat(savedUser).isEqualTo(mappedUser);
        assertThat(savedParent).isEqualTo(mappedParent);
    }

    @Test
    public void shouldThrowEmailAlreadyExistException_whenMailExist() {
        //given
        CreateParentCommand command = createTestCreateParentCommand();
        User userFromRepository = createTestUser();
        when(userRepository.findUserByMail(eq(command.getMail()))).thenReturn(Optional.of(userFromRepository));

        //when & then
//        try {
//            parentService.createParent(command);
//            fail("No exception.");
//        } catch (EmailAlreadyExistsException exception) {
//            //do nothing
//        }
        assertThrows(EmailAlreadyExistsException.class, () -> {
            parentService.createParent(command);
        });
    }

    @Test
    public void shouldGetParentByID_whenParentExist() {
        //given
        Parent parent = createTestParent(PARENT_ID);
        when(parentRepository.findById(eq(PARENT_ID))).thenReturn(Optional.of(parent));

        //when
        Parent parentValue = parentService.getParent(PARENT_ID);

        //then
        assertThat(parentValue).isEqualTo(parent);
    }

    @Test
    public void shouldThrowPersonNotExist_whenPersonNotExist() {
        //given
        Parent parent = createTestParent(PARENT_ID);
        when(parentRepository.findById(eq(PARENT_ID))).thenReturn(Optional.empty());

        //when & then
        assertThrows(PersonNotFoundException.class, () -> {
            parentService.getParent(PARENT_ID);
        });
    }

    @Test
    public void shouldUpdateParent_whenParentExist() {
        //given
        CreateParentCommand command = createTestCreateParentCommand(COMMAND_ID);
        Parent parent = createTestParent(COMMAND_ID);
        when(parentRepository.findById(eq(COMMAND_ID))).thenReturn(Optional.of(parent));

        //when
        parentService.updateParent(command);

        //then
        verify(parentMapper, times(1)).updateParent(createParentCommandArgumentCaptor.capture(),
                parentArgumentCaptor.capture());

        CreateParentCommand createParentCommandValue = createParentCommandArgumentCaptor.getValue();
        Parent parentValue = parentArgumentCaptor.getValue();

        assertThat(createParentCommandValue).isEqualTo(command);
        assertThat(parentValue).isEqualTo(parent);
    }

    @Test()
    public void shouldThrowPersonNotFoundException_whenPersonDoesntExist() {
        //given
        CreateParentCommand command = createTestCreateParentCommand(COMMAND_ID);
        when(parentRepository.findById(eq(COMMAND_ID))).thenReturn(Optional.empty());

        //when & then
        assertThrows(PersonNotFoundException.class, () -> {
            parentService.updateParent(command);
        });
    }

    @Test
    public void shouldGetParentsWithOutParam() {
        //given
        FindParentsQuery findParentsQuery = createFindParentQuery("", "", "", "", "");
        List<Parent> parentList = new ArrayList<>();
        parentList.add(createTestParent(123L));
        parentList.add(createTestParent(43L));
        parentList.add(createTestParent(99L));
        when(parentRepository.findAll()).thenReturn(unmodifiableList(parentList));

        //when
        List<Parent> parents = parentService.getParents(findParentsQuery);

        //then
        assertThat(parents).isEqualTo(parentList);
        assertThat(parents).size().isEqualTo(parentList.size());
    }

    @Test
    public void shouldGetParentsWithParam() {
        //given
        FindParentsQuery findParentsQuery = createFindParentQuery("", "", PARENT_PHONE_NUMBER, "", "");
        parentSpecification = Parent.hasPhoneNumber(PARENT_PHONE_NUMBER);
        List<Parent> parents = Collections.singletonList(createTestParent());
        when(parentRepository.findAll(ArgumentMatchers.<Specification<Parent>>any())).thenReturn(parents);

        //when
        parentService.getParents(findParentsQuery);

        //then
        verify(parentRepository, times(1)).findAll(specificationArgumentCaptor.capture());
        Specification<Parent> finalSpecification = specificationArgumentCaptor.getValue();
        assertThat(finalSpecification).isNotNull();
    }

    @Test
    public void shouldDeleteParentAndUser_whenParentAndUserExist() {
        //given
        Parent parent = createTestParentAndUser(PARENT_ID);
        when(parentRepository.findById(eq(PARENT_ID))).thenReturn(Optional.of(parent));

        //when
        parentService.removeParent(PARENT_ID);

        //then
        verify(parentRepository, times(1)).deleteById(idParentArgumentCaptor.capture());
        Long idParentValue = idParentArgumentCaptor.getValue();
        assertThat(idParentValue).isEqualTo(PARENT_ID);
    }

    @Test
    public void shouldThrowPersonNotFoundException_whenPersonDosentExist() {
        //given
        when(parentRepository.findById(eq(PARENT_ID))).thenReturn(Optional.empty());

        //when & then
        assertThrows(PersonNotFoundException.class, () -> {
            parentService.removeParent(PARENT_ID);
        });
    }

    private User createTestUser() {
        return User.builder()
                .mail(USER_EMAIL)
                .passwordHash(USER_PASSWORD_HASH)
                .build();
    }

    private FindParentsQuery createFindParentQuery(String surname, String name, String phoneNumber, String city, String street) {
        return FindParentsQuery.builder()
                .surname(surname)
                .name(name)
                .phoneNumber(phoneNumber)
                .city(city)
                .street(street)
                .build();
    }

    private Parent createTestParent() {
        return createTestParent(null);
    }

    private Parent createTestParent(Long id) {
        return Parent.builder()
                .parentId(id)
                .phoneNumber(PARENT_PHONE_NUMBER)
                .build();
    }

    private CreateParentCommand createTestCreateParentCommand() {
        return createTestCreateParentCommand(null);
    }

    private CreateParentCommand createTestCreateParentCommand(Long id) {
        return CreateParentCommand.builder()
                .mail(USER_EMAIL)
                .idCreateParentCommand(id)
                .build();
    }

    private Parent createTestParentAndUser(Long id) {
        return Parent.builder()
                .parentId(id)
                .user(User.builder()
                        .id(USER_ID)
                        .passwordHash(USER_PASSWORD_HASH)
                        .mail(USER_EMAIL)
                        .build())
                .phoneNumber(PARENT_PHONE_NUMBER)
                .build();
    }

}