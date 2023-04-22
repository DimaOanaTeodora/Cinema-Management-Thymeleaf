# :cinema: Platforma pentru management-ul rezervarilor la cinema 
Documentatie

### :clipboard:  Baza de date - entitati
![DB schema](https://github.com/DimaOanaTeodora/Cinema-Management-Backend/blob/main/DB.png?raw=true)

## Aplicația va îndeplini cerințele următoare.
1. Vor fi create relații între entități de toate tipurile: @OneToOne, @OneToMany, @ManyToOne, @ManyToMany.

   @OneToOne 
   - Reservation - Broadcast
  
   @OneToMany
   - Movie - Broadcast
   - Room - Seat
   - Room - Broadcast
   - Schedule - Broadcast
  
   @ManyToOne
   - Broadcast - Room
   - Broadcast - Schedule
   - Broadcast - Movie
   - Reservation - User
   - Seat - Room
  
   @ManyToMany
   - Reservation - Seat (reserved_seat)

2. Vor fi implementate toate tipurile de operații CRUD.
### CREATE
	- (Admins only)Adaugare film la lista de filme
	- (Admins only) Adaugare programare film(broadcast) la o anumita data, ora, intr-o anumita sala
	- Adugare user (inregistrare cu username si parola)
	- Adaugare rezervare facuta de un user la un anumit film programat(broadcast)
### UPDATE
    - (Admins only) Modificarea unei programari a unui film (schimbare film, program, sala) 
### GET
	- Afisare lista filme pagina principala
	- Afisare lista programari cu toate informatiile pagina principala
	- Afisare lisat rezervari user curent pagina principala
	- Alte informatii extra pe paginile secundare
### DELETE
    - Anularea unei rezervari
   
3. Se va testa aplicația folosindu-se profiluri și două baze de date diferite, una dintre ele pentru etapa de testare. Se poate utiliza și o bază de date in-memory (H2).
   
4. Utilizare unit-tests/integration tests.
   
5. Se vor valida datele din formulare, se vor trata excepțiile.
   - Validare date formular - la adaugarea unui film, validarea numelui
  ```Java
  public class Movie {

	....

	@Size(min = 2, message = "*The name is too short!")
	@Size(max = 10, message = "*The name is too long (<=10 characters)!")
	private String name;

	...

  }
  ```
   - Tratarea exceptiilor 
6. Se vor utiliza log-uri. Opțional aspecte.
```
2023-04-22 23:14:40.790  INFO 9848 --- [nio-8080-exec-6] c.b.cinema.configuration.LoggingAspect   : aspect log after List com.backend.cinema.services.ReservationServiceImpl.getAllReservationsByUsername(String)
2023-04-22 23:14:40.790  INFO 9848 --- [nio-8080-exec-6] c.b.cinema.configuration.LoggingAspect   : aspect log after ModelAndView com.backend.cinema.controllers.MainController.getHome(Model)
```
```Java
@Component
@Aspect
@Slf4j
public class LoggingAspect {
	...
	@After("logAnnotation()")
	public void logMethodCallAdvice(JoinPoint joinPoint) {
		log.info("aspect log after " + joinPoint.getSignature());
	}

	@Before("logPackage()")
	public void logPackageAdvice(JoinPoint joinPoint) {
		log.info("aspect log before " + joinPoint.getSignature());
	}

	@After("logMethod()")
	public void logSetterAdvice(JoinPoint joinPoint) {
		log.info("aspect log after " + joinPoint.getSignature());
		log.info("aspect log param " + joinPoint.getArgs()[0]);
	}
	...
}
```
7. For fi utilizate opțiuni de paginarea și sortarea a datelor.
8. Se va include Spring Security (cerința minima autentificare jdbc).
- Sistem de login si inregistrare
  ```html
   <body>
      <div class="container" style="margin-top: 20px">
         <div class="col-md-6 col-md-offset-3">
            <form class="form-signin" method="post" th:action="perform_login">
               <h2 class="form-signin-heading">Sign in ....</h2>
               <p>
               <p th:if="${param.error}" class="text-danger">Invalid user or password</p>
               <label for="username" class="sr-only">Username</label>
               <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>
               </p>
               <p>
                  <label for="password" class="sr-only">Password</label>
                  <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
               </p>
               <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
               
               </br>
               
               <p> Don't have an account?
               	  <a class="btn btn-primary" href="/users/signup" >Register</a>
               </p>
            </form>
         </div>
      </div>
   </body>
  ```
- Permisiuni in functie de rolurile asociate
- Parola salvata criptata in baza de date 
  ```Java
  public class SecurityJpaConfig {

	....

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeRequests(auth -> auth.antMatchers("/broadcasts").permitAll()
				.antMatchers("/auction").hasAnyRole().antMatchers("/broadcasts/**").hasRole("ADMIN")
				.antMatchers("/auction").hasAnyRole().antMatchers("/movies/**").hasRole("ADMIN")
				.antMatchers("/movies/add").hasRole("ADMIN").antMatchers("/login").permitAll()
				.antMatchers("/broadcasts/add").hasRole("ADMIN").antMatchers("/login").permitAll()
		// .anyRequest().authenticated()
		).userDetailsService(userDetailsService).formLogin().loginPage("/login").loginProcessingUrl("/perform_login")
				.and().exceptionHandling().accessDeniedPage("/access_denied").and().httpBasic(withDefaults()).build();
	}

  }
  ```
