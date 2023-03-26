# :cinema: Platforma pentru management-ul rezervarilor la cinema 
Documentatie

## :clipboard: MySQL database - Baza de date - entitati
![DB schema](https://github.com/DimaOanaTeodora/Cinema-Management-Backend/blob/main/DB.png?raw=true)

## :briefcase: REST ENDPOINTS - CRUD - Functionalitati
### CREATE
1. Adaugare film
2. Adaugare filme printr-un JSON cu o lista de filme
3. Adaugare sala + generare automata locuri din sala in ordine crescatoare incepand cu 1
4. Adaugare sali printr-un JSON cu o lista de sali + enerare automata locuri din sala in ordine crescatoare incepand cu 1
5. Adaugare user + duplicate exception pentru email
6. Adaugarea unei rezervari facuta de un user, pentru un anumit film, la o anumita data si ora, cu un numar de locuri dat
7. Adaugare programare film intr-o anumita sala, la o anumita data si ora
### UPDATE
1. Modificarea programului (data si ora de inceput/sfarsit) a unui film 
2. Modificarea salii in care un film este programat
### GET
1. Afisarea locurilor libere la un anumit film programat sa ruleze + not found exception
2. Afisarea informatiilor unui user + not found exception
3. Afisarea informatiilor unei sali (cu tot cu locurile autogenerate) + not found exception
4. Afisarea informatiilor despre programarea unui film + not found exception
5. Afisarea informatiilor despre o rezervare + not found exception
6. Afisarea informatiilor despre un film + not found exception
### DELETE
1. Stergerea unui user si a rezervarilor aferente acestuia
2. Anularea unei rezervari
3. Stergerea unei programari a unui film
   
## :next_track_button: Testing
Code coverage total obtinut 69.7%

![Tests](https://github.com/DimaOanaTeodora/Cinema-Management-Backend/blob/main/test.png?raw=true)

Au fost create atat teste de integrare pentru controllers cat si unit teste pentru servicii.

Testele de integrare au fost facute pentru *UserController* si *ScheduleController* acoperind cate o functionalitate CRUD.
```Java
@WebMvcTest(controllers = UserController.class) // this tells Spring Boot to auto-configure a Spring web context
												// for integration tests for the UserController class
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private UserService userService;
	@MockBean
	private ReservationService resevrationService;
	@MockBean
	private UserMapper userMapper;

	@Test
	public void createUser() throws Exception {
		UserRequest request = new UserRequest("oanadima26@gmail.com", "Dima", "Oana-Teodora");

		when(userService.createUser(any())).thenReturn(new User(1, "oanadima26@gmail.com", "Dima", "Oana-Teodora"));

		mockMvc.perform(
				post("/users").contentType("application/json").content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.lastName").value(request.getLastName()))
				.andExpect(jsonPath("$.firstName").value(request.getFirstName()))
				.andExpect(jsonPath("$.email").value(request.getEmail()));
	}
    ...
}
```

Unit testele acopera marea majoritate a functionalitatilor din serviciile folosite.
```Java
@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

	@InjectMocks
	private RoomService roomService;

	@Mock
	private RoomRepository roomRepository;
	
	...

	@Test
	void whenRoomDoesntExists_getRoom_throwsRoomNotFoundException() {

		// Act
		RoomNotFoundException exception = assertThrows(RoomNotFoundException.class, () -> roomService.getRoom(1));

		// Assert
		assertEquals("Room with id 1 doesn't exist ", exception.getMessage());

	}
    ...
}
```
## Controllers 
Exista 7 controllere create cate una pentru fiecare entitate in parte.
- UserController /users
- ScheduleController /schedules
- MovieController /movies
- RoomController /rooms
- SeatController /seats
- BroadcastController /broadcasts
- ReservationController /reservations
  
## Services
Exista 7 servicii create cate una pentru fiecare entitate in parte.
- UserService 
- ScheduleService 
- MovieService 
- RoomService  
- SeatService  
- BroadcastService  
- ReservationService  
  
```Java 
@Service
public class BroadcastService {

	private BroadcastRepository broadcastRepository;

	public BroadcastService(BroadcastRepository broadcastRepository) {
		this.broadcastRepository = broadcastRepository;
	}

	public Broadcast updateBroadcastRoom(Broadcast oldBroadcast, Room newRoom) {
		oldBroadcast.setRoom(newRoom);
		return broadcastRepository.save(oldBroadcast);
	}

	public Broadcast createBroadcast(Broadcast broadcast, Room room, Movie movie) {
		broadcast.setRoom(room);
		broadcast.setMovie(movie);
		return broadcastRepository.save(broadcast);
	}

	public Broadcast getBroadcast(Integer id) {
		Optional<Broadcast> broadcastOptional = broadcastRepository.findById(id);
		if (broadcastOptional.isPresent()) {
			return broadcastOptional.get();
		} else {
			throw new BroadcastNotFoundException(id);
		}
	}

	public void deleteBroadcast(Integer id) {
		Optional<Broadcast> broadcastOptional = broadcastRepository.findById(id);
		if (broadcastOptional.isPresent()) {
			broadcastRepository.delete(broadcastOptional.get());
		} else {
			throw new BroadcastNotFoundException(id);
		}
	}

}
```
## Repositories
- Fiecare entitate are asociata cate o interfata care extinde *JpaRepository<Entitate, Tip_ID>* unde au fost definite metodele care nu se aflau in interfata de baza (cele existente deja: findById, findAllById, delete, save etc...) 
```Java
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "select * from user where first_name = :name", nativeQuery = true)
	User findUserByFirstNameWithNativeQuery(String name);

	Optional<User> findByEmail(String email);

}
```

## Model mapper 
- Clasele de tip *@Component* cu scopul maparii entitatilor de tip request in entitati folosite ca model in baza de date au fost create pentru fiecare entitate in parte, continand un singur constructor cu parametrii
```Java
@Component
public class UserMapper {

	public User userRequestToUser(UserRequest userRequest) {

		return new User(userRequest.getEmail(), userRequest.getLastName(), userRequest.getFirstName());
	}
}
```

## DTOs
- fiecare entitate de tip *NumeEntitateRequest* contine pentru toate campurile validari in functie de tip
- String/Enum
```Java
public class MovieRequest {

	@NotBlank(message = "Name of the movie cannot be null")
	@ApiModelProperty(value = "name", required = true, notes = "The name of the movie", example = "Avatar 2", position = 1)
	private String name;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Type cannot be null")
	@ApiModelProperty(value = "type", required = true, notes = "The type of the movie", example = "D2", position = 2)
	private MovieType type;
    ...
    }
```
- int/Integer
```Java
public class RoomRequest {

	...

	@NotNull(message = "Capacity cannot be null")
	@Min(1)
	@ApiModelProperty(value = "capacity", required = true, notes = "The capacity of the room", example = "40", position = 2)
	private int capacity;

    ...
}
```

## :exclamation: Exception handling
- Pentru user: UserDuplicateException si UserNotFoundException
- Pentru restul entatilor doar NotFoundException
  
```Java
  @ControllerAdvice
    public class GlobalExceptionHandler {
        
        @ExceptionHandler({DuplicateUserException.class})
        public ResponseEntity handle(DuplicateUserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        @ExceptionHandler({UserNotFoundException.class})
        public ResponseEntity<String> handle(UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage() + " at " + LocalDateTime.now());
        }
        
        @ExceptionHandler({MovieNotFoundException.class})
        public ResponseEntity<String> handle(MovieNotFoundException e) {
            ...
        }
        
        ...

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
            return ResponseEntity.badRequest()
                    .body("Invalid value : " + e.getFieldError().getRejectedValue() +
                            " for field " + e.getFieldError().getField() +
                            " with message " + e.getFieldError().getDefaultMessage());
        }
    }
```

## :high_brightness: SWAGGER DOCS

![Swagger](https://github.com/DimaOanaTeodora/Cinema-Management-Backend/blob/main/swagger.png?raw=true)

```Java
\\SwaggerConfig.java

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.backend.cinema.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Cinema API Documentation")
                .description("API Documentation for all available operations")
                .build();
    }
}
```
Adaugarea informatiilor pentru ficare API in controllers
```Java
@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a user", notes = "Creates a new user based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The User was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
            ...
```